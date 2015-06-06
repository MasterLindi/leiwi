package at.fhtw.leiwi.index.model;

import java.util.List;

/**
 * Created by Markus on 06.06.2015.
 */
public class IndexResult {


    List<Katalog> katalogList;

    Long gesamtIndex;

    public Long getGesamtIndex() {
        return gesamtIndex;
    }

    public void setGesamtIndex(Long gesamtIndex) {
        this.gesamtIndex = gesamtIndex;
    }

    public List<Katalog> getKatalogList() {
        return katalogList;
    }

    public void setKatalogList(List<Katalog> katalogList) {
        this.katalogList = katalogList;
    }




}
