CREATE TABLE if not exists dish_ingredient
    (
        id_dish BIGINT,
        id_ingredient BIGINT,
        required_quantity NUMERIC,
        unit unit,
        PRIMARY KEY (id_dish, id_ingredient),
        FOREIGN KEY (id_dish) REFERENCES dish(id),
        FOREIGN KEY (id_ingredient) REFERENCES ingredient(id)
    );