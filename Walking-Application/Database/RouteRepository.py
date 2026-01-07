# Database/RouteRepository.py
class RouteRepository:
    """
    CRUD for 'routes' table (LineString)
    """
    def __init__(self, conn):
        self.conn = conn

    def insert_route(self, location_id, from_feature_id, to_feature_id,
                     path_wkt, distance_m, estimated_time_s=None):
        """
        Inserts a new route and returns its ID.
        """
        with self.conn.cursor() as cur:
            cur.execute("""
                INSERT INTO routes (
                    location_id, from_feature_id, to_feature_id,
                    path, distance_m, estimated_time_s
                ) VALUES (%s,%s,%s,ST_GeomFromText(%s,4326),%s,%s)
                RETURNING id
            """, (location_id, from_feature_id, to_feature_id,
                  path_wkt, distance_m, estimated_time_s))
            route_id = cur.fetchone()[0]
        self.conn.commit()
        return route_id
