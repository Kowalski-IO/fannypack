package io.kowalski.fannypack;

import io.kowalski.fannypack.uk.BumBag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TouristTest {

    private static final String SINGLE_QUERY = "src/test/resources/single.sql";
    private static final String MULTIPLE_QUERIES = "src/test/resources/multiple.sql";

    @Test
    void singleQueryTest() {
        Map<String, String> fp = FannyPack.fill(SINGLE_QUERY);

        assertNotNull(fp);
        assertEquals(fp.size(), 1);
        assertTrue(fp.containsKey("FakeCustomerQuery"));
        assertEquals(fp.get("FakeCustomerQuery"),
                "SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;");
    }

    @Test
    void britishSingleQueryTest() {
        Map<String, String> bb = BumBag.fill(SINGLE_QUERY);

        assertNotNull(bb);
        assertEquals(bb.size(), 1);
        assertTrue(bb.containsKey("FakeCustomerQuery"));
        assertEquals(bb.get("FakeCustomerQuery"),
                "SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;");
    }

    @Test
    void multipleQueryTest() {
        Map<String, String> fp = FannyPack.fill(MULTIPLE_QUERIES);

        assertNotNull(fp);
        assertEquals(fp.size(), 1);
        assertTrue(fp.containsKey("FakeCustomerQuery"));
        assertEquals(fp.get("FakeCustomerQuery"),
                "SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;");
    }

    @Test
    void britishQueryFileTest() {
        Map<String, String> bb = BumBag.fill(MULTIPLE_QUERIES);

        assertNotNull(bb);
        assertEquals(bb.size(), 1);
        assertTrue(bb.containsKey("FakeCustomerQuery"));
        assertEquals(bb.get("FakeCustomerQuery"),
                "SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;");
    }

}
