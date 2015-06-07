package at.fhtw.leiwi.feature.dao;

import at.fhtw.leiwi.index.model.Katalog;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.postgis.PGgeometry;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 07.06.2015.
 */
public class FeatureDaoImpl implements FeatureDao {

    java.sql.Connection conn;

    public FeatureDaoImpl() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://vbpostgres:5432/leiwi";
        conn = DriverManager.getConnection(url, "leiwi", "leiwi");
    }

    @Override
    public void insertFeature(SimpleFeature simpleFeature, String type){

        try {
            ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));

            String geomsql ="INSERT INTO feature(id, coordinate,type) VALUES (?,ST_SetSRID(ST_MakePoint(?, ?), 4326),?)";
            PreparedStatement psSE= null;
            psSE = conn.prepareStatement(geomsql);
        //    Point point = (Point) simpleFeature.getDefaultGeometry();
            Polygon polygon = (Polygon)simpleFeature.getDefaultGeometry();
            Coordinate[] coordinateArray = polygon.getCoordinates();
            Coordinate coordinate = coordinateArray[0];
            org.postgis.Point pointDb = new org.postgis.Point(coordinate.x,coordinate.y);
            psSE.setString(1, simpleFeature.getID());
            psSE.setDouble(2, coordinate.x);
            psSE.setDouble(3, coordinate.y);
            psSE.setString(4, type);

            int rows = psSE.executeUpdate();

            if (rows > 0) {
                System.out.println(" Successful insert! ");
            } else {
                System.out.println(" Failed insert!");
            }
            psSE.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Katalog> selectKatalogListFromFeature(String type){


    /*
    * Create a statement and execute a select query.
    */
        List<Katalog> resultList = new ArrayList();
        try {

            ((org.postgresql.PGConnection) conn).addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
            ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));


            String geomsql ="select * from feature where type = ?";
            PreparedStatement psSE= null;
            psSE = conn.prepareStatement(geomsql);
            psSE.setString(1, type);
            ResultSet r = psSE.executeQuery();
            while( r.next() ) {

                Katalog katalog = new Katalog();
                katalog.setId(r.getString(1));
                PGgeometry geom = (PGgeometry)r.getObject(2);

                GeometryFactory geometryFactory=new GeometryFactory();
                Coordinate coord=new Coordinate(geom.getGeometry().getFirstPoint().x,geom.getGeometry().getFirstPoint().y);
                Point point=geometryFactory.createPoint(coord);
                katalog.setDefaultGeometry(point);
                katalog.setType(r.getString(3));
                resultList.add(katalog);

            }
            psSE.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
