import requests

def geocode_address(address):
    """
    Uses Nominatim API to geocode an address in Canada.
    """
    url = f"https://nominatim.openstreetmap.org/search?format=json&countrycodes=ca&q={address}"
    print(f"[INFO] Geocoding address: {address}")
    res = requests.get(url).json()
    if not res:
        raise ValueError(f"No result for address: {address}")
    lat, lon = float(res[0]['lat']), float(res[0]['lon'])
    print(f"[✓] Geocoded: {lat}, {lon}")
    return lat, lon
