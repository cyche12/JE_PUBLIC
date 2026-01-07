# Services/ImageProcessor.py
import base64
import cv2
import numpy as np

class ImageProcessor:
    """
    Decodes base64‐encoded uploads into OpenCV images.
    """
    def __init__(self, base64_data):
        self.image = self._decode(base64_data)

    def _decode(self, b64):
        raw = base64.b64decode(b64)
        arr = np.frombuffer(raw, np.uint8)
        img = cv2.imdecode(arr, cv2.IMREAD_COLOR)
        if img is None:
            raise ValueError("Decoded image is None.")
        return img

    def get_image(self):
        return self.image
