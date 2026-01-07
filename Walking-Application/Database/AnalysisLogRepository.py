# Database/AnalysisLogRepository.py
class AnalysisLogRepository:
    """
    Optional: log every analysis run for audit.
    """
    def __init__(self, conn):
        self.conn = conn

    def log_analysis(self, location_id, distance_m):
        """
        Inserts into analysis_log.
        """
        with self.conn.cursor() as cur:
            cur.execute("""
                INSERT INTO analysis_log (location_id, distance_meters)
                VALUES (%s, %s) RETURNING id
            """, (location_id, distance_m))
            log_id = cur.fetchone()[0]
        self.conn.commit()
        return log_id
