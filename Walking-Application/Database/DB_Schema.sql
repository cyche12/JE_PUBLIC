-- Enable PostGIS extension
CREATE EXTENSION IF NOT EXISTS postgis;

-- Drop tables in dependency-safe order
DROP TABLE IF EXISTS
    analysis_log,
    boundaries,
    routes,
    obstacles,
    features,
    locations
CASCADE;

-- Table: locations
CREATE TABLE IF NOT EXISTS locations (
    id SERIAL PRIMARY KEY,
    address TEXT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    image_path TEXT,
    postal_code TEXT,
    city TEXT,
    province TEXT,
    geom GEOMETRY(Point, 4326) GENERATED ALWAYS AS (
        ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
    ) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: features
CREATE TABLE IF NOT EXISTS features (
    id SERIAL PRIMARY KEY,
    location_id INTEGER NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
    type TEXT NOT NULL CHECK (type IN ('curb', 'door')),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    geom GEOMETRY(Point, 4326) GENERATED ALWAYS AS (
        ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
    ) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_features_geom ON features USING GIST (geom);

-- Table: obstacles
CREATE TABLE IF NOT EXISTS obstacles (
    id SERIAL PRIMARY KEY,
    location_id INTEGER NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
    label TEXT NOT NULL,
    geom GEOMETRY(Polygon, 4326) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_obstacles_geom ON obstacles USING GIST (geom);

-- Table: routes
CREATE TABLE IF NOT EXISTS routes (
    id SERIAL PRIMARY KEY,
    location_id INTEGER NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
    from_feature_id INTEGER NOT NULL REFERENCES features(id) ON DELETE CASCADE,
    to_feature_id INTEGER NOT NULL REFERENCES features(id) ON DELETE CASCADE,
    path GEOMETRY(LineString, 4326) NOT NULL,
    distance_m DOUBLE PRECISION NOT NULL,
    estimated_time_s DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_routes_path ON routes USING GIST (path);

-- Table: boundaries
CREATE TABLE IF NOT EXISTS boundaries (
    id SERIAL PRIMARY KEY,
    location_id INTEGER NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
    label TEXT NOT NULL,
    geom GEOMETRY(Polygon, 4326) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_boundaries_geom ON boundaries USING GIST (geom);

-- Table: analysis_log
CREATE TABLE IF NOT EXISTS analysis_log (
    id SERIAL PRIMARY KEY,
    location_id INTEGER NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
    distance_meters DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
