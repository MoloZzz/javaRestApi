-- Створення таблиці користувачів
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       role VARCHAR(100) NOT NULL
);

-- Створення таблиці періодичних видань
CREATE TABLE publications (
                              id SERIAL PRIMARY KEY,
                              title VARCHAR(100) NOT NULL,
                              description TEXT,
                              price DECIMAL(10, 2) NOT NULL
);

-- Створення таблиці підписок
CREATE TABLE subscriptions (
                               id SERIAL PRIMARY KEY,
                               user_id INT NOT NULL REFERENCES users(id),
                               publication_id INT NOT NULL REFERENCES publications(id),
                               subscription_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Створення таблиці платежів
CREATE TABLE payments (
                          id SERIAL PRIMARY KEY,
                          subscription_id INT NOT NULL REFERENCES subscriptions(id),
                          amount DECIMAL(10, 2) NOT NULL,
                          payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
