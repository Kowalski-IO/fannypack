# fannypack 🇺🇸 (bumbag 🇬🇧)
Keep your SQL queries right on your waist!

### Inlined SQL is a PITA. 👎

Why make your life miserable with inlined SQL and the ugly mess of string concatenation?

Instead organize your queries your own way and grab 'em out of your fannypack when you need to use them!

### Getting started

0. Add the fannypack dependency to your POM file.

```
<dependency>
  <groupId>io.kowalski</groupId>
  <artifactId>fannypack</artifactId>
  <version>1.1.0</version>
</dependency>
```

1. Rip out all that inlined SQL and organize it sanely in a good old-fashioned SQL file.
2. Before each query in your file, add a comment that looks like this:

```SQL
-- name: QueryNameHere
```

This library calls this type of comment a ```Name Marker```. You will use this name marker to retreive the query when your program requires it.

With this in mind, a simplistic inventory query will look like this:

```SQL
-- name: DemoInventoryQuery
SELECT id, name, price FROM inventory;
```

FannyPack supports multiple queries per file, just label them with a comment like the one shown above.

Additionally, you can have comments, multi-line queries and placeholders just as you'd expect.

```SQL
-- name: LongFakeUserOrderQuery

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
-- Just a comment pointing out the placeholders on the line below.
WHERE o.orderedOn > ? AND o.orderedOn < ? AND o.itemCount = 4
ORDER BY o.OrderedOn DESC, u.last_name ASC;

-- name: CustomerCount
SELECT COUNT(*) FROM customer;
```

3. Make a fannypack instance by passing in the SQL files you've created.

```Java
var fp = FannyPack.fill("src/test/resources/queries.sql");
// FannyPack::fill takes a var-args String parameter. 
// Feel free to enumerate every SQL file you want to include in your pack
```

4. Grab the query out of your pack and use it!

```Java
var q = fp.get("LongFakeUserOrderQuery");
```

### Caveats

1. If you give two (or more) queries the same name, the last one parsed wins.
2. This is just a dumb helper library. It doesn't do anything to help with SQL injection. Be sure to use the proper parameter binding you'd otherwise be using.


### Localization (or an excuse to add an Easter Egg)

Replace ```FannyPack``` with ```BumBag``` in the above examples. You're welcome.
