package at.fhtw.leiwi.wfsconnector;

import com.google.common.base.Preconditions;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by cli on 08/03/15.
 */
public class GeoTools {

    private final static int SRID = 4326;

    public static String createWktGeometryString(Geometry geometry) {
        StringWriter stringWriter = new StringWriter();
        WKTWriter wktWriter = new WKTWriter(2);
        try {
            wktWriter.write(geometry, stringWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.format("SRID=%s;%s",SRID, stringWriter.toString());
    }

    public static Geometry parseWktGeometryString(String wktGeometry) {
        GeometryFactory geometryFactory = createGeometryFactory();
        WKTReader wktReader = new WKTReader(geometryFactory);
        String[] split = wktGeometry.split(";");
        Preconditions.checkArgument(split.length == 2);
        Preconditions.checkArgument(split[0].contains(String.valueOf(SRID)));
        try {
            Geometry read = wktReader.read(split[1]);
            read.setSRID(SRID);
            return read;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Coordinate transformFromEPSG3068(Coordinate sourceCoordinates) {
        try {
            CoordinateReferenceSystem sourceCrs = CRS.parseWKT("PROJCS[\"DHDN / Soldner Berlin\",GEOGCS[\"DHDN\"," +
                    "DATUM[\"Deutsches_Hauptdreiecksnetz\",SPHEROID[\"Bessel 1841\",6377397.155,299.1528128,AUTHORITY[\"EPSG\",\"7004\"]]," +
                    "AUTHORITY[\"EPSG\",\"6314\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328," +
                    "AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4314\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]]," +
                    "PROJECTION[\"Cassini_Soldner\"],PARAMETER[\"latitude_of_origin\",52.41864827777778],PARAMETER[\"central_meridian\"," +
                    "13.62720366666667],PARAMETER[\"false_easting\",40000],PARAMETER[\"false_northing\",10000],AUTHORITY[\"EPSG\",\"3068\"]," +
                    "AXIS[\"y\",EAST],AXIS[\"x\",NORTH]]");

            CoordinateReferenceSystem targetCrs = CRS.decode("EPSG:4326");

            boolean lenient = true;
            MathTransform mathTransform = CRS.findMathTransform(sourceCrs, targetCrs, lenient);

            DirectPosition2D srcDirectPosition2D = new DirectPosition2D(sourceCrs, sourceCoordinates.x, sourceCoordinates.y);
            DirectPosition2D destDirectPosition2D = new DirectPosition2D();
            mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);

            return new Coordinate(destDirectPosition2D.y, destDirectPosition2D.x);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Point transformFromEPSG3068(Double x, Double y) {
        if (x == null || y == null) { return null; }
        Coordinate soldnerCoord = new Coordinate(x, y);
        Coordinate transformedCoord = GeoTools.transformFromEPSG3068(soldnerCoord);
        return GeoTools.createPoint(transformedCoord);
    }

    public static Coordinate transformFromEPSG25833(Coordinate sourceCoordinates) {
        try {
            CoordinateReferenceSystem sourceCrs = CRS.decode("EPSG:25833");
            CoordinateReferenceSystem targetCrs = CRS.decode("EPSG:4326");

            boolean lenient = true;
            MathTransform mathTransform = CRS.findMathTransform(sourceCrs, targetCrs, lenient);

            DirectPosition2D srcDirectPosition2D = new DirectPosition2D(sourceCrs, sourceCoordinates.x, sourceCoordinates.y);
            DirectPosition2D destDirectPosition2D = new DirectPosition2D();
            mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);

            return new Coordinate(destDirectPosition2D.y, destDirectPosition2D.x);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Polygon transformFromEPSG25833(Polygon sourceCoordinates) {
        Coordinate[] exteriorRingCoordinates = new Coordinate[sourceCoordinates.getExteriorRing().getCoordinates().length];
        LinearRing[] holes = null;

        for (int i = 0; i < sourceCoordinates.getExteriorRing().getCoordinates().length; i++) {
            exteriorRingCoordinates[i] = transformFromEPSG25833(sourceCoordinates.getCoordinates()[i]);
        }

        holes = new LinearRing[sourceCoordinates.getNumInteriorRing()];
        for (int i = 0; i < sourceCoordinates.getNumInteriorRing(); i++) {
            holes[i] = transformFromEPSG25833(sourceCoordinates.getInteriorRingN(i));
        }

        GeometryFactory gf = createGeometryFactory();
        LinearRing exteriorRing = gf.createLinearRing(exteriorRingCoordinates);
        return gf.createPolygon(exteriorRing, holes);
    }

    public static LinearRing transformFromEPSG25833(LineString sourceLineString) {
        Coordinate[] targetCoordinates = new Coordinate[sourceLineString.getCoordinates().length];

        for (int i = 0; i < sourceLineString.getCoordinates().length; i++) {
            targetCoordinates[i] = transformFromEPSG25833(sourceLineString.getCoordinateN(i));
        }

        GeometryFactory gf = createGeometryFactory();
        return gf.createLinearRing(targetCoordinates);
    }

    public static MultiPolygon transformFromEPSG25833(MultiPolygon sourceGeometry) {
        Polygon[] tempMultipolygon = new Polygon[sourceGeometry.getNumGeometries()];

        for (int i = 0; i < sourceGeometry.getNumGeometries(); i++) {
            tempMultipolygon[i] = transformFromEPSG25833((Polygon) sourceGeometry.getGeometryN(i));
        }
        return createMultiPolygon(tempMultipolygon);
    }

    public static MultiPolygon createMultiPolygon(Polygon[] tempMultipolygon) {
        GeometryFactory gf = createGeometryFactory();
        return gf.createMultiPolygon(tempMultipolygon);
    }

    public static Point createPoint(double longitude, double latitude) {
        return createPoint(new Coordinate(longitude, latitude));
    }

    public static Point createPoint(Coordinate coordinate) {
        GeometryFactory gf = GeoTools.createGeometryFactory();
        return gf.createPoint(coordinate);
    }

    public static Geometry transformFromEPSG3068(MultiPolygon sourceGeometry) {
        Polygon[] tempMultipolygon = new Polygon[sourceGeometry.getNumGeometries()];

        for (int i = 0; i < sourceGeometry.getNumGeometries(); i++) {
            tempMultipolygon[i] = transformFromEPSG3068((Polygon) sourceGeometry.getGeometryN(i));
        }
        return createMultiPolygon(tempMultipolygon);
    }

    public static Polygon transformFromEPSG3068(Polygon sourceCoordinates) {
        Coordinate[] exteriorRingCoordinates = new Coordinate[sourceCoordinates.getExteriorRing().getCoordinates().length];
        LinearRing[] holes = null;

        for (int i = 0; i < sourceCoordinates.getExteriorRing().getCoordinates().length; i++) {
            exteriorRingCoordinates[i] = transformFromEPSG3068(sourceCoordinates.getCoordinates()[i]);
        }

        holes = new LinearRing[sourceCoordinates.getNumInteriorRing()];
        for (int i = 0; i < sourceCoordinates.getNumInteriorRing(); i++) {
            holes[i] = transformFromEPSG3068(sourceCoordinates.getInteriorRingN(i));
        }

        GeometryFactory gf = createGeometryFactory();
        LinearRing exteriorRing = gf.createLinearRing(exteriorRingCoordinates);
        return gf.createPolygon(exteriorRing, holes);
    }

    private static LinearRing transformFromEPSG3068(LineString sourceLineString) {
        Coordinate[] targetCoordinates = new Coordinate[sourceLineString.getCoordinates().length];

        for (int i = 0; i < sourceLineString.getCoordinates().length; i++) {
            targetCoordinates[i] = transformFromEPSG3068(sourceLineString.getCoordinateN(i));
        }

        GeometryFactory gf = createGeometryFactory();
        return gf.createLinearRing(targetCoordinates);
    }

    public static GeometryFactory createGeometryFactory() {
        return new GeometryFactory(new PrecisionModel(), 4326);
    }

    public static Double getLongitude(Geometry geometry){
        Point point = (Point) geometry;
        return point.getX();
    }

    public static Double getLattitude(Geometry geometry){
        Point point = (Point)geometry;
        return point.getY();
    }

}