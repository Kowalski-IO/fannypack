-- name: LongQuery

SELECT
  id,
  username,
  email_address,
  password_hash,
  ip_address,
  first_name,
  last_name,
  address,
  company

FROM users u

       INNER JOIN orders o ON u.id = orders.purchaser

WHERE o.orderedOn > '2018-10-01' AND o.orderedOn < '2019-01-01' AND o.itemCount = 4

ORDER BY o.OrderedOn DESC, u.last_name ASC;