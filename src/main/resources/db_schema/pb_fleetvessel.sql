CREATE TABLE public.vessels (
	callsign varchar(255) NOT NULL,
	"timestamp" timestamptz(6) NOT NULL,
	draught float4 NOT NULL,
	geom public.geometry(geometry, 4326) GENERATED ALWAYS AS (st_setsrid(st_makepoint(longitude, latitude::double precision), 4326)::geometry) STORED NULL,
	heading float8 NULL,
	imo int4 NOT NULL,
	latitude float8 NULL,
	length float4 NOT NULL,
	longitude float8 NULL,
	mmsi int4 NOT NULL,
	"name" varchar(255) NULL,
	"type" int4 NOT NULL,
	width float4 NOT NULL,
	CONSTRAINT vessels_pkey PRIMARY KEY (callsign, "timestamp")
);
CREATE INDEX vessels_spatial_idx ON public.vessels USING gist (geom);
CREATE INDEX vessels_timestamp_idx ON public.vessels("timestamp");