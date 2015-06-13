package at.fhtw.leiwi.laermdaten.dao;

import at.fhtw.leiwi.index.model.Katalog;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Created by Markus on 07.06.2015.
 */
public interface LaermdatenDao {

    public void insertLaermdaten(SimpleFeature simpleFeature);

    Katalog selectNearestFeature(SimpleFeature simpleFeature);
}
