DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id                     INTEGER AUTO_INCREMENT PRIMARY KEY,
    name                   VARCHAR(255)            NOT NULL,
    email                  VARCHAR(255)            NOT NULL,
    password               VARCHAR(255)            NOT NULL,
    symbol                 VARCHAR(255)            NOT NULL,
    price_usd              DECIMAL                 NOT NULL,
    price_usd_registration DECIMAL                 NOT NULL,
    registered             TIMESTAMP DEFAULT NOW() NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);