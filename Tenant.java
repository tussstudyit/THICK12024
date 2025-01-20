package THICK;

public class Tenant {
    private int tenantID; // ID của khách hàng
    private String fullName; // Họ và tên của khách hàng
    private String phoneNumber; // Số điện thoại của khách hàng
    private String email; // Địa chỉ email của khách hàng

    // Constructor khởi tạo thông tin khách hàng
    public Tenant(int tenantID, String fullName, String phoneNumber, String email) {
        this.tenantID = tenantID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getter và Setter cho tenantID
    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    // Getter và Setter cho fullName
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter và Setter cho phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter và Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
