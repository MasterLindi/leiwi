package at.fhtw.leiwi.wfsconnector;

import com.vividsolutions.jts.geom.Geometry;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christoph on 01.03.2015.
 */
public class GeoFeatureServiceImpl implements GeoFeatureService {

    @Override
    public List<GeoFeature> getGeoFeature(String type) {
        String getCapabilities = "http://data.wien.gv.at/daten/geo?version=1.1.0&service=WFS&request=GetCapabilities";
        FeatureIterator<SimpleFeature> features = WfsConnector.getFeatures(getCapabilities, type);
        List<GeoFeature> resultList = new ArrayList<>();
        while(features.hasNext()){
            SimpleFeature sf = features.next();
            GeoFeature geoFeature = new GeoFeature();

            Geometry defaultGeometry = (Geometry) sf.getDefaultGeometry();
            geoFeature.setPolygon(defaultGeometry);
            geoFeature.setType(type);

            System.out.println(sf.toString());
        }

        return null;
    }
}
