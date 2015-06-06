package at.fhtw.leiwi.index.model;

import org.opengis.feature.simple.SimpleFeature;

/**
 * Created by Markus on 06.06.2015.
 */
public class Katalog {

    String type;
    SimpleFeature sf;
    Double distanceFromSource;
    Integer indexBewertung;
    Integer gewichtung;

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

    Integer anzahl;

    public SimpleFeature getSf() {
        return sf;
    }

    public void setSf(SimpleFeature sf) {
        this.sf = sf;
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
                ", sf=" + sf +
                ", distanceFromSource=" + distanceFromSource +
                ", indexBewertung=" + indexBewertung +
                ", anzahl=" + anzahl +
                '}';
    }
}
