# Services/DoorCurbDetector.py
import os
import cv2

class DoorCurbDetector:
    """
    Detects door & curb pixel-centroids by reading:
      - a .jpg image (to get its width/height)
      - the corresponding Roboflow .txt label file with normalized polygons

    Class IDs in your .txt:
      1 → door
      3 → curb
    """

    DOOR_CLASS = 1
    CURB_CLASS = 3

    def __init__(self, image_path: str, label_path: str):
        self.image_path = image_path
        self.label_path = label_path

    def detect(self):
        """
        Returns two tuples: (door_x, door_y), (curb_x, curb_y).
        Falls back to (0,0) if nothing detected.
        """
        # load image to get its dimensions
        img = cv2.imread(self.image_path)
        if img is None:
            raise FileNotFoundError(f"Image not found: {self.image_path}")
        h, w = img.shape[:2]

        door_pts = []
        curb_pts = []

        # each line: cls x1 y1 x2 y2 ... normalized to [0–1]
        with open(self.label_path, "r") as f:
            for line in f:
                parts = line.strip().split()
                if not parts:
                    continue
                cls    = int(parts[0])
                coords = list(map(float, parts[1:]))

                # group into (nx,ny) pairs → actual pixel (x*w, y*h)
                pts = [
                    (coords[i] * w, coords[i+1] * h)
                    for i in range(0, len(coords), 2)
                ]
                if not pts:
                    continue

                # centroid of all polygon pts
                cx = sum(x for x, y in pts) / len(pts)
                cy = sum(y for x, y in pts) / len(pts)

                if cls == self.DOOR_CLASS:
                    door_pts.append((cx, cy))
                elif cls == self.CURB_CLASS:
                    curb_pts.append((cx, cy))

        # pick the first detection of each, or (0,0)
        door = door_pts[0] if door_pts else (0.0, 0.0)
        curb = curb_pts[0] if curb_pts else (0.0, 0.0)
        return door, curb
