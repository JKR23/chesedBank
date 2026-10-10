CREATE TABLE account(
    id_account BIGSERIAL PRIMARY KEY ,
    public_id_account UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE ,
    public_id_customer UUID NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(30) NOT NULL ,
    currency CHAR(3) NOT NULL ,
    balance NUMERIC(19,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL ,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_account_balance_non_negative CHECK ( balance>=0 )

);