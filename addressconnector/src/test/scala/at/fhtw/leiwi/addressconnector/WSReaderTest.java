package at.fhtw.leiwi.addressconnector;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Christoph on 02.05.2015.
 */
public class WSReaderTest {

    @Test
    public void testRead() throws Exception {
        //ARRANGE
        WSReader wsReader = new WSReader();

        //ACT
        WSAddress actual = wsReader.read("Gesaugasse", String.valueOf(47));

        //ASSERT
        assertNotNull(actual);
    }
}