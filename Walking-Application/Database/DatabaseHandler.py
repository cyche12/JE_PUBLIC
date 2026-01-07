# Database/DatabaseHandler.py
import psycopg2
from config import DATABASE_URL

class DatabaseHandler:
    """
    Manages a psycopg2 connection to PostgreSQL/PostGIS.
    """
    def __init__(self):
        # use the full DATABASE_URL from config
        self.conn = psycopg2.connect(DATABASE_URL)

    def close(self):
        self.conn.close()
