import pytest
import os
import geopandas as gpd
from sqlalchemy import create_engine, inspect
from export_features import export_features_to_postgis

# Load DB connection info from .env
from dotenv import load_dotenv
load_dotenv()

DB_URI = f"postgresql://{os.getenv('DB_USER')}:{os.getenv('DB_PASSWORD')}@{os.getenv('DB_HOST')}:{os.getenv('DB_PORT')}/{os.getenv('DB_NAME')}"
engine = create_engine(DB_URI)

@pytest.mark.parametrize("ftype", ["door", "curb"])
def test_export_features_to_postgis(ftype):
    # Run the export
    export_features_to_postgis(ftype)

    # Check that the table was created
    inspector = inspect(engine)
    table_name = f"{ftype}_features"
    assert table_name in inspector.get_table_names(), f"Table '{table_name}' not found in database."

    # Check that it has at least 1 row
    gdf = gpd.read_postgis(f"SELECT * FROM {table_name}", engine, geom_col="geom")
    assert not gdf.empty, f"Exported table '{table_name}' is empty."

    # (Optional) Clean up table after test
    with engine.begin() as conn:
        conn.execute(f'DROP TABLE IF EXISTS {table_name}')
