package at.fhtw.leiwi.wfsconnector;

import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;

/**
 * Created by Christoph on 01.03.2015.
 */
public class GeoFeatureServiceImpl implements GeoFeatureService {

    @Override
    public List<GeoFeature> getGeoFeature() {
        String getCapabilities = "http://data.wien.gv.at/daten/geo?version=1.1.0&service=WFS&request=GetCapabilities";
        FeatureIterator<SimpleFeature> features = WfsConnector.getFeatures(getCapabilities);
        return null;
    }
}
