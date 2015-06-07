package at.fhtw.leiwi.index;


import at.fhtw.leiwi.Util;
import at.fhtw.leiwi.index.model.IndexResult;
import junit.framework.Assert;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Created by Markus on 06.06.2015.
 */
public class IndexCalculatorTest {

    @Test
    public void testCalculateIndex(){

        SimpleFeature adresse =Util.createSimpleFeatureWithPoint(Double.parseDouble("339750.0"),Double.parseDouble("4781.0"),"Adresse");

        IndexCalculator indexCalculator = new IndexCalculator();
        IndexResult indexResult = indexCalculator.calculateIndex(adresse, Double.parseDouble("2500"), "Allgemein");

        Assert.assertNotNull(adresse);

    }


}
