# Database/LocationRepository.py
class LocationRepository:
    """
    CRUD for 'locations' table.
    """
    def __init__(self, conn):
        self.conn = conn

    def insert_location(self, address, lat, lon, image_path,
                        postal_code=None, city=None, province=None):
        """
        Inserts a new location and returns its ID.
        """
        with self.conn.cursor() as cur:
            cur.execute("""
                INSERT INTO locations (
                    address, latitude, longitude, image_path,
                    postal_code, city, province
                ) VALUES (%s,%s,%s,%s,%s,%s,%s)
                RETURNING id
            """, (address, lat, lon, image_path,
                  postal_code, city, province))
            loc_id = cur.fetchone()[0]
        self.conn.commit()
        return loc_id
