DELETE FROM encrypted_passwords;
DELETE FROM generated_passwords;
DELETE FROM encryption_algorithms;
DELETE FROM generation_algorithms;

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