package THICK;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class ContractManagementPanel extends JPanel {
    private final JComboBox<Integer> houseIDComboBox; // ComboBox cho House ID
    private final JComboBox<String> addressComboBox; // ComboBox cho địa chỉ
    static DefaultTableModel model; // Model cho JTable

    public ContractManagementPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout()); // Sử dụng BorderLayout để sắp xếp các thành phần

        // Tiêu đề panel
        JLabel titleLabel = new JLabel("Contract Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.YELLOW);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.CENTER); // Thêm tiêu đề vào vị trí giữa panel

        // Nút quay lại
        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setMargin(new Insets(5, 10, 5, 10));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Management")); // Quay lại màn hình quản lý

        // Tạo trường tìm kiếm cho TenantID
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 25)); // Set preferred width and height for the search field
        JButton searchButton = new JButton("SEARCH");
        searchButton.addActionListener(e -> searchContractByTenantID(searchField.getText()));

        // Panel for header (Back button + Search field)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout for horizontal alignment
        headerPanel.add(backButton);
        headerPanel.add(new JLabel("Search by Tenant ID:"));
        headerPanel.add(searchField);
        headerPanel.add(searchButton);
        add(headerPanel, BorderLayout.NORTH);

        // Tạo các trường nhập liệu
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 1, 1)); // Update GridLayout to 10 rows

        // Các trường nhập liệu cho thông tin hợp đồng
        formPanel.add(new JLabel("      Contract ID:"));
        JTextField contractIDField = new JTextField();
        formPanel.add(contractIDField);

        formPanel.add(new JLabel("      Start Date:"));
        JTextField startDateField = new JTextField();
        formPanel.add(startDateField);

        formPanel.add(new JLabel("      End Date:"));
        JTextField endDateField = new JTextField();
        formPanel.add(endDateField);

        formPanel.add(new JLabel("      Rental Price:"));
        JTextField rentalPriceField = new JTextField();
        formPanel.add(rentalPriceField);

        formPanel.add(new JLabel("      Tenant ID:"));
        JTextField tenantIDField = new JTextField();
        formPanel.add(tenantIDField);

        formPanel.add(new JLabel("      Deposit:"));
        JTextField depositField = new JTextField();
        formPanel.add(depositField);

        formPanel.add(new JLabel("      House ID:"));
        houseIDComboBox = new JComboBox<>(new Integer[]{1, 2}); // Ví dụ các giá trị houseID
        formPanel.add(houseIDComboBox);

        formPanel.add(new JLabel("      Number of Rooms:"));
        JTextField numberofroomsField = new JTextField();
        formPanel.add(numberofroomsField);

        formPanel.add(new JLabel("      Address:"));
        addressComboBox = new JComboBox<>(new String[]{"38 Thạch Lam", "88 Khái Tây 1"}); // Ví dụ các địa chỉ
        formPanel.add(addressComboBox);

        add(formPanel, BorderLayout.CENTER); // Thêm form vào panel

        // Tạo các nút hành động (Thêm, Sửa, Xóa, Đặt lại)
        JButton addContractButton = new JButton("ADD");
        JButton editContractButton = new JButton("EDIT");
        JButton deleteContractButton = new JButton("DELETE");
        JButton resetButton = new JButton("RESET");

        // Định nghĩa các cột cho JTable
        String[] column = {"contractID", "startDate", "endDate", "rentalPrice", "tenantID", "deposit", "houseID", "numberOfRooms", "address"};
        model = new DefaultTableModel(column, 0);
        JTable tbb = new JTable(model);
        loadContractData(); // Tải dữ liệu hợp đồng từ cơ sở dữ liệu

        tbb.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tbb.getSelectedRow(); // Lấy chỉ số hàng được chọn
                if (selectedRow != -1) {
                    contractIDField.setText(model.getValueAt(selectedRow, 0).toString());
                    startDateField.setText(model.getValueAt(selectedRow, 1).toString());
                    endDateField.setText(model.getValueAt(selectedRow, 2).toString());
                    rentalPriceField.setText(model.getValueAt(selectedRow, 3).toString());
                    tenantIDField.setText(model.getValueAt(selectedRow, 4).toString());
                    depositField.setText(model.getValueAt(selectedRow, 5).toString());
                    numberofroomsField.setText(model.getValueAt(selectedRow, 6).toString());

                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tbb);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addContractButton);
        buttonPanel.add(editContractButton);
        buttonPanel.add(deleteContractButton);
        buttonPanel.add(resetButton);

        // Panel chứa bảng
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel chính chứa nút và bảng
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.SOUTH); // Thêm panel chính vào phía dưới

        // Đặt sự kiện cho các nút
        addContractButton.addActionListener(e -> addContract(contractIDField, startDateField, endDateField, rentalPriceField, tenantIDField, depositField, houseIDComboBox, numberofroomsField, addressComboBox));
        editContractButton.addActionListener(e -> editContract(contractIDField, startDateField, endDateField, rentalPriceField, tenantIDField, depositField, houseIDComboBox, numberofroomsField, addressComboBox));
        deleteContractButton.addActionListener(e -> deleteContract(contractIDField));
        resetButton.addActionListener(e -> loadContractData()); // Thêm sự kiện cho nút reset
    }

    // Thêm hợp đồng mới vào cơ sở dữ liệu
    private void addContract(JTextField contractIDField, JTextField startDateField, JTextField endDateField, JTextField rentalPriceField, JTextField tenantIDField, JTextField depositField, JComboBox<Integer> houseIDComboBox, JTextField numberofroomsField, JComboBox<String> addressComboBox) {
        String sql = "INSERT INTO contract (contractID, startDate, endDate, rentalPrice, tenantID, deposit, houseID, numberofrooms, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(contractIDField.getText()));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(startDateField.getText()))); // Chuyển đổi ngày từ String sang LocalDate
            pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.parse(endDateField.getText()))); // Chuyển đổi ngày từ String sang LocalDate
            pstmt.setDouble(4, Double.parseDouble(rentalPriceField.getText()));
            pstmt.setInt(5, Integer.parseInt(tenantIDField.getText()));
            pstmt.setString(6, depositField.getText());
            pstmt.setInt(7, (Integer) houseIDComboBox.getSelectedItem());
            pstmt.setString(8, numberofroomsField.getText());
            pstmt.setString(9, (String) addressComboBox.getSelectedItem());
            pstmt.executeUpdate(); // Thực thi câu lệnh SQL
            JOptionPane.showMessageDialog(this, "Add a successful contract!"); // Hiển thị thông báo
            loadContractData(); // Làm mới dữ liệu sau khi thêm
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding a contract: " + e.getMessage());
        }
    }

    // Chỉnh sửa thông tin hợp đồng
    private void editContract(JTextField contractIDField, JTextField startDateField, JTextField endDateField, JTextField rentalPriceField, JTextField tenantIDField, JTextField depositField, JComboBox<Integer> houseIDComboBox, JTextField numberofroomsField, JComboBox<String> addressComboBox) {
        String sql = "UPDATE contract SET startDate = ?, endDate = ?, rentalPrice = ?, tenantID = ?, deposit = ?, houseID = ?, numberofrooms = ?, address = ? WHERE contractID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.parse(startDateField.getText()))); // Chuyển đổi ngày từ String sang LocalDate
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(endDateField.getText()))); // Chuyển đổi ngày từ String sang LocalDate
            pstmt.setDouble(3, Double.parseDouble(rentalPriceField.getText()));
            pstmt.setInt(4, Integer.parseInt(tenantIDField.getText()));
            pstmt.setString(5, depositField.getText());
            pstmt.setInt(6, (Integer) houseIDComboBox.getSelectedItem());
            pstmt.setString(7, numberofroomsField.getText());
            pstmt.setString(8, (String) addressComboBox.getSelectedItem());
            pstmt.setInt(9, Integer.parseInt(contractIDField.getText()));
            pstmt.executeUpdate(); // Thực thi câu lệnh SQL
            JOptionPane.showMessageDialog(this, "Successful edit contract infomation!");
            loadContractData(); // Làm mới dữ liệu sau khi sửa
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error editing contract information: " + e.getMessage());
        }
    }

    // Xóa hợp đồng
    private void deleteContract(JTextField contractIDField) {
        String sql = "DELETE FROM contract WHERE contractID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(contractIDField.getText()));
            pstmt.executeUpdate(); // Thực thi câu lệnh SQL
            JOptionPane.showMessageDialog(this, "Successfully deleting a contract!"); // Hiển thị thông báo
            loadContractData(); // Làm mới dữ liệu sau khi xóa
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error when deleting a contract: " + e.getMessage());
        }
    }

    // Tải dữ liệu hợp đồng từ cơ sở dữ liệu và hiển thị lên bảng
    private void loadContractData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM contract";
            ResultSet rs = stmt.executeQuery(query);
            model.setRowCount(0); // Xóa dữ liệu cũ trong model
            while (rs.next()) {
                int contractID = rs.getInt("contractID");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                double rentalPrice = rs.getDouble("rentalPrice");
                int tenantID = rs.getInt("tenantID");
                String deposit = rs.getString("deposit");
                int houseID = rs.getInt("houseID");
                String numberOfRooms = rs.getString("numberOfRooms");
                String address = rs.getString("address");

                model.addRow(new Object[]{contractID, startDate, endDate, rentalPrice, tenantID, deposit, houseID, numberOfRooms, address});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }

        repaint(); // Vẽ lại giao diện
        revalidate(); // Cập nhật giao diện
    }

    // Tìm kiếm hợp đồng
    private void searchContractByTenantID(String tenantID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM contract WHERE tenantID = ?")) {
            pstmt.setInt(1, Integer.parseInt(tenantID));
            ResultSet rs = pstmt.executeQuery();
            model.setRowCount(0); // Xóa dữ liệu cũ trong model
            while (rs.next()) {
                int contractID = rs.getInt("contractID");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                double rentalPrice = rs.getDouble("rentalPrice");
                int tenantIDFromDB = rs.getInt("tenantID");
                String deposit = rs.getString("deposit");
                int houseID = rs.getInt("houseID");
                String numberOfRooms = rs.getString("numberOfRooms");
                String address = rs.getString("address");

                model.addRow(new Object[]{contractID, startDate, endDate, rentalPrice, tenantIDFromDB, deposit, houseID, numberOfRooms, address});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching contract: " + e.getMessage());
        }
    }
}
