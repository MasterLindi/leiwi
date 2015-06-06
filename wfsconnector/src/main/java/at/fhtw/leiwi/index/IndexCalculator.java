package at.fhtw.leiwi.index;

import at.fhtw.leiwi.index.model.IndexResult;
import at.fhtw.leiwi.index.model.Katalog;
import at.fhtw.leiwi.wfsconnector.FeatureImporter;
import org.opengis.feature.simple.SimpleFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 31.05.2015.
 */
public class IndexCalculator {

    public final static String CapabilitiesString = "http://data.wien.gv.at/daten/geo?version=1.1.0&service=WFS&request=GetCapabilities";



    public IndexResult calculateIndex(SimpleFeature adresseFeature, Double radius, String profil){
        IndexResult indexResult = new IndexResult();
        if (profil.equalsIgnoreCase("Allgemein")){
           return calculateAllgemeinProfile(adresseFeature,radius);
        }




        return indexResult;
    }

    private IndexResult calculateAllgemeinProfile(SimpleFeature adresseFeature, Double radius){
        IndexResult indexResult = new IndexResult();
        List<Katalog> katalogList = new ArrayList();
        FeatureImporter featureImporter = new FeatureImporter();

        //Wiener Märkte
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:MARKTFLAECHEOGD",adresseFeature,radius));
        //Schulen
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:SCHULEOGD",adresseFeature,radius));
        //Universitäten und Fachhochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:UNIVERSITAETOGD",adresseFeature,radius));
        //Volkshochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:VOLKSHOCHSCHULEOGD",adresseFeature,radius));
        //Kindergarten
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:KINDERGARTENOGD",adresseFeature,radius));
        //Öffentlich zugängliche Grünflächen
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:OEFFGRUENFLOGD",adresseFeature,radius));
        //Garagen
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:GARAGENOGD",adresseFeature,radius));
        //Krankenanstalten
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:KRANKENHAUSOGD",adresseFeature,radius));
        //Wohn-und Pflegehaus
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:WOHNPFLEGEHAUSOGD",adresseFeature,radius));
        //Wanderwege
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:WANDERWEGEOGD",adresseFeature,radius));




        for (Katalog katalog : katalogList) {
            System.out.println(katalog.toString());
        }
        return indexResult;
    }
}
