CREATE TABLE IF NOT EXISTS money_record (
    id BIGINT PRIMARY KEY,
    total_money INT NOT NULL
);

INSERT IGNORE INTO money_record (id, total_money) VALUES (1, 0);
