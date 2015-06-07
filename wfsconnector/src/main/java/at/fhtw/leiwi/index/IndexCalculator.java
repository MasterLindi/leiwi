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
        //katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:OEFFGRUENFLOGD",adresseFeature,radius));
        //Garagen
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:GARAGENOGD",adresseFeature,radius));
        //Krankenanstalten
        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:KRANKENHAUSOGD",adresseFeature,radius));
//        //Wohn-und Pflegehaus
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:WOHNPFLEGEHAUSOGD",adresseFeature,radius));
//        //Wanderwege
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:WANDERWEGEOGD",adresseFeature,radius));
//        //Sportstätten
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:SPORTSTAETTENOGD",adresseFeature,radius));
//        //Badestellen
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:BADESTELLENOGD",adresseFeature,radius));
//        //Spielplätze
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:SPIELPLATZOGD",adresseFeature,radius));
//        //Fußgängerzonen
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:FUSSGEHERZONEOGD",adresseFeature,radius));
//        //Wohnstraßen
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:WOHNSTRASSEOGD",adresseFeature,radius));
//        //Wohnstraßen
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:TEMPOZONEOGD",adresseFeature,radius));
//        //Public WLAN
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:WLANWIENATOGD",adresseFeature,radius));
//        //Schwimmbad
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:SCHWIMMBADOGD",adresseFeature,radius));
//        //Schwimmbad
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:FAHRRADABSTELLANLAGEOGD",adresseFeature,radius));
//        //Haltestellen - Wiener Linien
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:HALTESTELLEWLOGD",adresseFeature,radius));
//        //Parkanlagen
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:PARKANLAGEOGD",adresseFeature,radius));
//        //Öffentliches Verkehrsnetz Haltestellenpunkte
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:OEFFHALTESTOGD",adresseFeature,radius));
//        //Carsharing
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:CARSHARINGOGD",adresseFeature,radius));
//        //Citybike
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:CITYBIKEOGD",adresseFeature,radius));
//        //Hundekotsackerlspender
//        katalogList.add(featureImporter.createKatalogFromFeatureSource(IndexCalculator.CapabilitiesString, "ogdwien:HUNDESACKERLOGD",adresseFeature,radius));


        double resultIndexBewertung = 0;
        for (Katalog katalog : katalogList) {
            resultIndexBewertung +=katalog.getIndexBewertung();
            System.out.println(katalog.toString());
        }

        double gesamt = resultIndexBewertung/new Double(katalogList.size());
        indexResult.setGesamtIndex(gesamt);
        System.out.println("Gesamtindex: "+ indexResult.getGesamtIndex());
        return indexResult;
    }
}
