CREATE TABLE IF NOT EXISTS menu_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price INT NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT chk_menu_items_price CHECK (price > 0)
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_number VARCHAR(36) NOT NULL,
    total_amount BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PLACED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_orders_order_number UNIQUE (order_number)
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    unit_price INT NOT NULL,
    quantity INT NOT NULL,
    subtotal BIGINT NOT NULL,
    PRIMARY KEY (id),
    -- The configured application DB user has no REFERENCES privilege; the service validates both IDs.
    CONSTRAINT chk_order_items_quantity CHECK (quantity > 0)
);
