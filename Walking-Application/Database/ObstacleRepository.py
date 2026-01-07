# Database/ObstacleRepository.py
import json

class ObstacleRepository:
    """
    CRUD for 'obstacles' table (polygon)
    """
    def __init__(self, conn):
        self.conn = conn

    def add_obstacle(self, location_id, label, polygon_wkt):
        """
        Inserts an obstacle polygon.
        """
        with self.conn.cursor() as cur:
            cur.execute("""
                INSERT INTO obstacles (location_id, label, geom)
                VALUES (%s, %s, ST_GeomFromText(%s,4326))
                RETURNING id
            """, (location_id, label, polygon_wkt))
            obs_id = cur.fetchone()[0]
        self.conn.commit()
        return obs_id
