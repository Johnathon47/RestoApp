CREATE TABLE if not exists dish_availability(
    dish_id BIGINT PRIMARY KEY,
    availability NUMERIC NOT NULL,
    FOREIGN KEY (dish_id) REFERENCES dish(id)
);