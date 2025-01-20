package THICK;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;

public class TenantManagementPanel extends JPanel {
    static DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public TenantManagementPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        // Tiêu đề của màn hình quản lý
        JLabel titleLabel = new JLabel("Tenant Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Nút quay lại màn hình quản lý
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

        // Tạo các trường nhập dữ liệu với GridBagLayout và tăng kích thước
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Thêm các trường nhập dữ liệu cho Tenant
        JLabel tenantIDLabel = new JLabel("     Tenant ID:");
        tenantIDLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(tenantIDLabel, gbc);
        gbc.gridx = 1;
        JTextField tenantIDField = new JTextField(20);
        tenantIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tenantIDField, gbc);

        JLabel tenantNameLabel = new JLabel("       Name:");
        tenantNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(tenantNameLabel, gbc);
        gbc.gridx = 1;
        JTextField tenantNameField = new JTextField(20);
        tenantNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tenantNameField, gbc);

        JLabel tenantPhoneLabel = new JLabel("      Phonenumber:");
        tenantPhoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(tenantPhoneLabel, gbc);
        gbc.gridx = 1;
        JTextField tenantPhoneField = new JTextField(20);
        tenantPhoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tenantPhoneField, gbc);

        JLabel tenantEmailLabel = new JLabel("      Email:");
        tenantEmailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(tenantEmailLabel, gbc);
        gbc.gridx = 1;
        JTextField tenantEmailField = new JTextField(20);
        tenantEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tenantEmailField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Tạo các nút thêm, sửa, xóa, và reset
        JButton addTenantButton = new JButton("ADD");
        JButton editTenantButton = new JButton("EDIT");
        JButton deleteTenantButton = new JButton("DELETE");
        JButton resetButton = new JButton("RESET");

        // Khởi tạo model cho bảng dữ liệu
        String[] column = {"tenantID", "fullName", "phoneNumber", "email"};
        model = new DefaultTableModel(column, 0);
        JTable tablee = new JTable(model);
        rowSorter = new TableRowSorter<>(model); // Khởi tạo rowSorter
        tablee.setRowSorter(rowSorter); // Thiết lập rowSorter cho bảng
        loadTenantData(); // Tải dữ liệu ban đầu vào bảng

        // Thêm sự kiện nhấn chuột vào bảng JTable

        tablee.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tablee.getSelectedRow(); // Lấy chỉ số hàng được chọn
                if (selectedRow != -1) {
                    tenantIDField.setText(model.getValueAt(selectedRow, 0).toString());
                    tenantNameField.setText(model.getValueAt(selectedRow, 1).toString());
                    tenantPhoneField.setText(model.getValueAt(selectedRow, 2).toString());
                    tenantEmailField.setText(model.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablee);

        // Thêm các nút vào một panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTenantButton);
        buttonPanel.add(editTenantButton);
        buttonPanel.add(deleteTenantButton);
        buttonPanel.add(resetButton); // Thêm nút reset vào buttonPanel

        // Chia bảng và nút thành 2 panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.SOUTH);

        // Đặt sự kiện cho các nút
        addTenantButton.addActionListener(e -> {
            addTenant(tenantIDField, tenantNameField, tenantPhoneField, tenantEmailField);
        });
        editTenantButton.addActionListener(e -> {
            editTenant(tenantIDField, tenantNameField, tenantPhoneField, tenantEmailField);
        });
        deleteTenantButton.addActionListener(e -> deleteTenant(tenantIDField));
        resetButton.addActionListener(e -> resetTenantData()); // Thêm sự kiện cho nút reset
        searchButton.addActionListener(e -> searchTenant(searchField.getText()));
    }

    // Phương thức tìm kiếm tenant
    private void searchTenant(String keyword) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM tenant WHERE fullName LIKE ?")) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("tenantID"), rs.getString("fullName"), rs.getString("phoneNumber"), rs.getString("email")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Thêm một tenant vào cơ sở dữ liệu
    private void addTenant(JTextField tenantIDField, JTextField tenantNameField, JTextField tenantPhoneField, JTextField tenantEmailField) {
        String sql = "INSERT INTO tenant (tenantID, fullName, phoneNumber, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(tenantIDField.getText()));
            pstmt.setString(2, tenantNameField.getText());
            pstmt.setString(3, tenantPhoneField.getText());
            pstmt.setString(4, tenantEmailField.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Add a successful tenant!");
            loadTenantData(); // Làm mới dữ liệu sau khi thêm
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding tenant: " + e.getMessage());
        }
    }

    // Chỉnh sửa thông tin tenant trong cơ sở dữ liệu
    private void editTenant(JTextField tenantIDField, JTextField tenantNameField, JTextField tenantPhoneField, JTextField tenantEmailField) {
        String sql = "UPDATE tenant SET fullName = ?, phoneNumber = ?, email = ? WHERE tenantID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tenantNameField.getText());
            pstmt.setString(2, tenantPhoneField.getText());
            pstmt.setString(3, tenantEmailField.getText());
            pstmt.setInt(4, Integer.parseInt(tenantIDField.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Successfully edit tenant information!");
            loadTenantData(); // Làm mới dữ liệu sau khi sửa
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error editing tenant information: " + e.getMessage());
        }
    }

    // Xóa tenant khỏi cơ sở dữ liệu
    private void deleteTenant(JTextField tenantIDField) {
        String sql = "DELETE FROM tenant WHERE tenantID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(tenantIDField.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Successful tenant deletion!");
            loadTenantData(); // Làm mới dữ liệu sau khi xóa
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting a tenant: " + e.getMessage());
        }
    }

    // Tải lại dữ liệu tenant từ cơ sở dữ liệu vào bảng
    private void loadTenantData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM tenant";
            ResultSet rs = stmt.executeQuery(query);

            // Xóa dữ liệu cũ trong model
            model.setRowCount(0);

            // Lặp qua kết quả truy vấn và thêm dữ liệu vào model
            while (rs.next()) {
                int tenantID = rs.getInt("tenantID");
                String fullName = rs.getString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                model.addRow(new Object[]{tenantID, fullName, phoneNumber, email});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }

        repaint();
        revalidate();
    }

    // Làm mới dữ liệu tenant
    private void resetTenantData() {
        loadTenantData(); // Tải lại dữ liệu từ cơ sở dữ liệu
        JOptionPane.showMessageDialog(this, "Data refreshed!");
    }
}
