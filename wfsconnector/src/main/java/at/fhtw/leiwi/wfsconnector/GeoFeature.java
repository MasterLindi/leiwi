package at.fhtw.leiwi.wfsconnector;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Created by Christoph on 01.03.2015.
 */
public class GeoFeature {
    private String type;
    private Geometry polygon;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getPolygon() {
        return polygon;
    }

    public void setPolygon(Geometry polygon) {
        this.polygon = polygon;
    }
}
