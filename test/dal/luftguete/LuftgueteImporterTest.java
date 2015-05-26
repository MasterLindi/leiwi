package dal.luftguete;

import model.GeoFeature;
import org.junit.Test;
import scala.collection.immutable.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Christoph on 26.05.2015.
 */
public class LuftgueteImporterTest {

    @Test
    public void testReadCsv_ReadLuftguete_ActualShouldBeTrue() {
        //ARRANGE
        LuftgueteImporter importer = new LuftgueteImporter();


        //ACT
        List<GeoFeature> actual = importer.importO3();

        //ASSERT
        assertEquals(87600, actual.size());
    }
}