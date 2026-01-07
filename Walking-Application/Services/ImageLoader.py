# Services/ImageLoader.py
import os
import json
import re
import cv2

class ImageLoader:
    """
    Loads images & associated JSON masks from Static/train/images.
    """
    def __init__(self, image_dir=os.path.join("Static", "train", "images")):
        self.image_dir = image_dir

    def get_image_path(self, address, view):
        """
        view: "street" or "satellite"
        """
        slug   = address.replace(" ", "-")
        suffix = "-1.jpg" if view=="street" else "-2.jpg"
        path   = os.path.join(self.image_dir, f"{slug}{suffix}")
        return path if os.path.isfile(path) else None

    def load_image(self, path):
        img = cv2.imread(path)
        if img is None:
            raise FileNotFoundError(f"Image not found: {path}")
        return img

    def get_boundary_json(self, image_path):
        """
        If Roboflow exported a .json beside the .jpg, load it.
        """
        json_path = image_path.rsplit(".",1)[0] + ".json"
        if os.path.isfile(json_path):
            return json.load(open(json_path))
        return None
