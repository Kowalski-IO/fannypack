-- name: InventoryList

SELECT * FROM inventory;

-- name: InStock
SELECT * FROM inventory WHERE stock > 0;