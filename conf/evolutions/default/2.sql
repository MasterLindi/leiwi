# --- !Ups

CREATE TABLE Feature (
    id varchar(50) PRIMARY KEY,
    coordinate GEOMETRY(Point, 4326),
    type varchar(50) NOT NULL
);

-- Add a spatial index
CREATE INDEX feature_coord_index
  ON Feature
  USING GIST (coordinate);


# --- !Downs

DROP INDEX feature_coord_index;

DROP TABLE Feature;