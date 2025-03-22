CREATE TABLE if not exists dish_order (
    id BIGINT PRIMARY KEY,
    dish_id BIGINT NOT NULL,
    quantityToOrder NUMERIC NOT NULL,
    orderId BIGINT NOT NULL,
    FOREIGN KEY (dish_id) REFERENCES dish(id),
    FOREIGN KEY (orderId) REFERENCES "order"(id)
);