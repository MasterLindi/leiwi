package at.fhtw.leiwi.laermdaten.dao;

import at.fhtw.leiwi.index.model.Katalog;
import com.vividsolutions.jts.geom.*;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.postgis.PGgeometry;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.postgis.PGgeometry;

/**
 * Created by Markus on 07.06.2015.
 */
public class LaermdatenDaoImpl implements LaermdatenDao {

    java.sql.Connection conn;

    public LaermdatenDaoImpl() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://vbpostgres:5432/leiwi";
        conn = DriverManager.getConnection(url, "leiwi", "leiwi");
    }

    @Override
    public void insertLaermdaten(SimpleFeature simpleFeature){

        try {
//            ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
//            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));

            String geomsql ="INSERT INTO laermdaten(id, coordinate,decibel) VALUES (?,ST_SetSRID(ST_MakePoint(?, ?), 4326),?)";
            PreparedStatement psSE= null;
            psSE = conn.prepareStatement(geomsql);
            Coordinate[] coordinateArray = null;
            if (simpleFeature.getDefaultGeometry() instanceof Point) {
                Point point = (Point) simpleFeature.getDefaultGeometry();
                coordinateArray = point.getCoordinates();
            }else if (simpleFeature.getDefaultGeometry() instanceof Polygon) {
                Polygon polygon = (Polygon)simpleFeature.getDefaultGeometry();
                coordinateArray = polygon.getCoordinates();
            }else if (simpleFeature.getDefaultGeometry() instanceof LineString) {
                LineString lineString = (LineString)simpleFeature.getDefaultGeometry();
                coordinateArray = lineString.getCoordinates();
            }else if (simpleFeature.getDefaultGeometry() instanceof MultiLineString) {
                MultiLineString lineString = (MultiLineString)simpleFeature.getDefaultGeometry();
                coordinateArray = lineString.getCoordinates();
            }else if (simpleFeature.getDefaultGeometry() instanceof MultiPolygon) {
                MultiPolygon lineString = (MultiPolygon)simpleFeature.getDefaultGeometry();
                coordinateArray = lineString.getCoordinates();
            }

            Coordinate coordinate = coordinateArray[0];
//            org.postgis.Point pointDb = new org.postgis.Point(coordinate.x,coordinate.y);
            psSE.setString(1, simpleFeature.getID());
            psSE.setDouble(2, coordinate.x);
            psSE.setDouble(3, coordinate.y);

            Long decibel = Long.valueOf(0);
            for (Property property : simpleFeature.getValue())
                if (property.getValue() instanceof Long) {
                    decibel = (Long) property.getValue();
                    break;
                }
            ;
            psSE.setString(4, decibel.toString());
            int rows = psSE.executeUpdate();

            if (rows > 0) {
                System.out.println(" Successful insert! ");
            } else {
                System.out.println(" Failed insert!");
            }
            psSE.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
        }
    }

    @Override
    public Katalog selectNearestFeature(SimpleFeature simpleFeature){


    /*
    * Create a statement and execute a select query.
    */  Katalog katalog = null;
        try {

//            ((org.postgresql.PGConnection) conn).addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
//            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));


            String geomsql ="\n" +
                    "SELECT *\n" +
                    "FROM laermdaten \n" +
                    "ORDER BY laermdaten.coordinate <-> ST_SetSRID(ST_MakePoint(?, ?), 4326)\n" +
                    "LIMIT 1;";
            PreparedStatement psSE= null;
            psSE = conn.prepareStatement(geomsql);

            Point point = (Point) simpleFeature.getDefaultGeometry();
            Coordinate[] coordinateArray = point.getCoordinates();
            Coordinate coordinate = coordinateArray[0];
            psSE.setDouble(1, coordinate.x);
            psSE.setDouble(2, coordinate.y);
            ResultSet r = psSE.executeQuery();
            while( r.next() ) {

                katalog = new Katalog();
                katalog.setId(r.getString(1));
                PGgeometry geom = (PGgeometry)r.getObject(2);

                GeometryFactory geometryFactory=new GeometryFactory();
                Coordinate coord=new Coordinate(geom.getGeometry().getFirstPoint().x,geom.getGeometry().getFirstPoint().y);
                Point pointDB=geometryFactory.createPoint(coord);
                katalog.setDefaultGeometry(pointDB);
                katalog.setValue(r.getString(3));


            }
            psSE.close();


        } catch (SQLException e) {
            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }

        return katalog;
    }
}
