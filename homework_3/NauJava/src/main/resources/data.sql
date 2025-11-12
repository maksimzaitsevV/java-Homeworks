DELETE FROM encrypted_passwords;
DELETE FROM generated_passwords;
DELETE FROM encryption_algorithms;
DELETE FROM generation_algorithms;
DELETE FROM users;
DELETE FROM reports;

INSERT INTO encryption_algorithms (id, name, key_length, description)
VALUES (1, 'SHA-256', 256, 'Secure Hash Algorithm 256-bit');

INSERT INTO generation_algorithms (id, name, character_set, description)
VALUES (1, 'Simple Generator', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', 'Simple password generator');

INSERT INTO generated_passwords (id, password_content, password_length, strength_rating, generation_algorithm_id)
VALUES
(1, 'SimplePass123', 12, 'MEDIUM', 1),
(2, 'MySecret456', 11, 'MEDIUM', 1),
(3, 'TestPassword', 12, 'WEAK', 1);

INSERT INTO encrypted_passwords (id, encrypted_content, iterations, hash_type, generated_password_id, encryption_algorithm_id)
VALUES
(1, 'a1b2c3d4e5f6sha256hash', 1, 'SHA-256', 1, 1),
(2, 'fedcba987654sha256hash', 1, 'SHA-256', 2, 1),
(3, '1234567890abcdefsha256', 1, 'SHA-256', 3, 1);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'USER' NOT NULL
);

CREATE TABLE IF NOT EXISTS reports (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    content TEXT
);



INSERT INTO users (username, password, role) VALUES
('user', '$2a$12$4rPumxnIO8xoJOpQeyC0HuGXz9Ls/ONt3lB9MmwKAxkRUmXDR6Svu', 'USER'),
('admin', '$2a$12$4rPumxnIO8xoJOpQeyC0HuGXz9Ls/ONt3lB9MmwKAxkRUmXDR6Svu', 'ADMIN');