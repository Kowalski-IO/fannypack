package io.kowalski.fannypack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TouristTest {

    private static final String SINGLE_QUERY = "src/test/resources/single.sql";
    private static final String MULTIPLE_QUERIES = "src/test/resources/multiple.sql";
    private static final String EXTRA_QUERIES = "src/test/resources/extra.sql";

    private static final String DOUBLE_MARKER = "src/test/resources/unparseable/double_marker.sql";
    private static final String MISSING_MARKER = "src/test/resources/unparseable/missing_marker.sql";

    @Test
    void singleQueryTest() {
        Map<String, String> fp = FannyPack.fill(SINGLE_QUERY);

        assertNotNull(fp);
        assertEquals(fp.size(), 1);

        assertTrue(fp.containsKey("FakeCustomerQuery"));
        assertEquals("SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;",
                fp.get("FakeCustomerQuery"));
    }

    @Test
    void multipleQueryTest() {
        Map<String, String> fp = FannyPack.fill(MULTIPLE_QUERIES);

        assertNotNull(fp);
        assertEquals(fp.size(), 2);

        assertTrue(fp.containsKey("InventoryList"));
        assertEquals("SELECT * FROM inventory;", fp.get("InventoryList"));

        assertTrue(fp.containsKey("InStock"));
        assertEquals("SELECT * FROM inventory WHERE stock > 0;", fp.get("InStock"));
    }

    @Test
    void multipleFilesTest() {
        Map<String, String> fp = FannyPack.fill(SINGLE_QUERY, MULTIPLE_QUERIES, EXTRA_QUERIES);

        assertNotNull(fp);
        assertEquals(fp.size(), 4);

        assertTrue(fp.containsKey("FakeCustomerQuery"));
        assertEquals("SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;",
                fp.get("FakeCustomerQuery"));

        assertTrue(fp.containsKey("InventoryList"));
        assertEquals("SELECT * FROM inventory;", fp.get("InventoryList"));

        assertTrue(fp.containsKey("InStock"));
        assertEquals("SELECT * FROM inventory WHERE stock > 0;", fp.get("InStock"));

        assertTrue(fp.containsKey("LongQuery"));
        assertEquals("SELECT id, username, email_address, password_hash, ip_address, first_name, " +
                        "last_name, address, company FROM users u INNER JOIN orders o ON u.id = orders.purchaser " +
                        "WHERE o.orderedOn > '2018-10-01' AND o.orderedOn < '2019-01-01' AND o.itemCount = 4 " +
                        "ORDER BY o.OrderedOn DESC, u.last_name ASC;",
                fp.get("LongQuery"));
    }

    @Test
    void unparseable() {
        Assertions.assertThrows(ParseException.class, () -> FannyPack.fill(DOUBLE_MARKER));
        Assertions.assertThrows(ParseException.class, () -> FannyPack.fill(MISSING_MARKER));
    }

}
