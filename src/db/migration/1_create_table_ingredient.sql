do
$$
    begin
        if not exists(SELECT FROM pg_type WHERE typname = 'unit') then
            CREATE TYPE "unit" AS ENUM ('G', 'L', 'U');
        end if;
    end
$$;

CREATE TABLE if not exists ingredient
    (
        id INT PRIMARY KEY,
        name VARCHAR(255),
        unit_price NUMERIC,
        unit unit,
        update_datetime DATE
    );