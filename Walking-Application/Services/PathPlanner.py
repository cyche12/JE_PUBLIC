from shapely.geometry import Point, Polygon, LineString, MultiPolygon
from shapely.ops import unary_union
import networkx as nx

class PathPlanner:
    """
    Builds a visibility graph within walkway areas,
    subtracts obstacles, and computes the shortest path
    from curb to door. Falls back to direct line.
    """
    @staticmethod
    def shortest_path(curb_pt, door_pt, walkways, obstacles):
        # build unioned walkway region
        walk_polys = [Polygon(poly).buffer(0) for poly in walkways]
        walk_union = unary_union(walk_polys)
        # subtract obstacles
        obs_union = unary_union([Polygon(poly).buffer(0) for poly in obstacles])
        free_space = walk_union.difference(obs_union)
        # direct fallback
        direct = LineString([curb_pt, door_pt])
        if free_space.contains(direct):
            return [curb_pt, door_pt]
        # collect visibility nodes
        nodes = [Point(curb_pt), Point(door_pt)]
        def collect(geom):
            if isinstance(geom, Polygon):
                for x,y in geom.exterior.coords:
                    nodes.append(Point((x,y)))
                for hole in geom.interiors:
                    for x,y in hole.coords:
                        nodes.append(Point((x,y)))
            elif isinstance(geom, MultiPolygon):
                for part in geom.geoms:
                    collect(part)
        collect(free_space)
        # build graph
        G = nx.Graph()
        for i,p in enumerate(nodes):
            G.add_node(i, point=p)
        for i, p_i in enumerate(nodes):
            for j, p_j in enumerate(nodes):
                if j <= i: continue
                seg = LineString([p_i, p_j])
                if free_space.contains(seg):
                    G.add_edge(i, j, weight=p_i.distance(p_j))
        # Dijkstra
        try:
            path_idxs = nx.shortest_path(G, 0, 1, weight='weight')
            return [(nodes[i].x, nodes[i].y) for i in path_idxs]
        except Exception:
            return [curb_pt, door_pt]