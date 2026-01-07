import geopandas as gpd
from sqlalchemy import create_engine
import os
from dotenv import load_dotenv

load_dotenv()

# Build your connection URI from .env
DB_URI = (
    f"postgresql://{os.getenv('DB_USER')}:"
    f"{os.getenv('DB_PASSWORD')}@{os.getenv('DB_HOST')}:"
    f"{os.getenv('DB_PORT')}/{os.getenv('DB_NAME')}"
)
engine = create_engine(DB_URI)

class PostGISExporter:
    """
    Exports analyzed features from main features table
    into new PostGIS tables for GIS consumption.
    """

    @staticmethod
    def export_features_to_postgis(ftype):
        """
        Export feature points (door or curb) to its own PostGIS table.
        """
        query = f"""
            SELECT f.id, l.address, f.type, f.latitude, f.longitude, f.geom
            FROM features f
            JOIN locations l ON f.location_id = l.id
            WHERE f.type = %s
        """
        gdf = gpd.read_postgis(query, engine, geom_col='geom', params=(ftype,))
        if gdf.empty:
            print(f"[WARNING] No features of type '{ftype}' found.")
            return

        # Write to new PostGIS table (overwrite if exists)
        table_name = f"{ftype}_features"
        gdf.to_postgis(name=table_name, con=engine, if_exists='replace', index=False)
        print(f"[✓] Saved {ftype} features to PostGIS table: {table_name}")

    @staticmethod
    def export_all_features():
        """
        Exports both door and curb features at once.
        """
        PostGISExporter.export_features_to_postgis("door")
        PostGISExporter.export_features_to_postgis("curb")


# Optional CLI support
if __name__ == '__main__':
    PostGISExporter.export_all_features()
