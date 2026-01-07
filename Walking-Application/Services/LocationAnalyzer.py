```python
# Services/LocationAnalyzer.py
import base64
from Database.DatabaseHandler    import DatabaseHandler
from Database.LocationRepository import LocationRepository
from Database.FeatureRepository  import FeatureRepository
from Services.DoorCurbDetector   import DoorCurbDetector
from Services.DistanceCalculator import DistanceCalculator
from config import DATABASE_URL

class LocationAnalyzer:
    """
    Parses incoming JSON (with base64 image), runs detection,
    computes distance, and writes into PostGIS.
    """
    def __init__(self, base64_image, lat, lon, address, feature_type="door"):
        self.b64         = base64_image
        self.lat         = float(lat)
        self.lon         = float(lon)
        self.address     = address
        self.feature_type = feature_type

    def analyze(self) -> float:
        # Detect pixel coordinates
        detector = DoorCurbDetector()
        door_px, curb_px = detector.detect_from_base64(self.b64)

        # Compute distance in meters
        dist_m = DistanceCalculator().compute(door_px, curb_px)

        # Persist to database
        db        = DatabaseHandler(DATABASE_URL)
        loc_repo  = LocationRepository(db.conn)
        feat_repo = FeatureRepository(db.conn)

        # Insert location row
        loc_id = loc_repo.insert_location(
            address    = self.address,
            lat        = self.lat,
            lon        = self.lon,
            image_path = None,  # not saving as file
            postal_code=None,
            city=None,
            province=None
        )

        # Insert feature points: note ordering lat=y, lon=x
        feat_repo.add_feature(loc_id, door_px[1], door_px[0], "door")
        feat_repo.add_feature(loc_id, curb_px[1], curb_px[0], "curb")

        db.close()
        return dist_m