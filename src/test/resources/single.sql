-- name: FakeCustomerQuery
SELECT id, name, address, phoneNumber FROM customers


WHERE id = 123


-- I am a comment. This shouldn't appear in the parsed query.

              ORDER BY name ASC;