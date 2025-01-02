-- Table: ACCOUNTS---------------------------------------------------
CREATE TABLE IF NOT EXISTS ACCOUNTS
(
    ID             UUID PRIMARY KEY,
    VERSION        INT                 NOT NULL,
    CREATED_AT     TIMESTAMP           NOT NULL,
    UPDATED_AT     TIMESTAMP           NOT NULL,
    ACCOUNT_NUMBER VARCHAR(255) UNIQUE NOT NULL,
    NAME           VARCHAR(255)        NOT NULL,
    EMAIL          VARCHAR(255) UNIQUE NOT NULL,
    BALANCE        DECIMAL(19, 2)      NOT NULL CHECK (balance >= 0)
);
