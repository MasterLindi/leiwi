# --- !Ups

CREATE TABLE Address (
    id UUID PRIMARY KEY,
    street varchar(255) NOT NULL,
    houseNr varchar(10) NOT NULL,
    zip int NOT NULL,
    district varchar(50) NOT NULL,
    coordinate GEOMETRY(Point, 4326),
    updateTime  TIMESTAMP NOT NULL
);

-- Add a spatial index
CREATE INDEX addr_coord_index
  ON Address
  USING GIST (coordinate);


# --- !Downs

DROP INDEX addr_coord_index;

DROP TABLE Address;