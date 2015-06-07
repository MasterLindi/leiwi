package at.fhtw.leiwi.wfsconnector;

import at.fhtw.leiwi.feature.dao.FeatureDao;
import at.fhtw.leiwi.feature.dao.FeatureDaoImpl;
import at.fhtw.leiwi.index.model.Katalog;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeature;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Markus on 25.05.2015.
 */
public class FeatureImporter {

    FeatureDao featureDao;

    public FeatureImporter(){
        try {
            featureDao = new FeatureDaoImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public final static String CapabilitiesString = "http://data.wien.gv.at/daten/geo?version=1.1.0&service=WFS&request=GetCapabilities";

    public Katalog createKatalogFromFeatureSource(String wfsName, String type, SimpleFeature source, Double radius){
        
        FeatureIterator<SimpleFeature> features = WfsConnector.getFeatures(CapabilitiesString, type);
        Katalog katalog = null;
        List<Katalog> resultList = new ArrayList();
        Double distanceResult = Double.parseDouble("0");
        while(features.hasNext()) {
            SimpleFeature sf = features.next();
//            featureDao.insertFeature(sf,type);
            Geometry defaultGeometry = (Geometry) sf.getDefaultGeometry();
            Double distanceSourceToFeature = defaultGeometry.distance((Geometry) source.getDefaultGeometry());
            if (distanceResult == 0) {
                distanceResult = distanceSourceToFeature;
            }



            if (distanceSourceToFeature < distanceResult){
                distanceResult = distanceSourceToFeature;
                katalog = new Katalog();
                katalog.setId(sf.getID());
                katalog.setType(type);
                katalog.setDefaultGeometry(defaultGeometry);
                katalog.setDistanceFromSource(distanceResult);
                if (distanceResult < radius/4){
                    katalog.setIndexBewertung(1);
                }else if (distanceResult > (radius/4) && distanceResult < (radius/4*2)){
                    katalog.setIndexBewertung(2);
                }else if (distanceResult > (radius/2) && distanceResult < (radius/4*3)){
                    katalog.setIndexBewertung(3);
                }else if (distanceResult > (radius/3) && distanceResult < (radius)){
                    katalog.setIndexBewertung(4);
                }else{
                    katalog.setIndexBewertung(5);
                }

            }



        }

        return katalog;
    }

    public static void main(String[] args){

        try {
            FeatureIterator<SimpleFeature> features = WfsConnector.getFeatures("http://data.wien.gv.at/daten/geo?version=1.1.0&service=WFS&request=GetCapabilities", "ogdwien:KRANKENHAUSOGD");

            while(features.hasNext()){
                SimpleFeature sf = features.next();
                GeometryAttribute gemetryAttribute = sf.getDefaultGeometryProperty();

                //  Point defaultGeometry = (Point) sf.getDefaultGeometry();
                Geometry defaultGeometry = (Geometry) sf.getDefaultGeometry();

                System.out.println(sf.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}