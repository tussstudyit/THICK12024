package THICK;

import java.sql.*;

public class UserManagement {
    // Thông tin kết nối đến cơ sở dữ liệu MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sqltest";  // URL kết nối đến MySQL database
    private static final String DB_USER = "root";  // Tên người dùng MySQL
    private static final String DB_PASSWORD = "1234";  // Mật khẩu của người dùng MySQL

    // Constructor khởi tạo lớp, tự động gọi phương thức khởi tạo cơ sở dữ liệu
    public UserManagement() {
        initializeDatabase();
    }

    // Khởi tạo bảng Users nếu chưa tồn tại trong cơ sở dữ liệu
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS Users (
                    userID INT AUTO_INCREMENT PRIMARY KEY,  
                    username VARCHAR(50) NOT NULL UNIQUE,   
                    password VARCHAR(255) NOT NULL         
                )
            """;
            Statement statement = conn.createStatement();
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xác minh thông tin đăng nhập của người dùng
    public boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";  // Câu lệnh SQL để tìm người dùng theo username và password
            PreparedStatement stmt = conn.prepareStatement(sql);  // Tạo PreparedStatement để tránh SQL Injection
            stmt.setString(1, username);  // Gán giá trị cho tham số username
            stmt.setString(2, password);  // Gán giá trị cho tham số password
            ResultSet rs = stmt.executeQuery();  // Thực thi truy vấn và nhận kết quả

            return rs.next();  // Nếu có dòng dữ liệu trả về, tức là đăng nhập thành công
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có vấn đề với cơ sở dữ liệu
            return false;  // Trả về false nếu có lỗi
        }
    }

    // Đăng ký người dùng mới vào cơ sở dữ liệu
    public boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";  // Câu lệnh SQL để chèn người dùng mới
            PreparedStatement stmt = conn.prepareStatement(sql);  // Tạo PreparedStatement
            stmt.setString(1, username);  // Gán giá trị cho tham số username
            stmt.setString(2, password);  // Gán giá trị cho tham số password
            int rowsAffected = stmt.executeUpdate();  // Thực thi câu lệnh và nhận số dòng bị ảnh hưởng

            return rowsAffected > 0;  // Nếu có dòng dữ liệu bị ảnh hưởng, tức là đăng ký thành công
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {  // Mã lỗi MySQL cho lỗi trùng lặp tên người dùng
                System.out.println("Username already exists.");  // In thông báo nếu username đã tồn tại
            } else {
                e.printStackTrace();  // In ra lỗi nếu có vấn đề khác
            }
            return false;  // Trả về false nếu đăng ký không thành công
        }
    }
}
