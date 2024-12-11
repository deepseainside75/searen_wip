CREATE TABLE vessels (
    mmsi INT NOT NULL,
    imo INT NOT NULL,
    call_sign VARCHAR(255) NOT NULL,
    "timestamp" timestamptz NOT NULL,
    name VARCHAR(255) NOT NULL,
    type INT NOT NULL,
    width FLOAT NOT NULL,
    length FLOAT NOT NULL,
    draught FLOAT NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    heading FLOAT NOT NULL,
    geom public.geometry(geometry, 4326) GENERATED ALWAYS AS (st_setsrid(st_makepoint(longitude, latitude::double precision), 4326)::geometry) STORED NULL,
    CONSTRAINT vessels_pkey PRIMARY KEY (call_sign, "timestamp")
);
CREATE INDEX vessels_spatial_idx ON public.vessels USING gist (geom);