package THICK;

import java.time.LocalDate;

public class Contract {
    private int contractID; // Mã hợp đồng
    private int tenantID; // Mã người thuê
    private LocalDate startDate; // Ngày bắt đầu hợp đồng
    private LocalDate endDate; // Ngày kết thúc hợp đồng
    private double rentalPrice; // Giá thuê
    private double deposit; // Tiền đặt cọc
    private int numberOfRooms; // Số lượng phòng thuê
    private String address; // Địa chỉ nhà thuê
    private int houseID; // Mã nhà

    /**
     * Constructor
     */
    public Contract(int contractID, LocalDate startDate, LocalDate endDate, double rentalPrice, int tenantID, double deposit, int houseID, int numberOfRooms, String address) {
        this.contractID = contractID;
        this.tenantID = tenantID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalPrice = rentalPrice;
        this.deposit = deposit;
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.houseID = houseID;
    }

    // Getter và Setter
    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }
}
