CREATE TABLE security_users (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                username VARCHAR(50) NOT NULL UNIQUE, -- ID or email
                                password VARCHAR(255) NOT NULL,       -- hash BCrypt, no plain text
                                role VARCHAR(20) NOT NULL             -- ROLE_AFILIADO, ROLE_ADMIN, ROLE_ANALISTA
);

-- Registrar usuarios:
--{
--  "username": "admin",
--  "password": "1234",
--  "role": "ROLE_ADMIN"
--}
--{
--  "username": "9080706050",
--  "password": "1234",
--  "role": "ROLE_AFILIADO"
--}
--{
--  "username": "analista",
--  "password": "1234",
--  "role": "ROLE_ANALISTA"
--}