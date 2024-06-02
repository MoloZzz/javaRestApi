package application.repository.util;

import application.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "role VARCHAR(100) NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS publications (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "price DECIMAL(10, 2) NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS subscriptions (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    user_id INT NOT NULL REFERENCES users(id),\n" +
                "    publication_id INT NOT NULL REFERENCES publications(id),\n" +
                "    start_date DATE NOT NULL,\n" +
                "    end_date DATE NOT NULL\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS payments (" +
                "id SERIAL PRIMARY KEY," +
                "subscription_id INT NOT NULL REFERENCES subscriptions(id)," +
                "amount DECIMAL(10, 2) NOT NULL," +
                "payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
