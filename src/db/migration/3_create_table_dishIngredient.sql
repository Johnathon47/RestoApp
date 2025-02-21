CREATE TABLE if not exists dish_ingredient
    (
        id_dish INT,
        id_ingredient INT,
        required_quality NUMERIC,
        unit unit,
        PRIMARY KEY (id_dish, id_ingredient),
        FOREIGN KEY (id_dish) REFERENCES dish(id),
        FOREIGN KEY (id_ingredient) REFERENCES ingredient(id)
    );