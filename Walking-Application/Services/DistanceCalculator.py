# Services/DistanceCalculator.py
import math

class DistanceCalculator:
    """
    Converts Euclidean pixel distance into meters,
    using a constant scale (meters_per_pixel).
    """
    def __init__(self, scale_m_per_pixel: float = 0.25):
        self.scale = scale_m_per_pixel

    def compute(self, p1: tuple, p2: tuple) -> float:
        """
        p1, p2: (x, y) pixel coords
        Returns: distance in meters, or 0.0 if either is (0,0).
        """
        if p1 == (0.0, 0.0) or p2 == (0.0, 0.0):
            # missing detection
            return 0.0

        dx = p2[0] - p1[0]
        dy = p2[1] - p1[1]
        px_dist = math.hypot(dx, dy)
        return px_dist * self.scale
