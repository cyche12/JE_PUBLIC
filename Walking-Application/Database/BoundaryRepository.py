# Database/BoundaryRepository.py
import json

class BoundaryRepository:
    """
    CRUD for 'boundaries' table (polygon)
    """
    def __init__(self, conn):
        self.conn = conn

    def add_boundary(self, location_id, label, polygon_wkt):
        """
        Inserts a boundary polygon.
        """
        with self.conn.cursor() as cur:
            cur.execute("""
                INSERT INTO boundaries (location_id, label, geom)
                VALUES (%s, %s, ST_GeomFromText(%s,4326))
                RETURNING id
            """, (location_id, label, polygon_wkt))
            bnd_id = cur.fetchone()[0]
        self.conn.commit()
        return bnd_id
