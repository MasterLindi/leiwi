package at.fhtw.leiwi.index;

import at.fhtw.leiwi.Util;
import at.fhtw.leiwi.feature.dao.FeatureDaoImpl;
import at.fhtw.leiwi.index.model.Katalog;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.postgis.PGgeometry;

import java.sql.*;
import java.util.List;

/**
 * Created by Markus on 07.06.2015.
 */
public class PostGisTest {

    @Test
    public void testConnection(){
        java.sql.Connection conn;

        try {
    /*
    * Load the JDBC driver and establish a connection.
    */
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://vbpostgres:5432/leiwi";
            conn = DriverManager.getConnection(url, "leiwi", "leiwi");
    /*
    * Add the geometry types to the connection. Note that you
    * must cast the connection to the pgsql-specific connection
    * implementation before calling the addDataType() method.
    */
            ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
    /*
    * Create a statement and execute a select query.
    */
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("select * from feature");
            while( r.next() ) {
      /*
      * Retrieve the geometry as an object then cast it to the geometry type.
      * Print things out.
      */
                PGgeometry geom = (PGgeometry)r.getObject(2);
                String id = r.getString(1);
                System.out.println("Row " + id + ":");
                System.out.println(geom.toString());
            }
            s.close();
            conn.close();
        }
        catch( Exception e ) {
            e.printStackTrace();
        }




}

    @Test
    public void testInsertPostgis(){

        try {
            java.sql.Connection conn;
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://vbpostgres:5432/leiwi";
            conn = DriverManager.getConnection(url, "leiwi", "leiwi");
            ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
            SimpleFeature adresse = Util.createSimpleFeatureWithPoint(Double.parseDouble("339750.0"), Double.parseDouble("4781.0"), "Adresse");
            String geomsql ="INSERT INTO feature(id, coordinate,type) VALUES (?,ST_SetSRID(ST_MakePoint(?, ?), 4326),?)";
            PreparedStatement psSE= conn.prepareStatement(geomsql);

            Point point = (Point) adresse.getDefaultGeometry();

            Coordinate[] coordinateArray = point.getCoordinates();
            Coordinate coordinate = coordinateArray[0];
            org.postgis.Point pointDb = new org.postgis.Point(coordinate.x,coordinate.y);
            psSE.setString(1, "adresse_2");
            psSE.setDouble(2, coordinate.x);
            psSE.setDouble(3, coordinate.y);
            psSE.setString(4, "Adresse");

            int rows = psSE.executeUpdate();

            if (rows > 0) {
                System.out.println(" Successful insert! ");
            } else {
                System.out.println(" Failed insert!");
            }
            psSE.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testSelectKatalogFromFeature(){
        FeatureDaoImpl featureDAO = null;
        try {
            featureDAO = new FeatureDaoImpl();
            List<Katalog> resultList = featureDAO.selectKatalogListFromFeature("Adresse");

            for (Katalog katalog : resultList) {
                System.out.println(katalog.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
