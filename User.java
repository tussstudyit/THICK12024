package THICK;

public class User {
    private int userID;  // ID người dùng
    private String username;  // Tên đăng nhập của người dùng
    private String password;  // Mật khẩu của người dùng

    // Constructor để khởi tạo đối tượng User với đầy đủ thông tin
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    // Constructor để khởi tạo đối tượng User chỉ với tên đăng nhập và mật khẩu (không cần userID)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter và Setter
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
