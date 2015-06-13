# --- !Ups

CREATE TABLE Laermdaten (
    id varchar(50) PRIMARY KEY,
    coordinate GEOMETRY(Point, 4326),
    decibel varchar(5)
);

-- Add a spatial index
CREATE INDEX laermdaten_coord_index
  ON Feature
  USING GIST (coordinate);


# --- !Downs

DROP INDEX feature_coord_index;

DROP TABLE Feature;