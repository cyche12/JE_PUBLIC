import os
from math import hypot
from Database.DatabaseHandler import DatabaseHandler
from Database.LocationRepository import LocationRepository
from Database.FeatureRepository import FeatureRepository
from Database.BoundaryRepository import BoundaryRepository
from Database.ObstacleRepository import ObstacleRepository
from Database.RouteRepository import RouteRepository
from Services.LabelParser import LabelParser
from Services.PathPlanner import PathPlanner

class MultiViewAnalyzer:
    """
    Parses both street (suffix=1) & satellite (suffix=2),
    averages door & curb, computes path & distance,
    persists to PostGIS.
    """
    SCALE_M_PER_PX = 0.25
    CLASS_MAP_KEY = {
        'building':'building',
        'boundaryof property':'boundary',
        'obstacle':'obstacle',
        'walkways':'walkways',
    }

    def __init__(self, slug: str):
        self.slug = slug
        base = os.path.join('Static','train')
        self.img_dir = os.path.join(base,'images')
        self.lbl_dir = os.path.join(base,'labels')

    def analyze(self) -> float:
        all_polys = {v:[] for v in self.CLASS_MAP_KEY.values()}
        doors, curbs = [], []
        # parse two views
        for suffix in ('1','2'):
            img_p = os.path.join(self.img_dir,f"{self.slug}-{suffix}.jpg")
            lbl_p = os.path.join(self.lbl_dir,f"{self.slug}-{suffix}.txt")
            if not (os.path.exists(img_p) and os.path.exists(lbl_p)):
                continue
            res = LabelParser(img_p, lbl_p).parse()
            # collect
            for raw_name, poly_list in res['polygons'].items():
                key = self.CLASS_MAP_KEY.get(raw_name)
                if key:
                    all_polys[key].extend(poly_list)
            doors.append(tuple(res['door']))
            curbs.append(tuple(res['curb']))
        if not doors or not curbs:
            raise RuntimeError('Missing door/curb in views')
        avg_door = (sum(x for x,_ in doors)/len(doors), sum(y for _,y in doors)/len(doors))
        avg_curb = (sum(x for x,_ in curbs)/len(curbs), sum(y for _,y in curbs)/len(curbs))
        # path
        path_pts = PathPlanner.shortest_path(
            avg_curb, avg_door,
            all_polys['walkways'], all_polys['obstacle']
        )
        # pixel distance
        total_px = sum(hypot(path_pts[i+1][0]-path_pts[i][0], path_pts[i+1][1]-path_pts[i][1]) for i in range(len(path_pts)-1))
        dist_m = total_px * self.SCALE_M_PER_PX
        # persist
        db=DatabaseHandler(); conn=db.conn
        loc_id=LocationRepository(conn).insert_location(
            address=self.slug.replace('-',' '), lat=0.0, lon=0.0,
            image_path=','.join(f"{self.slug}-{s}.jpg" for s in ('1','2'))
        )
        # store boundaries/obstacles
        for poly in all_polys['boundary']:
            wkt='POLYGON((' + ','.join(f"{x} {y}" for x,y in poly) + '))'
            BoundaryRepository(conn).add_boundary(loc_id,'boundary',wkt)
        for poly in all_polys['obstacle']:
            wkt='POLYGON((' + ','.join(f"{x} {y}" for x,y in poly) + '))'
            ObstacleRepository(conn).add_obstacle(loc_id,'obstacle',wkt)
        # features
        door_id=FeatureRepository(conn).add_feature(loc_id,avg_door[1],avg_door[0],'door')
        curb_id=FeatureRepository(conn).add_feature(loc_id,avg_curb[1],avg_curb[0],'curb')
        # route
        linestr='LINESTRING(' + ','.join(f"{x} {y}" for x,y in path_pts) + ')'
        RouteRepository(conn).insert_route(loc_id,curb_id,door_id,linestr,dist_m)
        db.close()
        return round(dist_m,8)