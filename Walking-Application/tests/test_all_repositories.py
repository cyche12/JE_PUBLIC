import pytest
import psycopg2
from config import PG_HOST, PG_PORT, PG_DB, PG_USER, PG_PASSWORD

from Database.LocationRepository import LocationRepository
from Database.FeatureRepository import FeatureRepository
from Database.ObstacleRepository import ObstacleRepository
from Database.RouteRepository import RouteRepository
from Database.BoundaryRepository import BoundaryRepository
from Database.AnalysisLogRepository import AnalysisLogRepository

@pytest.fixture(scope="module")
def db_conn():
    conn = psycopg2.connect(
        host=PG_HOST,
        port=PG_PORT,
        dbname=PG_DB,
        user=PG_USER,
        password=PG_PASSWORD
    )
    yield conn
    conn.close()

def test_location_insert(db_conn):
    repo = LocationRepository(db_conn)
    loc_id = repo.insert_location(
        address="123 Test St",
        lat=45.0,
        lon=-75.0,
        image_path="test.jpg",
        postal_code="K1A1A1",
        city="Ottawa",
        province="ON"
    )
    assert isinstance(loc_id, int)

def test_feature_insert(db_conn):
    loc_repo = LocationRepository(db_conn)
    loc_id = loc_repo.insert_location("Feature Location", 45.1, -75.1, "f.jpg")

    feat_repo = FeatureRepository(db_conn)
    feat_id = feat_repo.add_feature(loc_id, 45.1, -75.1, "curb")
    assert isinstance(feat_id, int)

def test_obstacle_insert(db_conn):
    loc_repo = LocationRepository(db_conn)
    loc_id = loc_repo.insert_location("Obstacle Loc", 45.2, -75.2, "o.jpg")

    obs_repo = ObstacleRepository(db_conn)
    polygon_wkt = "POLYGON((-75.2 45.2, -75.2 45.3, -75.1 45.3, -75.1 45.2, -75.2 45.2))"
    obs_id = obs_repo.add_obstacle(loc_id, "fence", polygon_wkt)
    assert isinstance(obs_id, int)

def test_route_insert(db_conn):
    loc_repo = LocationRepository(db_conn)
    loc_id = loc_repo.insert_location("Route Loc", 45.3, -75.3, "r.jpg")

    feat_repo = FeatureRepository(db_conn)
    from_id = feat_repo.add_feature(loc_id, 45.3, -75.3, "curb")
    to_id = feat_repo.add_feature(loc_id, 45.31, -75.31, "door")

    route_repo = RouteRepository(db_conn)
    path_wkt = "LINESTRING(-75.3 45.3, -75.31 45.31)"
    route_id = route_repo.insert_route(loc_id, from_id, to_id, path_wkt, 15.5, 30)
    assert isinstance(route_id, int)

def test_boundary_insert(db_conn):
    loc_repo = LocationRepository(db_conn)
    loc_id = loc_repo.insert_location("Boundary Loc", 45.4, -75.4, "b.jpg")

    bnd_repo = BoundaryRepository(db_conn)
    polygon_wkt = "POLYGON((-75.4 45.4, -75.4 45.5, -75.3 45.5, -75.3 45.4, -75.4 45.4))"
    bnd_id = bnd_repo.add_boundary(loc_id, "test-boundary", polygon_wkt)
    assert isinstance(bnd_id, int)

def test_analysis_log(db_conn):
    loc_repo = LocationRepository(db_conn)
    loc_id = loc_repo.insert_location("Log Loc", 45.5, -75.5, "l.jpg")

    log_repo = AnalysisLogRepository(db_conn)
    log_id = log_repo.log_analysis(loc_id, 12.34)
    assert isinstance(log_id, int)
