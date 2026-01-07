import cv2

# Mapping from your data.yaml:
CLASS_MAP = {
    0: "building",
    1: "door",
    2: "boundaryof property",
    3: "curb",
    4: "obstacle",
    5: "walkways",
}

class LabelParser:
    """
    Parses a Roboflow .txt label file to extract:
      - Polygons for building, boundary, obstacle, walkways
      - Bottom-most door point
      - Centroid of curb polygon
    Coordinates returned are pixel-space (not normalized).
    """
    def __init__(self, image_path: str, label_path: str):
        self.image_path = image_path
        self.label_path = label_path

    def parse(self) -> dict:
        # load image to get dimensions
        img = cv2.imread(self.image_path)
        if img is None:
            raise FileNotFoundError(f"Image not found: {self.image_path}")
        h, w = img.shape[:2]

        # prepare containers
        polygons = {k: [] for k in ("building","boundaryof property","obstacle","walkways")}
        door_pts = []
        curb_pts = []

        with open(self.label_path, 'r') as fh:
            for line in fh:
                parts = line.strip().split()
                if len(parts) < 5:
                    continue
                cls_id = int(parts[0])
                coords = list(map(float, parts[1:]))
                # group into (x,y) and un-normalize
                pts = [
                    [coords[i] * w, coords[i+1] * h]
                    for i in range(0, len(coords), 2)
                ]
                cls_name = CLASS_MAP.get(cls_id)
                if cls_name == "door":
                    # bottom-most point
                    door_pts.append(max(pts, key=lambda p: p[1]))
                elif cls_name == "curb":
                    # centroid
                    cx = sum(x for x,_ in pts) / len(pts)
                    cy = sum(y for _,y in pts) / len(pts)
                    curb_pts.append([cx, cy])
                elif cls_name in polygons:
                    polygons[cls_name].append(pts)

        return {
            "polygons": polygons,
            "door": door_pts[0] if door_pts else [0.0, 0.0],
            "curb": curb_pts[0] if curb_pts else [0.0, 0.0],
        }