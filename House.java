package THICK;

public class House {
    private int houseID; // ID của nhà
    private String address; // Địa chỉ của nhà
    private int rooms; // Số phòng trong nhà
    private double rentalPrice; // Giá thuê của nhà

    // Constructor để khởi tạo các giá trị cho các thuộc tính
    public House(int houseID, String address, int rooms, double rentalPrice) {
        this.houseID = houseID;
        this.address = address;
        this.rooms = rooms;
        this.rentalPrice = rentalPrice;
    }

    // Getter và Setter
    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter và Setter cho rooms
    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    // Phương thức giả định trả về giá trị cho deposit, có thể được cập nhật sau
    public String getDeposit() {
        return "a"; // Đây là giá trị giả định, bạn có thể thay đổi logic này sau
    }
}
