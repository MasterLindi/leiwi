package at.fhtw.leiwi.addressconnector;

import org.junit.Test;
import scala.collection.immutable.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Christoph on 06.04.2015.
 */
public class CSVReaderTest{

    @Test
    public void testReadCSV_ReadAllStreets_ActualShouldBeTrue(){
        //ARRANGE
        CSVReader reader = new CSVReader();


        //ACT
        List<String> actual = reader.readCsv();

        //ASSERT
        assertEquals(11999, actual.size());
        assertEquals("Radgarage Kennedybr\u00FCcke", actual.head());
        assertEquals("Tempelgrabensteg", actual.last());
    }
}