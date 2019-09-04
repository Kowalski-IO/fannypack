package io.kowalski.fannypack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UKTouristTest {

    private static final String SINGLE_QUERY = "src/test/resources/single.sql";
    private static final String MULTIPLE_QUERIES = "src/test/resources/multiple.sql";
    private static final String EXTRA_QUERIES = "src/test/resources/extra.sql";

    private static final String DOUBLE_MARKER = "src/test/resources/unparseable/double_marker.sql";
    private static final String MISSING_MARKER = "src/test/resources/unparseable/missing_marker.sql";
    private static final String FILE_NOT_FOUND = "src/test/resources/unparseable/iamnothere.sql";

    @Test
    void singleQueryTest() {
        BumBag bb = BumBag.fill(SINGLE_QUERY);

        assertNotNull(bb);
        assertEquals(bb.size(), 1);

        assertTrue(bb.queryExists("FakeCustomerQuery"));
        assertEquals("SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;",
                bb.get("FakeCustomerQuery"));
    }

    @Test
    void multipleQueryTest() {
        BumBag bb = BumBag.fill(MULTIPLE_QUERIES);

        assertNotNull(bb);
        assertEquals(bb.size(), 2);

        assertTrue(bb.queryExists("InventoryList"));
        assertEquals("SELECT * FROM inventory;", bb.get("InventoryList"));

        assertTrue(bb.queryExists("InStock"));
        assertEquals("SELECT * FROM inventory WHERE stock > 0;", bb.get("InStock"));
    }

    @Test
    void multipleFilesTest() {
        BumBag bb = BumBag.fill(SINGLE_QUERY, MULTIPLE_QUERIES, EXTRA_QUERIES);

        assertNotNull(bb);
        assertEquals(bb.size(), 4);

        assertTrue(bb.queryExists("FakeCustomerQuery"));
        assertEquals("SELECT id, name, address, phoneNumber FROM customers WHERE id = 123 ORDER BY name ASC;",
                bb.get("FakeCustomerQuery"));

        assertTrue(bb.queryExists("InventoryList"));
        assertEquals("SELECT * FROM inventory;", bb.get("InventoryList"));

        assertTrue(bb.queryExists("InStock"));
        assertEquals("SELECT * FROM inventory WHERE stock > 0;", bb.get("InStock"));

        assertTrue(bb.queryExists("LongQuery"));
        assertEquals("SELECT id, username, email_address, password_hash, ip_address, first_name, " +
                        "last_name, address, company FROM users u INNER JOIN orders o ON u.id = orders.purchaser " +
                        "WHERE o.orderedOn > '2018-10-01' AND o.orderedOn < '2019-01-01' AND o.itemCount = 4 " +
                        "ORDER BY o.OrderedOn DESC, u.last_name ASC;",
                bb.get("LongQuery"));
    }

    @Test
    void unparseable() {
        Assertions.assertThrows(ParseException.class, () -> BumBag.fill(DOUBLE_MARKER));
        Assertions.assertThrows(ParseException.class, () -> BumBag.fill(MISSING_MARKER));
        Assertions.assertThrows(ParseException.class, () -> BumBag.fill(FILE_NOT_FOUND));
    }

}
