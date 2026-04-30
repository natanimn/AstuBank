package et.edu.astu.desktop.view;

import et.edu.astu.desktop.util.UIUtils;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


public class AdminDashboard extends JFrame {
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JButton createEmployeeButton;

    public AdminDashboard() {
        setTitle("Astu Bank Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(UIUtils.PRIMARY_COLOR);
        
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(UIUtils.PRIMARY_COLOR);
        navPanel.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 15));

        JLabel brandLabel = UIUtils.createLabel("ASTU BANK", UIUtils.SUBHEADER_FONT, Color.WHITE);
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navPanel.add(brandLabel);
        navPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton employeeNav = createNavButton("Employee Management", true);
        navPanel.add(employeeNav);
        
        sidebar.add(navPanel, BorderLayout.NORTH);

        JButton logoutButton = createNavButton("Logout", false);
        logoutButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        sidebar.add(logoutButton, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);

        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(UIUtils.BACKGROUND_COLOR);
        contentArea.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = UIUtils.createLabel("Employee Management", UIUtils.HEADER_FONT, UIUtils.TEXT_PRIMARY);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        contentArea.add(headerPanel, BorderLayout.NORTH);

        JPanel mainCard = UIUtils.createCardPanel();
        mainCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        mainCard.add(UIUtils.createLabel("Register New Employee", UIUtils.SUBHEADER_FONT, UIUtils.TEXT_PRIMARY), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 5, 0);
        mainCard.add(UIUtils.createLabel("First Name", UIUtils.LABEL_FONT, UIUtils.TEXT_SECONDARY), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        firstNameField = UIUtils.createStyledTextField();
        mainCard.add(firstNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 5, 0);
        mainCard.add(UIUtils.createLabel("Middle Name", UIUtils.LABEL_FONT, UIUtils.TEXT_SECONDARY), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        middleNameField = UIUtils.createStyledTextField();
        mainCard.add(middleNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 5, 0);
        mainCard.add(UIUtils.createLabel("Last Name", UIUtils.LABEL_FONT, UIUtils.TEXT_SECONDARY), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lastNameField = UIUtils.createStyledTextField();
        mainCard.add(lastNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 10, 0);
        createEmployeeButton = UIUtils.createStyledButton("Create Employee Account");
        mainCard.add(createEmployeeButton, gbc);

        contentArea.add(mainCard, BorderLayout.CENTER);
        add(contentArea, BorderLayout.CENTER);

        new et.edu.astu.desktop.controller.AdminController(this);
    }

    private JButton createNavButton(String text, boolean active) {
        JButton btn = new JButton(text);
        btn.setFont(UIUtils.REGULAR_FONT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(active ? UIUtils.PRIMARY_HOVER : UIUtils.PRIMARY_COLOR);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("JButton.buttonType", "roundRect");
        return btn;
    }

    public String getFirstName() { return firstNameField.getText(); }
    public String getMiddleName() { return middleNameField.getText(); }
    public String getLastName() { return lastNameField.getText(); }
    public JButton getCreateEmployeeButton() { return createEmployeeButton; }
    
    public void clearFields() {
        firstNameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
    }
}
