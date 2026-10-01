CREATE TABLE customer (
    id_customer BIGSERIAL PRIMARY KEY,
    public_id_customer UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    phone VARCHAR(15),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customer_address (
    id_address BIGSERIAL PRIMARY KEY,
    public_id_address UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    id_customer BIGINT NOT NULL,
    street_number VARCHAR(10) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    province VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100) NOT NULL,
    address_type VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_customer_address_customer
        FOREIGN KEY (id_customer)
        REFERENCES customer(id_customer)
);