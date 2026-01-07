# config.py
"""
Central configuration for Flask, PostGIS, and Roboflow.
Loads secrets from a .env file in the same directory.
"""

import os
from pathlib import Path
from dotenv import load_dotenv

# ─── Load .env ───────────────────────────────────────────────────────────────
BASEDIR = Path(__file__).parent
load_dotenv(BASEDIR / ".env")

# ─── Postgres / PostGIS settings ─────────────────────────────────────────────
PG_HOST     = os.getenv("PG_HOST", "localhost")
PG_PORT     = int(os.getenv("PG_PORT", 5432))
PG_USER     = os.getenv("PG_USER", "postgres")
PG_PASSWORD = os.getenv("PG_PASSWORD", "")
PG_DB       = os.getenv("PG_DB", "walkingapp")

# Construct DATABASE_URL for psycopg2/SQLAlchemy
DATABASE_URL = os.getenv(
    "DATABASE_URL",
    f"postgresql://{PG_USER}:{PG_PASSWORD}@{PG_HOST}:{PG_PORT}/{PG_DB}"
)

# ─── Roboflow settings (if you ever integrate streaming) ──────────────────────
ROBOFLOW_API_KEY = os.getenv("ROBOFLOW_API_KEY", "")
ROBOFLOW_PROJECT = os.getenv("ROBOFLOW_PROJECT", "")
ROBOFLOW_MODEL   = os.getenv("ROBOFLOW_MODEL", "")
ROBOFLOW_VERSION = os.getenv("ROBOFLOW_VERSION", "")

# ─── Flask settings ──────────────────────────────────────────────────────────
FLASK_SECRET_KEY = os.getenv("FLASK_SECRET_KEY", "dev-secret")
DEBUG            = os.getenv("DEBUG", "False").lower() in ("1","true","yes")
