ALTER TABLE menu_items
    ADD COLUMN image_url VARCHAR(255);

UPDATE menu_items SET image_url = 'toast.jpg' WHERE id = 1;
UPDATE menu_items SET image_url = 'soy_milk.jpg' WHERE id = 2;
UPDATE menu_items SET image_url = 'egg_cake.jpg' WHERE id = 3;
UPDATE menu_items SET image_url = 'burger.jpg' WHERE id = 4;
