package at.fhtw.leiwi.wfsconnector;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Created by Christoph on 01.03.2015.
 */
public class GeoFeature {
    private String name;
    private Geometry polygon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getPolygon() {
        return polygon;
    }

    public void setPolygon(Geometry polygon) {
        this.polygon = polygon;
    }
}
