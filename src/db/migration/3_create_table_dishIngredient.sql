CREATE TABLE if not exists dish_ingredient
    (
        id_dish INT PRIMARY KEY,
        id_ingredient INT PRIMARY KEY,
        required_quality INT,
        unit unit,
        FOREIGN (id_dish) REFERENCES dish(id),
        FOREIGN (id_ingredient) REFERENCES ingredient(id)
    );