package at.fhtw.leiwi.wfsconnector;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.util.Assert;
import org.junit.Test;

/**
 * Created by cli on 08/03/15.
 */
public class GeoToolsTest {

    @Test
    public void testTransform_CoordinateTransformation_ValidEPSG4326Coordinates() throws Exception {
        Coordinate sourceCoordinate = new Coordinate(386914.9, 5815125.5);
        Coordinate destinatinCoordinate = GeoTools.transformFromEPSG25833(sourceCoordinate);

        Assert.equals(52.474559761329054, destinatinCoordinate.y);
        Assert.equals(13.335019810938043, destinatinCoordinate.x);
    }

    @Test
    public void testTransform_CoordinateTransformationFromSoldner_ValidEPSG4326Coordinates() throws Exception {
        Coordinate sourceCoordinate = new Coordinate(20267, 16417);
        Coordinate destinatinCoordinate = GeoTools.transformFromEPSG3068(sourceCoordinate);

        Assert.equals(52.47456033003066, destinatinCoordinate.y);
        Assert.equals(13.335011937224245, destinatinCoordinate.x);
    }

    @Test
    public void testTransform_TransformMultipolygonFromEPSG25833_ValidMultipoligonTransformed() {
        Coordinate[] coordinates1 = new Coordinate[] {new Coordinate(391770.41867678793, 5818762.285955451),
                new Coordinate(391939.3144173703, 5818541.991794965),
                new Coordinate(391949.2030496307, 5818530.314448107),
                new Coordinate(391770.41867678793, 5818762.285955451)};

        Coordinate[] coordinates2 = new Coordinate[] {new Coordinate(391963.45349625574, 5818533.633330617),
                new Coordinate(392086.89740254916, 5818610.078588096),
                new Coordinate(392176.73119361507, 5818400.082273831),
                new Coordinate(391963.45349625574, 5818533.633330617)};

        GeometryFactory gf = GeoTools.createGeometryFactory();
        Polygon polygon1 = gf.createPolygon(coordinates1);
        Polygon polygon2 = gf.createPolygon(coordinates2);
        MultiPolygon sourceMultipolygon = gf.createMultiPolygon(new Polygon[] {polygon1, polygon2});

        MultiPolygon actual = GeoTools.transformFromEPSG25833(sourceMultipolygon);

        Assert.equals(52.50822686411747, actual.getGeometryN(0).getCoordinates()[0].y);
        Assert.equals(13.40529605880141, actual.getGeometryN(0).getCoordinates()[0].x);

        Assert.equals(52.50628064648944, actual.getGeometryN(0).getCoordinates()[1].y);
        Assert.equals(13.407855154664874, actual.getGeometryN(0).getCoordinates()[1].x);

        Assert.equals(52.50617766527541, actual.getGeometryN(0).getCoordinates()[2].y);
        Assert.equals(13.40800458306358, actual.getGeometryN(0).getCoordinates()[2].x);

        Assert.equals(52.50822686411747, actual.getGeometryN(0).getCoordinates()[3].y);
        Assert.equals(13.40529605880141, actual.getGeometryN(0).getCoordinates()[3].x);

        Assert.equals(52.50621031491555, actual.getGeometryN(1).getCoordinates()[0].y);
        Assert.equals(13.408213380061655, actual.getGeometryN(1).getCoordinates()[0].x);

        Assert.equals(52.50692175241707, actual.getGeometryN(1).getCoordinates()[1].y);
        Assert.equals(13.410006618242702, actual.getGeometryN(1).getCoordinates()[1].x);

        Assert.equals(52.50505234597756, actual.getGeometryN(1).getCoordinates()[2].y);
        Assert.equals(13.411397732051297, actual.getGeometryN(1).getCoordinates()[2].x);

        Assert.equals(52.50621031491555, actual.getGeometryN(1).getCoordinates()[3].y);
        Assert.equals(13.408213380061655, actual.getGeometryN(1).getCoordinates()[3].x);
    }
}