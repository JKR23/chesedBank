CREATE TABLE transactions (
                              id_transaction BIGSERIAL PRIMARY KEY,
                              public_id_transaction UUID NOT NULL DEFAULT gen_random_uuid(),

                              transaction_type VARCHAR(20) NOT NULL,
                              source_account_id UUID,
                              destination_account_id UUID,

                              amount NUMERIC(19,2) NOT NULL,
                              currency VARCHAR(3) NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              description VARCHAR(255),

                              idempotency_key VARCHAR(100) NOT NULL UNIQUE,
                              original_transaction_id BIGINT,

                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              completed_at TIMESTAMP,

                              CONSTRAINT uk_transactions_public_id
                                  UNIQUE (public_id_transaction),

                              CONSTRAINT chk_transactions_amount_positive
                                  CHECK (amount > 0),

                              CONSTRAINT chk_transactions_type
                                  CHECK (transaction_type IN (
                                                              'DEPOSIT',
                                                              'WITHDRAWAL',
                                                              'TRANSFER',
                                                              'REVERSAL'
                                      )),

                              CONSTRAINT chk_transactions_status
                                  CHECK (status IN (
                                                    'PENDING',
                                                    'COMPLETED',
                                                    'FAILED',
                                                    'REVERSED'
                                      )),

                              CONSTRAINT chk_transactions_original_reference
                                  CHECK (
                                      (transaction_type = 'REVERSAL' AND original_transaction_id IS NOT NULL)
                                          OR
                                      (transaction_type <> 'REVERSAL' AND original_transaction_id IS NULL)
                                      ),

                              CONSTRAINT fk_transactions_original
                                  FOREIGN KEY (original_transaction_id)
                                      REFERENCES transactions(id_transaction),

                              CONSTRAINT uk_transactions_original_reversal
                                  UNIQUE (original_transaction_id)
);