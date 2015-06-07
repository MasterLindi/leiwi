package at.fhtw.leiwi.index.model;

import java.util.List;

/**
 * Created by Markus on 06.06.2015.
 */
public class IndexResult {


    List<Katalog> katalogList;

    Double gesamtIndex;

    public Double getGesamtIndex() {
        return gesamtIndex;
    }

    public void setGesamtIndex(Double gesamtIndex) {
        this.gesamtIndex = gesamtIndex;
    }

    public List<Katalog> getKatalogList() {
        return katalogList;
    }

    public void setKatalogList(List<Katalog> katalogList) {
        this.katalogList = katalogList;
    }




}
