package THICK;

import java.time.LocalDate;

public class Payment {
    private int paymentID; // id thanh toán
    private LocalDate paymentDate; // ngày thanh toán
    private double price; // giá trị thanh toán
    private Contract contract; // hợp đồng liên quan đến thanh toán

    // Constructor không cần paymentID
    public Payment(int paymentID, LocalDate paymentDate, double price, Contract contract) {
        this.paymentDate = paymentDate;
        this.price = price;
        this.contract = contract;
    }

    // Getters và Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
