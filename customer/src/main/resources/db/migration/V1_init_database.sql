-- -- 1. Create the sequence used for the primary key
-- CREATE SEQUENCE IF NOT EXISTS customer_id_sequence
--     START WITH 1
--     INCREMENT BY 1;
--
-- -- 2. Create the customer table
-- CREATE TABLE IF NOT EXISTS customer (
--                                         id INTEGER PRIMARY KEY DEFAULT nextval('customer_id_sequence'),
--                                         first_name VARCHAR(255),
--                                         last_name VARCHAR(255),
--                                         email VARCHAR(255)
-- );
--
-- -- 3. (Optional) Associate the sequence with the table column
-- -- so the sequence is dropped if the table is dropped.
-- ALTER SEQUENCE customer_id_sequence OWNED BY customer.id;


CREATE SEQUENCE customer_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE customer (
                          id INTEGER PRIMARY KEY DEFAULT nextval('customer_id_sequence'),
                          first_name VARCHAR(255),
                          last_name VARCHAR(255),
                          email VARCHAR(255)
);
