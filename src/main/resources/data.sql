DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM cryptocurrencies;

INSERT INTO cryptocurrencies (id, symbol, name, price_usd)
VALUES (90, 'BTC', 'Bitcoin', 38292.13),
       (80, 'ETH', 'Ethereum', 2628.57),
       (48543, 'SOL', 'Solana', 98.09);

INSERT INTO users (name, email, password, symbol, price_usd, price_usd_registration)
VALUES ('User', 'user@gmail.com', '{noop}user', 'BTC', 36613.90, 36613.90),
       ('Admin', 'admin@gmail.com', '{noop}admin', 'ETH', 2419.11, 2419.11);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

