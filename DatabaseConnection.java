package THICK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/sqltest";  // Thay your_database_name bằng sqltest
        String username = "root";
        String password = "1234";  // Thay your_password bằng mật khẩu của bạn

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to database successful.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}
