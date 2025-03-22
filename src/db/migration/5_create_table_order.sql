do
$$
    begin
        if not exists(SELECT FROM pg_type WHERE typname = 'table_number') then
            CREATE TYPE "table_number" AS ENUM ('TABLE1', 'TABLE2', 'TABLE3');
        end if;
    end
$$;

CREATE TABLE if not exists "order" (
    id BIGINT PRIMARY KEY,
    tableNumber table_number,
    amountPaid NUMERIC NOT NULL,
    amountDue NUMERIC NOT NULL,
    customerArrivalDatetime TIMESTAMP NOT NULL
);