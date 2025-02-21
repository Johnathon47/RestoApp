CREATE TABLE if not exists dish_ingredient
    (
        id_dish INT,
        id_ingredient INT,
        required_quality NUMERIC,
        unit unit,
        PRIMARY KEY (id_dish, id_ingredient),
        FOREIGN (id_dish) REFERENCES dish(id),
        FOREIGN (id_ingredient) REFERENCES ingredient(id)
    );