package THICK;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentManagementPanel extends JPanel {
    private List<Payment> payments;  // Danh sách thanh toán
    private JTextField paymentIDField, paymentDateField, amountField, contractIDField;  // Các trường nhập liệu
    static DefaultTableModel model;  // Mô hình bảng dữ liệu

    public PaymentManagementPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Manage Payments", JLabel.CENTER);  // Tiêu đề quản lý thanh toán
        add(titleLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("←");  // Nút quay lại màn hình quản lý
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setMargin(new Insets(5, 10, 5, 10));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Management"));  // Quay lại màn hình quản lý

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(backButton, BorderLayout.WEST);  // Thêm nút quay lại vào phần đầu trang
        add(headerPanel, BorderLayout.NORTH);

        this.payments = new ArrayList<>();  // Khởi tạo danh sách thanh toán

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));  // Form nhập liệu

        formPanel.add(new JLabel("      Payment ID:"));
        paymentIDField = new JTextField();
        formPanel.add(paymentIDField);

        formPanel.add(new JLabel("      Payment Date:"));
        paymentDateField = new JTextField();
        formPanel.add(paymentDateField);

        formPanel.add(new JLabel("      Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("      Contract ID:"));
        contractIDField = new JTextField();
        formPanel.add(contractIDField);

        add(formPanel, BorderLayout.CENTER);  // Thêm form nhập liệu vào panel chính

        // Các nút chức năng
        JButton addPaymentButton = new JButton("ADD");
        JButton editPaymentButton = new JButton("EDIT");
        JButton deletePaymentButton = new JButton("DELETE");
        JButton resetButton = new JButton("RESET");
        JButton displayBillButton = new JButton("Show Bill");

        String[] column = {"Payment ID", "Payment Date", "Amount", "Contract ID"};  // Các cột của bảng thanh toán
        model = new DefaultTableModel(column, 0);
        JTable table = new JTable(model);
        loadPaymentData();  // Tải dữ liệu thanh toán từ cơ sở dữ liệu

        // Thêm sự kiện nhấn chuột vào bảng JTable
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow(); // Lấy chỉ số hàng được chọn
                if (selectedRow != -1) {
                    paymentIDField.setText(model.getValueAt(selectedRow, 0).toString());
                    paymentDateField.setText(model.getValueAt(selectedRow, 1).toString());
                    amountField.setText(model.getValueAt(selectedRow, 2).toString());
                    contractIDField.setText(model.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);  // Thêm bảng vào ScrollPane để cuộn

        JPanel buttonPanel = new JPanel();  // Panel chứa các nút chức năng
        buttonPanel.add(addPaymentButton);
        buttonPanel.add(editPaymentButton);
        buttonPanel.add(deletePaymentButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(displayBillButton);

        JPanel tablePanel = new JPanel(new BorderLayout());  // Panel chứa bảng
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());  // Panel chính
        mainPanel.add(buttonPanel, BorderLayout.NORTH);  // Thêm nút vào phần trên của panel
        mainPanel.add(tablePanel, BorderLayout.CENTER);  // Thêm bảng vào phần giữa của panel

        add(mainPanel, BorderLayout.SOUTH);  // Thêm các phần vào phía dưới của panel

        // Các hành động khi nhấn nút
        addPaymentButton.addActionListener(e -> addPayment());
        editPaymentButton.addActionListener(e -> editPayment());
        deletePaymentButton.addActionListener(e -> deletePayment());
        resetButton.addActionListener(e -> loadPaymentData());
        displayBillButton.addActionListener(e -> displayBillDetails());
    }

    // Phương thức xóa thanh toán
    private void deletePayment() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM payment WHERE paymentID = ?")) {
            int paymentID = Integer.parseInt(paymentIDField.getText());
            pstmt.setInt(1, paymentID);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment cleared successfully!");
            loadPaymentData();  // Tải lại dữ liệu sau khi xóa
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting a payment: " + e.getMessage());
        }
    }

    // Phương thức thêm thanh toán
    private void addPayment() {
        String sql = "INSERT INTO payment (paymentID, paymentDate, amount, contractID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(paymentIDField.getText()));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(paymentDateField.getText())));
            pstmt.setDouble(3, Double.parseDouble(amountField.getText()));
            pstmt.setInt(4, Integer.parseInt(contractIDField.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Add a successful payment!");
            loadPaymentData();  // Tải lại dữ liệu sau khi thêm
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error adding payment: " + e.getMessage());
        }
    }

    // Phương thức sửa thanh toán
    private void editPayment() {
        String sql = "UPDATE payment SET paymentID = ?, paymentDate = ?, amount = ?, contractID = ? WHERE paymentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(paymentIDField.getText()));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(paymentDateField.getText())));
            pstmt.setDouble(3, Double.parseDouble(amountField.getText()));
            pstmt.setInt(4, Integer.parseInt(contractIDField.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Correct a successful payment!");
            loadPaymentData();  // Tải lại dữ liệu sau khi sửa
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error when correcting a payment: " + e.getMessage());
        }
    }

    // Phương thức hiển thị chi tiết hóa đơn
    private void displayBillDetails() {
        try {
            int paymentID = Integer.parseInt(paymentIDField.getText());
            generateBill(paymentID);  // Gọi phương thức generateBill để tạo hóa đơn
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid payment ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức tạo hóa đơn dựa trên paymentID
    private void generateBill(int paymentID) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Lấy thông tin thanh toán từ bảng payment
            String paymentQuery = "SELECT * FROM payment WHERE paymentID = ?";
            PreparedStatement paymentStmt = conn.prepareStatement(paymentQuery);
            paymentStmt.setInt(1, paymentID);
            ResultSet paymentRS = paymentStmt.executeQuery();

            if (paymentRS.next()) {
                LocalDate paymentDate = paymentRS.getDate("paymentDate").toLocalDate();
                double amount = paymentRS.getDouble("amount");
                int contractID = paymentRS.getInt("contractID");

                // Lấy thông tin hợp đồng
                String contractQuery = "SELECT * FROM contract WHERE contractID = ?";
                PreparedStatement contractStmt = conn.prepareStatement(contractQuery);
                contractStmt.setInt(1, contractID);
                ResultSet contractRS = contractStmt.executeQuery();

                if (contractRS.next()) {
                    int tenantID = contractRS.getInt("tenantID");
                    String tenantQuery = "SELECT * FROM tenant WHERE tenantID = ?";
                    PreparedStatement tenantStmt = conn.prepareStatement(tenantQuery);
                    tenantStmt.setInt(1, tenantID);
                    ResultSet tenantRS = tenantStmt.executeQuery();

                    if (tenantRS.next()) {
                        String fullName = tenantRS.getString("fullName");
                        String phone = tenantRS.getString("phoneNumber");
                        String email = tenantRS.getString("email");

                        // Lấy thông tin nhà từ bảng house
                        int houseID = contractRS.getInt("houseID");
                        String houseQuery = "SELECT * FROM house WHERE houseID = ?";
                        PreparedStatement houseStmt = conn.prepareStatement(houseQuery);
                        houseStmt.setInt(1, houseID);
                        ResultSet houseRS = houseStmt.executeQuery();

                        if (houseRS.next()) {
                            String houseAddress = houseRS.getString("address");
                            double rentalPrice = houseRS.getDouble("rentalPrice");
                            int numberOfRooms = contractRS.getInt("numberOfRooms");

                            double deposit = contractRS.getDouble("deposit");

                            // Tạo hóa đơn với thông tin thu thập được
                            Bill bill = new Bill(paymentID, paymentDate, tenantID, fullName, phone, email,
                                    numberOfRooms, houseID, houseAddress, rentalPrice, deposit,
                                    "38 Thach Lam, Phuoc My, Son Tra", "88 Khai Tay 1, Hoa Quy, Ngu Hanh Son");

                            // Hiển thị hóa đơn
                            String billDetails = bill.displayBillDetails();
                            JOptionPane.showMessageDialog(this, billDetails, "Bill Details", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error generating bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức tải dữ liệu thanh toán từ cơ sở dữ liệu và hiển thị trên bảng
    private void loadPaymentData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM payment";
            ResultSet rs = stmt.executeQuery(query);

            model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

            while (rs.next()) {
                int paymentID = rs.getInt("paymentID");
                LocalDate paymentDate = rs.getDate("paymentDate").toLocalDate();
                double amount = rs.getDouble("amount");
                int contractID = rs.getInt("contractID");

                model.addRow(new Object[]{paymentID, paymentDate, amount, contractID});  // Thêm dòng dữ liệu vào bảng
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

}
