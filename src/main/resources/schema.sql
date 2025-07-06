CREATE TABLE IF NOT EXISTS account (
                         account_id BIGINT PRIMARY KEY,
                         current_balance NUMERIC(40, 20) NOT NULL,
                         created_time TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
                         transaction_id BIGSERIAL PRIMARY KEY,
                         source_account_id BIGINT NOT NULL,
                         destination_account_id BIGINT NOT NULL,
                         transfer_amount NUMERIC(40, 20) NOT NULL,
                         transaction_status VARCHAR(30) NOT NULL,
                         inserted_time TIMESTAMP NOT NULL,
                         last_updated_time TIMESTAMP NOT NULL,
                         constraint fk_source_account FOREIGN KEY (source_account_id) REFERENCES account(account_id),
                         constraint fk_destination_account FOREIGN KEY (destination_account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS account_activity (
                              id BIGSERIAL PRIMARY KEY,
                              account_id BIGINT NOT NULL,
                              transaction_id BIGINT,
                              transfer_amount NUMERIC(40, 20) NOT NULL,
                              inserted_time TIMESTAMP NOT NULL,

                              CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(account_id),
                              CONSTRAINT fk_transaction FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id),
                              CONSTRAINT uq_account_transaction UNIQUE (account_id, transaction_id)
);

-- possible future table for daily reconcilation purposes: account_balance_archive to snapshot daily balance of each account