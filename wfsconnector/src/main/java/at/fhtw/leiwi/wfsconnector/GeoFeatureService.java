package at.fhtw.leiwi.wfsconnector;

import java.util.List;

/**
 * Created by Christoph on 01.03.2015.
 */
public interface GeoFeatureService {

    public List<GeoFeature> getGeoFeature(String type);
}
