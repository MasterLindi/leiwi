package at.fhtw.leiwi.feature.dao;

import at.fhtw.leiwi.index.model.Katalog;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;

/**
 * Created by Markus on 07.06.2015.
 */
public interface FeatureDao {

    void insertFeature(SimpleFeature simpleFeature, String type);

    List<Katalog> selectKatalogListFromFeature(String type);
}
