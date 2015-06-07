package at.fhtw.leiwi.index.model;

import com.vividsolutions.jts.geom.Geometry;

import java.io.Serializable;

/**
 * Created by Markus on 06.06.2015.
 */
public class Katalog implements Serializable{

    String id;
    String type;
    Geometry defaultGeometry;
    Double distanceFromSource;
    Integer indexBewertung;
    Integer gewichtung;
    Integer anzahl;

    public Double getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(Double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }


    public Integer getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Integer anzahl) {
        this.anzahl = anzahl;
    }

    public Integer getIndexBewertung() {
        return indexBewertung;
    }

    public void setIndexBewertung(Integer indexBewertung) {
        this.indexBewertung = indexBewertung;
    }

    public Geometry getDefaultGeometry() {
        return defaultGeometry;
    }

    public void setDefaultGeometry(Geometry defaultGeometry) {
        this.defaultGeometry = defaultGeometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGewichtung() {
        return gewichtung;
    }

    public void setGewichtung(Integer gewichtung) {
        this.gewichtung = gewichtung;
    }

    @Override
    public String toString() {
        return "Katalog{" +
                "type='" + type + '\'' +
                ", geometry=" + defaultGeometry +
                ", distanceFromSource=" + distanceFromSource +
                ", indexBewertung=" + indexBewertung +
                ", anzahl=" + anzahl +
                '}';
    }

    public String toStringAusgabe() {
        return "Katalog{" +
                "type='" + type + '\'' +
                ", distanceFromSource=" + distanceFromSource +
                ", indexBewertung=" + indexBewertung +
                '}';
    }
}
