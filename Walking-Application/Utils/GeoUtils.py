import math

def haversine_distance(lat1, lon1, lat2, lon2):
    """
    Calculate the great-circle distance between two points
    on the Earth (specified in decimal degrees) using the Haversine formula.

    Returns:
        Distance in meters.
    """
    R = 6371000  # Earth's radius in meters

    # Convert decimal degrees to radians
    phi1, phi2 = math.radians(lat1), math.radians(lat2)
    delta_phi = phi2 - phi1
    delta_lambda = math.radians(lon2 - lon1)

    # Haversine formula
    a = math.sin(delta_phi / 2) ** 2 + math.cos(phi1) * math.cos(phi2) * math.sin(delta_lambda / 2) ** 2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

    distance = R * c
    return distance


def format_distance(meters):
    """
    Format a distance in meters to a user-friendly string in meters or kilometers.

    Returns:
        e.g. '850.00 m' or '1.23 km'
    """
    if meters < 1000:
        return f"{meters:.2f} m"
    else:
        return f"{meters / 1000:.2f} km"


def shift_latlon(lat, lon, meters_north=0.0, meters_east=0.0):
    """
    Approximate shifting of a geographic coordinate north/east by a specified
    distance in meters.

    Note: Uses simple equirectangular approximation suitable for small distances.

    Returns:
        (new_lat, new_lon)
    """
    # Latitude shift: roughly constant ~111.32 km/degree
    delta_lat = meters_north / 111320

    # Longitude shift varies with latitude (shrinks near poles)
    meters_per_degree_lon = 40075000 * math.cos(math.radians(lat)) / 360
    delta_lon = meters_east / meters_per_degree_lon

    new_lat = lat + delta_lat
    new_lon = lon + delta_lon

    return new_lat, new_lon
