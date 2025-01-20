package THICK;

import java.time.LocalDate;

public class Bill {
    // Các thông tin cố định
    private final String companyName = "Van Minh Accommodation"; // Tên Nhà
    private final String phoneNumber = "0789476515"; // Số điện thoại
    private final String billTitle = "BILL PAYMENT"; // Tiêu đề hóa đơn
    private String address1 = "38 Thach Lam, Phuoc My, Son Tra"; // Địa chỉ chi nhánh 1
    private String address2 = "88 Khai Tay 1, Hoa Quy, Ngu Hanh Son"; // Địa chỉ chi nhánh 2

    // Các thông tin liên quan đến hóa đơn
    private int paymentID; // Mã thanh toán
    private LocalDate paymentDate; // Ngày thanh toán
    private int tenantID; // Mã người thuê
    private String fullName; // Tên đầy đủ của người thuê
    private String phone; // Số điện thoại của người thuê
    private String email; // Email của người thuê
    private int numberOfRooms; // Số lượng phòng thuê
    private int houseID; // Mã nhà
    private String address; // Địa chỉ nhà
    private double rentalPrice; // Giá thuê
    private double deposit; // Tiền đặt cọc

    /**
     * Constructor khởi tạo hóa đơn với các thông tin cần thiết.
     */
    public Bill(int paymentID, LocalDate paymentDate, int tenantID, String fullName, String phone, String email,
                int numberOfRooms, int houseID, String address, double rentalPrice, double deposit,
                String address1, String address2) {
        this.paymentID = paymentID;
        this.paymentDate = paymentDate;
        this.tenantID = tenantID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.numberOfRooms = numberOfRooms;
        this.houseID = houseID;
        this.address = address;
        this.rentalPrice = rentalPrice;
        this.deposit = deposit;
        this.address1 = address1;
        this.address2 = address2;
    }

    /**
     * Phương thức hiển thị chi tiết hóa đơn.
     */
    public String displayBillDetails() {
        StringBuilder billDetails = new StringBuilder();
        billDetails.append("          VAN MINH ACCOMMODATION").append("\n")
                .append("Address 1: ").append(address1).append("\n") // Hiển thị địa chỉ chi nhánh 1
                .append("Address 2: ").append(address2).append("\n") // Hiển thị địa chỉ chi nhánh 2
                .append("Phone Number: ").append(phoneNumber).append("\n\n")
                .append("**************************************************\n")
                .append("                       Payment Invoice     ").append("\n\n")
                .append("Payment ID: ").append(paymentID).append("\n") // Mã thanh toán
                .append("Payment Date: ").append(paymentDate).append("\n\n") // Ngày thanh toán

                .append("Tenant ID: ").append(tenantID).append("\n") // Mã người thuê
                .append("Full Name: ").append(fullName).append("\n") // Tên người thuê
                .append("Phone Number: ").append(phone).append("\n") // Số điện thoại người thuê
                .append("Email: ").append(email).append("\n\n") // Email người thuê

                .append("House ID: ").append(houseID).append("\n") // Mã nhà
                .append("Number Of Rooms: ").append(numberOfRooms).append("\n") // Số lượng phòng thuê
                .append("Address: ").append(address).append("\n") // Địa chỉ nhà thuê
                .append("Rental Price: ").append(String.format("%.2f", rentalPrice)).append("\n") // Giá thuê
                .append("Deposit: ").append(String.format("%.2f", deposit)).append("\n\n") // Tiền đặt cọc
                .append("Total Money: ").append(String.format("%.2f", rentalPrice + deposit)).append("\n") // Tổng tiền
                .append("**************************************************\n")
                .append("              Thank you for your payment!\n"); // Lời cảm ơn

        return billDetails.toString();
    }
}
