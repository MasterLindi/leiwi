package at.fhtw.leiwi.wfsconnector;

import org.junit.Test;

/**
 * Created by Markus on 07.06.2015.
 */
public class FeatureImporterTest {

    @Test
    public void FeatureImporterTest(){
        FeatureImporter featureImporter = new FeatureImporter();
        featureImporter.importKatalogeFromWFS();
    }

    @Test
    public void testImportLaemdaten(){
        FeatureImporter featureImporter = new FeatureImporter();
        featureImporter.importLaermdaten();
    }
}
