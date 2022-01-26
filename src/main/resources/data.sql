DELETE
FROM user_roles;
DELETE
FROM users;

INSERT INTO users (name, email, password, symbol, price_usd, price_usd_registration)
VALUES ('User', 'user@gmail.com', '{noop}user', 'BTC', 36613.90, 36613.90),
       ('Admin', 'admin@gmail.com', '{noop}admin', 'BTC', 2419.11, 2419.11);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);