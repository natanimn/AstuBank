package et.edu.astu.desktop.view;

import et.edu.astu.desktop.util.UIUtils;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;


public class LoginView extends JFrame {
    private JTextField adminUsernameField;
    private JPasswordField adminPasswordField;
    private JButton adminLoginButton;

    private JTextField employeeUsernameField;
    private JPasswordField employeePasswordField;
    private JButton employeeLoginButton;

    public LoginView() {
        setTitle("AstuBank - Secure Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.BACKGROUND_COLOR);
        setLayout(new GridBagLayout());

        JPanel mainCard = UIUtils.createCardPanel();
        mainCard.setPreferredSize(new Dimension(380, 500));
        mainCard.setLayout(new BorderLayout(0, 20));

        // Header Section
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setBackground(UIUtils.SURFACE_COLOR);
        JLabel titleLabel = UIUtils.createLabel("ASTUBANK", UIUtils.HEADER_FONT, UIUtils.PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel subtitleLabel = UIUtils.createLabel("Secure Gateway", UIUtils.REGULAR_FONT, UIUtils.TEXT_SECONDARY);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        mainCard.add(headerPanel, BorderLayout.NORTH);

        // Content Section (Tabs for Admin/Employee)
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("JTabbedPane.showTabSeparators", true);
        tabbedPane.putClientProperty("JTabbedPane.tabType", "card");
        tabbedPane.setFont(UIUtils.LABEL_FONT);

        tabbedPane.addTab("Employee Portal", createLoginPanel(false));
        tabbedPane.addTab("Administrator", createLoginPanel(true));

        mainCard.add(tabbedPane, BorderLayout.CENTER);

        add(mainCard);
    }

    private JPanel createLoginPanel(boolean isAdmin) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIUtils.SURFACE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 5, 0);

        panel.add(UIUtils.createLabel("Username", UIUtils.LABEL_FONT, UIUtils.TEXT_PRIMARY), gbc);

        gbc.gridy++;
        JTextField usernameField = UIUtils.createStyledTextField();
        panel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 0, 5, 0);
        panel.add(UIUtils.createLabel("Password", UIUtils.LABEL_FONT, UIUtils.TEXT_PRIMARY), gbc);

        gbc.gridy++;
        JPasswordField passwordField = UIUtils.createStyledPasswordField();
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 10, 0);
        JButton loginButton = UIUtils.createStyledButton(isAdmin ? "Login as Admin" : "Login to Workspace");
        panel.add(loginButton, gbc);

        if (isAdmin) {
            adminUsernameField = usernameField;
            adminPasswordField = passwordField;
            adminLoginButton = loginButton;
        } else {
            employeeUsernameField = usernameField;
            employeePasswordField = passwordField;
            employeeLoginButton = loginButton;
        }

        return panel;
    }

    public String getAdminUsername() { return adminUsernameField.getText(); }
    public String getAdminPassword() { return new String(adminPasswordField.getPassword()); }
    public JButton getAdminLoginButton() { return adminLoginButton; }

    public String getEmployeeUsername() { return employeeUsernameField.getText(); }
    public String getEmployeePassword() { return new String(employeePasswordField.getPassword()); }
    public JButton getEmployeeLoginButton() { return employeeLoginButton; }
}
