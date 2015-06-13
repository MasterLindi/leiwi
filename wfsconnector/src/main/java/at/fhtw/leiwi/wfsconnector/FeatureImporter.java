package at.fhtw.leiwi.wfsconnector;

import at.fhtw.leiwi.feature.dao.FeatureDao;
import at.fhtw.leiwi.feature.dao.FeatureDaoImpl;
import at.fhtw.leiwi.index.model.Katalog;
import at.fhtw.leiwi.laermdaten.dao.LaermdatenDao;
import at.fhtw.leiwi.laermdaten.dao.LaermdatenDaoImpl;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.FeatureType;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Markus on 25.05.2015.
 */
public class FeatureImporter {

    FeatureDao featureDao;
    LaermdatenDao laermdatenDao;

    public FeatureImporter(){
        try {
            featureDao = new FeatureDaoImpl();
            laermdatenDao = new LaermdatenDaoImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public final static String CapabilitiesString = "http://data.wien.gv.at/daten/geo?version=1.1.0&service=WFS&request=GetCapabilities";

    public Katalog createKatalogFromFeatureSourceDB(String wfsName, String type, SimpleFeature source, Double radius, int gewichtung) {
        List<Katalog> resultListDB = featureDao.selectKatalogListFromFeature(type);
        Katalog katalog = null;
        List<Katalog> resultList = new ArrayList();
        Double distanceResult = Double.parseDouble("0");
        for (Katalog katalogDB : resultListDB) {

                Geometry defaultGeometry = katalogDB.getDefaultGeometry();
                Double distanceSourceToFeature = defaultGeometry.distance((Geometry) source.getDefaultGeometry());
                if (distanceResult == 0) {
                    distanceResult = distanceSourceToFeature;
                }


                if (distanceSourceToFeature < distanceResult) {
                    distanceResult = distanceSourceToFeature;
                    katalog = new Katalog();
                    katalog.setId(katalogDB.getId());
                    katalog.setType(type);
                    katalog.setDefaultGeometry(defaultGeometry);
                    katalog.setDistanceFromSource(distanceResult);
                    if (distanceResult < radius / 4) {
                        katalog.setIndexBewertung(1);
                    } else if (distanceResult > (radius / 4) && distanceResult < (radius / 4 * 2)) {
                        katalog.setIndexBewertung(2);
                    } else if (distanceResult > (radius / 2) && distanceResult < (radius / 4 * 3)) {
                        katalog.setIndexBewertung(3);
                    } else if (distanceResult > (radius / 3) && distanceResult < (radius)) {
                        katalog.setIndexBewertung(4);
                    } else {
                        katalog.setIndexBewertung(5);
                    }

                }
            }
            katalog.setGewichtung(gewichtung);
            return katalog;
        }

    public Katalog createKatalogFromLaermdatenDB(SimpleFeature simpleFeature, Double radius, int gewichtung){
        Katalog katalog = laermdatenDao.selectNearestFeature(simpleFeature);
        Geometry defaultGeometry = katalog.getDefaultGeometry();
        Double distanceSourceToFeature = defaultGeometry.distance((Geometry) simpleFeature
                .getDefaultGeometry());
        katalog.setType("laermdaten");
        katalog.setDefaultGeometry(defaultGeometry);
        katalog.setDistanceFromSource(distanceSourceToFeature);
        Long decibel = Long.valueOf(katalog.getValue());
        if (decibel <= 50){
            katalog.setIndexBewertung(1);
        }
        else if (decibel > 50 && decibel < 60){
            katalog.setIndexBewertung(2);
        }else if (decibel >= 60 && decibel < 70){
            katalog.setIndexBewertung(3);
        }else if (decibel >= 70 && decibel < 80){
            katalog.setIndexBewertung(4);
        }else if (decibel >= 80){
            katalog.setIndexBewertung(5);
        }
        katalog.setGewichtung(gewichtung);
        return katalog;
    }

    private void importKatalogFromFeatureSource(String wfsName, String type){
        
        FeatureIterator<SimpleFeature> features = WfsConnector.getFeatures(CapabilitiesString, type);

        while(features.hasNext()) {
            SimpleFeature sf = features.next();
            featureDao.insertFeature(sf,type);
        }
    }

    public void importKatalogeFromWFS(){

        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:MARKTFLAECHEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:SCHULEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:UNIVERSITAETOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:VOLKSHOCHSCHULEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:KINDERGARTENOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:GARAGENOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:KRANKENHAUSOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:WOHNPFLEGEHAUSOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:WANDERWEGEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:SPORTSTAETTENOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:BADESTELLENOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:SPIELPLATZOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:FUSSGEHERZONEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:WOHNSTRASSEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:TEMPOZONEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:WLANWIENATOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:SCHWIMMBADOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:FAHRRADABSTELLANLAGEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:HALTESTELLEWLOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:PARKANLAGEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:OEFFHALTESTOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:CARSHARINGOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:CITYBIKEOGD");
        importKatalogFromFeatureSource(CapabilitiesString, "ogdwien:HUNDESACKERLOGD");



    }

    public void importLaermdaten(){
        try {
            LaermdatenDao laermdatenDao = new LaermdatenDaoImpl();
            File file = new File("C:\\Shapefiles\\STRASSE_24H_ZONEN_WI.shp");
            Map<String,URL> map = new HashMap<String,URL>();
            map.put( "url", file.toURL() );
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];

            FeatureSource source = dataStore.getFeatureSource(typeName);


            //Filter filter = CQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)");
            FeatureCollection<FeatureType, Feature> collection = source.getFeatures();
            FeatureIterator<Feature> iterator = collection.features();
            System.out.println("Anzahl: "+collection.size());


            while( iterator.hasNext() ){
                SimpleFeature feature = (SimpleFeature) iterator.next();

                laermdatenDao.insertLaermdaten(feature);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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