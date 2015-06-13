package at.fhtw.leiwi.laermdaten.dao;

import at.fhtw.leiwi.Util;
import at.fhtw.leiwi.index.model.Katalog;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

import java.sql.SQLException;

/**
 * Created by Markus on 13.06.2015.
 */
public class LaermdatenDaoImplTest {

    @Test
    public void testSelectNearestFeature(){
        SimpleFeature adresse = Util.createSimpleFeatureWithPoint(Double.parseDouble("339750.0"), Double.parseDouble("4781.0"), "Adresse");
        try {
            LaermdatenDao laermdatenDao = new LaermdatenDaoImpl();

            Katalog katalog = laermdatenDao.selectNearestFeature(adresse);
            System.out.println(katalog);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
