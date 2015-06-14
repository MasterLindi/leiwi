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
//Landstrasse Haupstrasse
        //SimpleFeature adresse =Util.createSimpleFeatureWithPoint(Double.parseDouble("339750.0"),Double.parseDouble("4781.0"),"Adresse");
      //  SimpleFeature adresse =Util.createSimpleFeatureWithPoint(Double.parseDouble("48.196143"),Double.parseDouble("16.396448"),"Adresse");
    //Marizaweg 1
      //  SimpleFeature adresse =Util.createSimpleFeatureWithPoint(Double.parseDouble("333455.0"),Double.parseDouble("3701.0"),"Adresse");
        SimpleFeature adresse =Util.createSimpleFeatureWithPoint(Double.parseDouble("48.139417"),Double.parseDouble("16.381728"),"Adresse");

        //Giefinggasse 6
    //    SimpleFeature adresse =Util.createSimpleFeatureWithPoint(Double.parseDouble("347909.0"),Double.parseDouble("7035.0"),"Adresse");

        IndexCalculator indexCalculator = new IndexCalculator();
        IndexResult indexResult = indexCalculator.calculateIndex(adresse, Double.parseDouble("2000"), Util.Profiles.PENSIONISTEN);

        Assert.assertNotNull(adresse);

    }


}
