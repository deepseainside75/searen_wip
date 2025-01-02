-- public.oil_spill definition

-- Drop table

-- DROP TABLE public.oil_spill;

CREATE TABLE oil_spill (
	ident int8 NOT NULL,
	"timestamp" timestamptz NOT NULL,
	latitude float8 NOT NULL,
	longitude float8 NOT NULL,
	radius float4 NOT NULL,
	geom geometry(geometry, 4326) GENERATED ALWAYS AS (st_buffer(st_setsrid(st_makepoint(longitude, latitude::double precision), 4326)::geography, radius * 100.0::double precision)::geometry) STORED NULL,
	CONSTRAINT oil_spill_pkey PRIMARY KEY (ident, "timestamp")
);
CREATE INDEX oil_spill_spatial_idx ON oil_spill USING gist (geom);