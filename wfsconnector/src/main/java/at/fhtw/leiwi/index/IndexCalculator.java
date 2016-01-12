package at.fhtw.leiwi.index;

import at.fhtw.leiwi.Util;
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


    public IndexResult calculateIndex(SimpleFeature adresseFeature, Double radius, Util.Profiles profile){
        IndexResult indexResult = new IndexResult();
        if (profile.equals(Util.Profiles.ALLGEMEIN)){
           return calculateAllgemeinProfile(adresseFeature,radius);
        } if (profile.equals(Util.Profiles.MOBILITAET)){
            return calculateMobilitaetProfile(adresseFeature, radius);
        }if (profile.equals(Util.Profiles.FAMILIEN)){
            return calculateFamilienProfile(adresseFeature, radius);
        }if (profile.equals(Util.Profiles.STUDENTEN)){
            return calculateStudentenProfile(adresseFeature, radius);
        }if (profile.equals(Util.Profiles.PENSIONISTEN)){
            return calculatePensionistenProfile(adresseFeature, radius);
        }




        return indexResult;
    }

    private IndexResult calculateAllgemeinProfile(SimpleFeature adresseFeature, Double radius){
        IndexResult indexResult = new IndexResult();
        List<Katalog> katalogList = new ArrayList();
        FeatureImporter featureImporter = new FeatureImporter();

        //Wiener Maerkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:MARKTFLAECHEOGD",adresseFeature,radius,2));
        //Schulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SCHULEOGD",adresseFeature,radius,3));
        //Universitaeten und Fachhochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:UNIVERSITAETOGD",adresseFeature,radius,2));
        //Volkshochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:VOLKSHOCHSCHULEOGD",adresseFeature,radius,2));
        //Kindergarten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:KINDERGARTENOGD",adresseFeature,radius,3));
        //Oeffentlich zugaengliche Gruenflaechen
//        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFGRUENFLOGD",adresseFeature,radius));
        //Garagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:GARAGENOGD",adresseFeature,radius,2));
        //Krankenanstalten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:KRANKENHAUSOGD",adresseFeature,radius,3));
        //Wohn-und Pflegehaus
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WOHNPFLEGEHAUSOGD",adresseFeature,radius,3));
        //Wanderwege
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WANDERWEGEOGD",adresseFeature,radius,1));
        //Sportstaetten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SPORTSTAETTENOGD",adresseFeature,radius,1));
        //Badestellen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:BADESTELLENOGD",adresseFeature,radius,1));
        //Spielplaetze
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SPIELPLATZOGD",adresseFeature,radius,2));
        //Fussgaengerzonen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:FUSSGEHERZONEOGD",adresseFeature,radius,2));
        //Wohnstrassen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WOHNSTRASSEOGD",adresseFeature,radius,2));
        //Tempozone
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:TEMPOZONEOGD",adresseFeature,radius,1));
        //Public WLAN
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WLANWIENATOGD",adresseFeature,radius,1));
        //Schwimmbad
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SCHWIMMBADOGD",adresseFeature,radius,1));
        //Schwimmbad
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:FAHRRADABSTELLANLAGEOGD",adresseFeature,radius,1));
        //Haltestellen - Wiener Linien
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HALTESTELLEWLOGD",adresseFeature,radius,3));
        //Parkanlagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:PARKANLAGEOGD",adresseFeature,radius,2));
        //Oeffentliches Verkehrsnetz Haltestellenpunkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFHALTESTOGD",adresseFeature,radius,3));
//        //Carsharing
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:CARSHARINGOGD",adresseFeature,radius,1));
//        //Citybike
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:CITYBIKEOGD",adresseFeature,radius,1));
//        //Hundekotsackerlspender
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HUNDESACKERLOGD",adresseFeature,radius,1));
        //Laermdaten
        katalogList.add(featureImporter.createKatalogFromLaermdatenDB(adresseFeature,radius,3));

        indexResult.setGesamtIndex(gesamtindexBerechnung(katalogList));
        indexResult.setKatalogList(katalogList);
        System.out.println("Gesamtindex: "+ indexResult.getGesamtIndex());
        return indexResult;
    }

    private IndexResult calculateMobilitaetProfile(SimpleFeature adresseFeature, Double radius){
        IndexResult indexResult = new IndexResult();
        List<Katalog> katalogList = new ArrayList();
        FeatureImporter featureImporter = new FeatureImporter();

        //Wohnstrassen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WOHNSTRASSEOGD",adresseFeature,radius,2));
        //Tempozone
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:TEMPOZONEOGD",adresseFeature,radius,1));
        //Fahrradabstellanlage
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:FAHRRADABSTELLANLAGEOGD",adresseFeature,radius,1));
        //Haltestellen - Wiener Linien
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HALTESTELLEWLOGD",adresseFeature,radius,3));
        //Parkanlagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:PARKANLAGEOGD",adresseFeature,radius,2));
        //Oeffentliches Verkehrsnetz Haltestellenpunkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFHALTESTOGD",adresseFeature,radius,3));
//        //Carsharing
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:CARSHARINGOGD",adresseFeature,radius,1));
//        //Citybike
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:CITYBIKEOGD",adresseFeature,radius,1));

        indexResult.setGesamtIndex(gesamtindexBerechnung(katalogList));
        indexResult.setKatalogList(katalogList);
        System.out.println("Gesamtindex: "+ indexResult.getGesamtIndex());
        return indexResult;
    }

    private IndexResult calculateFamilienProfile(SimpleFeature adresseFeature, Double radius){
        IndexResult indexResult = new IndexResult();
        List<Katalog> katalogList = new ArrayList();
        FeatureImporter featureImporter = new FeatureImporter();

        //Wiener Maerkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:MARKTFLAECHEOGD",adresseFeature,radius,2));
        //Schulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SCHULEOGD",adresseFeature,radius,3));
        //Kindergarten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:KINDERGARTENOGD",adresseFeature,radius,3));
        //Oeffentlich zugaengliche Gruenflaechen
//        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFGRUENFLOGD",adresseFeature,radius));
        //Krankenanstalten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:KRANKENHAUSOGD",adresseFeature,radius,3));
        //Wanderwege
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WANDERWEGEOGD",adresseFeature,radius,1));
        //Sportstaetten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SPORTSTAETTENOGD",adresseFeature,radius,1));
        //Badestellen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:BADESTELLENOGD",adresseFeature,radius,1));
        //Spielplaetze
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SPIELPLATZOGD",adresseFeature,radius,2));
        //Fussgaengerzonen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:FUSSGEHERZONEOGD",adresseFeature,radius,2));
        //Wohnstrassen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WOHNSTRASSEOGD",adresseFeature,radius,2));
        //Tempozone
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:TEMPOZONEOGD",adresseFeature,radius,1));
        //Schwimmbad
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SCHWIMMBADOGD",adresseFeature,radius,1));
        //Haltestellen - Wiener Linien
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HALTESTELLEWLOGD",adresseFeature,radius,3));
        //Parkanlagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:PARKANLAGEOGD",adresseFeature,radius,2));
        //Oeffentliches Verkehrsnetz Haltestellenpunkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFHALTESTOGD",adresseFeature,radius,3));
        //Laermdaten
        katalogList.add(featureImporter.createKatalogFromLaermdatenDB(adresseFeature,radius,3));

        indexResult.setGesamtIndex(gesamtindexBerechnung(katalogList));
        indexResult.setKatalogList(katalogList);
        System.out.println("Gesamtindex: "+ indexResult.getGesamtIndex());
        return indexResult;
    }

    private IndexResult calculatePensionistenProfile(SimpleFeature adresseFeature, Double radius){
        IndexResult indexResult = new IndexResult();
        List<Katalog> katalogList = new ArrayList();
        FeatureImporter featureImporter = new FeatureImporter();

        //Wiener Maerkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:MARKTFLAECHEOGD",adresseFeature,radius,2));
        //Volkshochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:VOLKSHOCHSCHULEOGD",adresseFeature,radius,2));
        //Oeffentlich zugaengliche Gruenflaechen
//        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFGRUENFLOGD",adresseFeature,radius));
        //Garagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:GARAGENOGD",adresseFeature,radius,2));
        //Krankenanstalten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:KRANKENHAUSOGD",adresseFeature,radius,3));
        //Wohn-und Pflegehaus
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WOHNPFLEGEHAUSOGD",adresseFeature,radius,3));
        //Wanderwege
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WANDERWEGEOGD",adresseFeature,radius,1));
        //Badestellen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:BADESTELLENOGD",adresseFeature,radius,1));
        //Wohnstrassen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WOHNSTRASSEOGD",adresseFeature,radius,2));
        //Tempozone
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:TEMPOZONEOGD",adresseFeature,radius,1));
        //Schwimmbad
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SCHWIMMBADOGD",adresseFeature,radius,1));
        //Haltestellen - Wiener Linien
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HALTESTELLEWLOGD",adresseFeature,radius,3));
        //Parkanlagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:PARKANLAGEOGD",adresseFeature,radius,2));
        //Oeffentliches Verkehrsnetz Haltestellenpunkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFHALTESTOGD",adresseFeature,radius,3));
//        //Hundekotsackerlspender
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HUNDESACKERLOGD",adresseFeature,radius,1));
        //Laermdaten
        katalogList.add(featureImporter.createKatalogFromLaermdatenDB(adresseFeature,radius,3));

        indexResult.setGesamtIndex(gesamtindexBerechnung(katalogList));
        indexResult.setKatalogList(katalogList);
        System.out.println("Gesamtindex: "+ indexResult.getGesamtIndex());
        return indexResult;
    }

    private IndexResult calculateStudentenProfile(SimpleFeature adresseFeature, Double radius){
        IndexResult indexResult = new IndexResult();
        List<Katalog> katalogList = new ArrayList();
        FeatureImporter featureImporter = new FeatureImporter();
        //Wiener Maerkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:MARKTFLAECHEOGD",adresseFeature,radius,2));
        //Universitaeten und Fachhochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:UNIVERSITAETOGD",adresseFeature,radius,2));
        //Volkshochschulen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:VOLKSHOCHSCHULEOGD",adresseFeature,radius,2));
        //Oeffentlich zugaengliche Gruenflaechen
//        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFGRUENFLOGD",adresseFeature,radius));
        //Sportstaetten
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SPORTSTAETTENOGD",adresseFeature,radius,1));
        //Badestellen
        Katalog katalogFromFeatureSourceDB = featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:BADESTELLENOGD", adresseFeature, radius, 1);
        if (katalogFromFeatureSourceDB != null){
            katalogList.add(katalogFromFeatureSourceDB);
        }
        //Public WLAN
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:WLANWIENATOGD",adresseFeature,radius,1));
        //Schwimmbad
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:SCHWIMMBADOGD",adresseFeature,radius,1));
        //Fahrradabstellanlage
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:FAHRRADABSTELLANLAGEOGD",adresseFeature,radius,1));
        //Haltestellen - Wiener Linien
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:HALTESTELLEWLOGD",adresseFeature,radius,3));
        //Parkanlagen
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:PARKANLAGEOGD",adresseFeature,radius,2));
        //Oeffentliches Verkehrsnetz Haltestellenpunkte
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:OEFFHALTESTOGD",adresseFeature,radius,3));
//        //Citybike
        katalogList.add(featureImporter.createKatalogFromFeatureSourceDB(IndexCalculator.CapabilitiesString, "ogdwien:CITYBIKEOGD",adresseFeature,radius,1));
        //Laermdaten
        katalogList.add(featureImporter.createKatalogFromLaermdatenDB(adresseFeature,radius,3));


        indexResult.setGesamtIndex(gesamtindexBerechnung(katalogList));
        indexResult.setKatalogList(katalogList);
        System.out.println("Gesamtindex: "+ indexResult.getGesamtIndex());
        return indexResult;
    }

    private double gesamtindexBerechnung(List<Katalog> katalogList){
        double resultIndexBewertung = 0;
        int resultSize = katalogList.size();
        for (Katalog katalog : katalogList) {
            if (katalog != null) {
                resultIndexBewertung += katalog.getIndexBewertung();
                if (katalog.getGewichtung() == 2) {
                    resultIndexBewertung += katalog.getIndexBewertung();
                    resultSize++;
                } else if (katalog.getGewichtung() == 3) {
                    resultIndexBewertung += katalog.getIndexBewertung();
                    resultIndexBewertung += katalog.getIndexBewertung();
                    resultSize += 2;
                }

                System.out.println(katalog.toString());
            }
        }
        return resultIndexBewertung/new Double(resultSize);
    }
}
