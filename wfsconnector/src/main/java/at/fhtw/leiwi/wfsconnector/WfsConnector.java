package at.fhtw.leiwi.wfsconnector;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christoph on 01.03.2015.
 */
public class WfsConnector {

    public static FeatureIterator<SimpleFeature> getFeatures(String wfsName, String type) {
        try {
            Map connectionParameters = new HashMap();
            connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", wfsName);
            connectionParameters.put("WFSDataStoreFactory:USEDEFAULTSRS", "EPSG:");


            // Step 2 - connection
            DataStore data = DataStoreFinder.getDataStore(connectionParameters);

            // Step 3 - discouvery
            //           String typeNames[] = data.getTypeNames();
//            String typeName = typeNames[0];
            String typeName = type;
            SimpleFeatureType schema = data.getSchema(typeName);

            // Step 4 - target
            FeatureSource<SimpleFeatureType, SimpleFeature> source = data.getFeatureSource(typeName);
            return source.getFeatures().features();
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }
}
