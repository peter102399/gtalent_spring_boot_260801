UPDATE menu_items
SET image_url = CASE id
    WHEN 1 THEN 'egg_cake.jpg'
    WHEN 2 THEN 'toast.jpg'
    WHEN 3 THEN 'burger.jpg'
    WHEN 4 THEN 'soy_milk.jpg'
END
WHERE id IN (1, 2, 3, 4);
