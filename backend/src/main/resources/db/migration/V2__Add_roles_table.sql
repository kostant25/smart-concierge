-- 1. Создать таблицу ролей
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- 2. Вставить базовые роли
INSERT INTO roles (name) VALUES ('ADMIN'), ('EMPLOYEE');

-- 3. Добавить колонку role_id в users
ALTER TABLE users ADD COLUMN role_id INT;

-- 4. Установить role_id = 2 (EMPLOYEE) для всех существующих пользователей
UPDATE users SET role_id = (SELECT id FROM roles WHERE name = 'EMPLOYEE');

-- 5. Сделать role_id NOT NULL
ALTER TABLE users ALTER COLUMN role_id SET NOT NULL;

-- 6. Добавить внешний ключ
ALTER TABLE users ADD CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id);

-- 7. Удалить старую колонку role
ALTER TABLE users DROP COLUMN role;