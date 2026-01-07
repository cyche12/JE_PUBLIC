# Database/FeatureRepository.py
class FeatureRepository:
    """
    CRUD for 'features' (door & curb points).
    """
    def __init__(self, conn):
        self.conn = conn

    def add_feature(self, location_id, latitude, longitude, ftype):
        """
        Inserts a feature and returns its ID.
        """
        with self.conn.cursor() as cur:
            cur.execute("""
                INSERT INTO features (
                    location_id, latitude, longitude, type
                ) VALUES (%s,%s,%s,%s)
                RETURNING id
            """, (location_id, latitude, longitude, ftype))
            feature_id = cur.fetchone()[0]
        self.conn.commit()
        return feature_id
