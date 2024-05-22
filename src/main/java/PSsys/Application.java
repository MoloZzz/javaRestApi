package PSsys;
import PSsys.Configuration.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;

public class Application {

    public static void main(String[] args) throws IOException {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
    }
}