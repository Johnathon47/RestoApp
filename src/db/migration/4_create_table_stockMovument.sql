do
$$
    begin
        if not exists(SELECT FROM pg_type WHERE typname = 'movement_type') then
            CREATE TYPE "movement_type" AS ENUM ('ENTRY', 'EXIT');
        end if;
    end
$$;

CREATE TABLE if not exists stock_movement (
    id BIGINT PRIMARY KEY,
    ingredient_id BIGINT,
    movement_type movement_type,
    quantity NUMERIC,
    unit unit,
    movement_date TIMESTAMP,
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);