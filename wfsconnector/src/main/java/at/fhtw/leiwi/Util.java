package at.fhtw.leiwi;

import at.fhtw.leiwi.wfsconnector.GeoTools;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * Created by Markus on 06.06.2015.
 */
public class Util {

    public enum Profiles {
        ALLGEMEIN, MOBILITAET, FAMILIEN, STUDENTEN, PENSIONISTEN
    }

    public static SimpleFeatureType createFeatureType() {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system

        // add attributes in order
        builder.add("Location", Point.class);
        builder.length(15).add("Name", String.class); // <- 15 chars width for name field

        // build the type
        final SimpleFeatureType LOCATION = builder.buildFeatureType();

        return LOCATION;
    }

    public static SimpleFeature createSimpleFeatureWithPoint(Double lon, Double lat, String name){
        SimpleFeatureType featureType = Util.createFeatureType();

        //create the builder
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(featureType);
        Coordinate coord=new Coordinate(lon,lat);
        Point point = GeoTools.transformToEPSG31256(coord);
//        GeometryFactory geometryFactory=new GeometryFactory();
//        Coordinate coord=new Coordinate(lon,lat);
//        Point point=geometryFactory.createPoint(coord);
        //add the attributes
        builder.add( point );
        builder.add( name );

        //build the feature
        return builder.buildFeature( name );
    }

}
