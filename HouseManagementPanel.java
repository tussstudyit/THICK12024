package THICK;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HouseManagementPanel extends JPanel {
    static DefaultTableModel model;

    public HouseManagementPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        // Tiêu đề của màn hình quản lý nhà
        JLabel titleLabel = new JLabel("House Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.YELLOW);
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.CENTER);

        // Nút quay lại màn hình quản lý chính
        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setMargin(new Insets(5, 10, 5, 10));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Management"));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(backButton, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Tạo ô tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search: ");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        headerPanel.add(searchPanel, BorderLayout.CENTER);

        // Tạo các trường nhập dữ liệu với GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Tạo các label và textfield cho nhập liệu
        JLabel houseIDLabel = new JLabel("      House ID:");
        houseIDLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(houseIDLabel, gbc);
        gbc.gridx = 1;
        JTextField houseIDField = new JTextField(20);
        houseIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(houseIDField, gbc);

        JLabel houseAddressLabel = new JLabel("     Address:");
        houseAddressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(houseAddressLabel, gbc);
        gbc.gridx = 1;
        JTextField houseAddressField = new JTextField(20);
        houseAddressField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(houseAddressField, gbc);

        JLabel houseRoomsLabel = new JLabel("       Rooms:");
        houseRoomsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(houseRoomsLabel, gbc);
        gbc.gridx = 1;
        JTextField houseRoomsField = new JTextField(20);
        houseRoomsField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(houseRoomsField, gbc);

        JLabel houseRentalPriceLabel = new JLabel("     Rental Price:");
        houseRentalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(houseRentalPriceLabel, gbc);
        gbc.gridx = 1;
        JTextField houseRentalPriceField = new JTextField(20);
        houseRentalPriceField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(houseRentalPriceField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Tạo các nút chức năng: thêm, sửa, xóa, reset
        JButton addHouseButton = new JButton("ADD");
        JButton editHouseButton = new JButton("EDIT");
        JButton deleteHouseButton = new JButton("DELETE");
        JButton resetButton = new JButton("RESET");

        // Cấu hình bảng hiển thị dữ liệu
        String[] column = {"houseID", "address", "rooms", "rentalPrice"};
        model = new DefaultTableModel(column, 0);
        JTable tb = new JTable(model);
        loadHouseData();

        // Thêm sự kiện nhấn chuột vào bảng JTable
        tb.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tb.getSelectedRow(); // Lấy chỉ số hàng được chọn
                if (selectedRow != -1) {
                    houseIDField.setText(model.getValueAt(selectedRow, 0).toString());
                    houseAddressField.setText(model.getValueAt(selectedRow, 1).toString());
                    houseRoomsField.setText(model.getValueAt(selectedRow, 2).toString());
                    houseRentalPriceField.setText(model.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tb);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addHouseButton);
        buttonPanel.add(editHouseButton);
        buttonPanel.add(deleteHouseButton);
        buttonPanel.add(resetButton);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.SOUTH);

        // Đặt sự kiện cho các nút
        addHouseButton.addActionListener(e -> addHouse(houseIDField, houseAddressField, houseRoomsField, houseRentalPriceField));
        editHouseButton.addActionListener(e -> editHouse(houseIDField, houseAddressField, houseRoomsField, houseRentalPriceField));
        deleteHouseButton.addActionListener(e -> deleteHouse(houseIDField));
        resetButton.addActionListener(e -> resetHouseData());
        searchButton.addActionListener(e -> searchHouse(searchField.getText()));
    }

    private void searchHouse(String keyword) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM house WHERE houseID LIKE ?")) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                int houseID = rs.getInt("houseID");
                String address = rs.getString("address");
                int rooms = rs.getInt("rooms");
                double rentalPrice = rs.getDouble("rentalPrice");
                model.addRow(new Object[]{houseID, address, rooms, rentalPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching data: " + e.getMessage());
        }
    }

    private void addHouse(JTextField houseIDField, JTextField houseAddressField, JTextField houseRoomsField, JTextField houseRentalPriceField) {
        String sql = "INSERT INTO house (houseID, address, rooms, rentalPrice) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(houseIDField.getText()));
            pstmt.setString(2, houseAddressField.getText());
            pstmt.setInt(3, Integer.parseInt(houseRoomsField.getText()));
            pstmt.setDouble(4, Double.parseDouble(houseRentalPriceField.getText()));
            pstmt.executeUpdate();
            loadHouseData();
            JOptionPane.showMessageDialog(this, "Add a successful home!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error when adding a house: " + e.getMessage());
        }
    }

    private void editHouse(JTextField houseIDField, JTextField houseAddressField, JTextField houseRoomsField, JTextField houseRentalPriceField) {
        String sql = "UPDATE house SET address = ?, rooms = ?, rentalPrice = ? WHERE houseID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, houseAddressField.getText());
            pstmt.setInt(2, Integer.parseInt(houseRoomsField.getText()));
            pstmt.setDouble(3, Double.parseDouble(houseRentalPriceField.getText()));
            pstmt.setInt(4, Integer.parseInt(houseIDField.getText()));
            pstmt.executeUpdate();
            loadHouseData();
            JOptionPane.showMessageDialog(this, "Successfully edit home information!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errors when editing home information: " + e.getMessage());
        }
    }

    private void deleteHouse(JTextField houseIDField) {
        String sql = "DELETE FROM house WHERE houseID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(houseIDField.getText()));
            pstmt.executeUpdate();
            loadHouseData();
            JOptionPane.showMessageDialog(this, "Successfully deleting a house!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errors when deleting a house: " + e.getMessage());
        }
    }

    private void loadHouseData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM house";
            ResultSet rs = stmt.executeQuery(query);
            model.setRowCount(0);
            while (rs.next()) {
                int houseID = rs.getInt("houseID");
                String address = rs.getString("address");
                int rooms = rs.getInt("rooms");
                double rentalPrice = rs.getDouble("rentalPrice");
                model.addRow(new Object[]{houseID, address, rooms, rentalPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
        repaint();
        revalidate();
    }

    private void resetHouseData() {
        loadHouseData();
        JOptionPane.showMessageDialog(this, "Data refreshed!");
    }
}
