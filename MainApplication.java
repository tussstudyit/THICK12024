package THICK;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserManagement userManagement;
    private boolean isLogin = true;

    public MainApplication() {
        setTitle("Van Minh Accommodation");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userManagement = new UserManagement();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JLabel titleLabel = new JLabel("Van Minh Accommodation", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(78, 111, 126));
        titleLabel.setForeground(Color.WHITE);

        cardPanel.add(createLoginRegisterView(), "LoginRegister");
        cardPanel.add(createManagementView(), "Management");
        cardPanel.add(new ContractManagementPanel(cardLayout, cardPanel), "Contracts");
        cardPanel.add(new HouseManagementPanel(cardLayout, cardPanel), "Houses");
        cardPanel.add(new TenantManagementPanel(cardLayout, cardPanel), "Tenants");
        cardPanel.add(new PaymentManagementPanel(cardLayout, cardPanel), "Payments");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createLoginRegisterView() {
        JPanel loginRegisterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel leftPanel = createLoginRegisterPanel();
        JPanel rightPanel = createImagePanel();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.4;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        loginRegisterPanel.add(leftPanel, c);

        c.weightx = 0.6;
        c.gridx = 1;
        loginRegisterPanel.add(rightPanel, c);

        return loginRegisterPanel;
    }

    private JPanel createManagementView() {
        JPanel managementPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 50, 50));
        JButton manageHousesButton = new JButton("Manage Houses");
        JButton manageTenantsButton = new JButton("Manage Tenants");
        JButton manageContractsButton = new JButton("Manage Contracts");
        JButton managePaymentsButton = new JButton("Manage Payments");

        styleButton(manageHousesButton);
        styleButton(manageTenantsButton);
        styleButton(manageContractsButton);
        styleButton(managePaymentsButton);

        menuPanel.add(manageHousesButton);
        menuPanel.add(manageTenantsButton);
        menuPanel.add(manageContractsButton);
        menuPanel.add(managePaymentsButton);

        JPanel rightPanel = createImagePanel();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        managementPanel.add(menuPanel, c);

        c.weightx = 0.5;
        c.gridx = 1;
        managementPanel.add(rightPanel, c);

        manageHousesButton.addActionListener(e -> cardLayout.show(cardPanel, "Houses"));
        manageTenantsButton.addActionListener(e -> cardLayout.show(cardPanel, "Tenants"));
        manageContractsButton.addActionListener(e -> cardLayout.show(cardPanel, "Contracts"));
        managePaymentsButton.addActionListener(e -> cardLayout.show(cardPanel, "Payments"));

        return managementPanel;
    }

    private JPanel createLoginRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(239, 246, 250));

        JLabel titleLabel = new JLabel("Login / Register", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        usernameField = new JTextField();
        JLabel usernameLabel = new JLabel(" Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));

        passwordField = new JPasswordField();
        JLabel passwordLabel = new JLabel(" Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        styleButton(loginButton, new Color(76, 175, 80), Color.WHITE);
        styleButton(registerButton, new Color(33, 150, 243), Color.WHITE);

        loginButton.addActionListener(e -> handleLoginOrRegister());
        registerButton.addActionListener(e -> toggleLoginRegister());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        panel.add(titleLabel);
        panel.add(inputPanel);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon image = new ImageIcon("C:\\Users\\DELL\\Downloads\\CS2 88 Khái Tây 1, Hòa Qúy, Ngũ Hành Sơn, Đà Nẵng.png");
        JLabel imageLabel = new JLabel(image);
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    private void handleLoginOrRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
            return;
        }

        if (isLogin) {
            if (userManagement.loginUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                cardLayout.show(cardPanel, "Management");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } else {
            if (userManagement.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                toggleLoginRegister();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username might already exist.");
            }
        }
    }

    private void toggleLoginRegister() {
        isLogin = !isLogin;
        if (isLogin) {
            loginButton.setText("Login");
            registerButton.setText("Register");
        } else {
            loginButton.setText("Register");
            registerButton.setText("Back to Login");
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(173, 216, 230));
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApplication().setVisible(true));
    }
}
