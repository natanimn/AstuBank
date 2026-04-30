package et.edu.astu.desktop.view;

import et.edu.astu.desktop.util.Session;
import et.edu.astu.desktop.util.UIUtils;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;


public class EmployeeDashboard extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private AccountPanel accountPanel;
    private TransactionPanel transactionPanel;

    public EmployeeDashboard() {
        setTitle("AstuBank Workspace");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        
        accountPanel = new AccountPanel();
        transactionPanel = new TransactionPanel();
        
        contentPanel.add(accountPanel, "ACCOUNT");
        contentPanel.add(transactionPanel, "TRANSACTION");
        
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBackground(UIUtils.PRIMARY_HOVER);
        
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(UIUtils.PRIMARY_COLOR);
        navPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel logoLabel = UIUtils.createLabel("ASTUBANK", UIUtils.SUBHEADER_FONT, Color.WHITE);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navPanel.add(logoLabel);
        navPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JLabel welcomeLabel = UIUtils.createLabel("Workspace", UIUtils.REGULAR_FONT, new Color(199, 210, 254));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navPanel.add(welcomeLabel);
        
        navPanel.add(Box.createRigidArea(new Dimension(0, 45)));

        navPanel.add(createSidebarButton("Accounts", "ACCOUNT"));
        navPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        navPanel.add(createSidebarButton("Transactions", "TRANSACTION"));
        
        sidebar.add(navPanel, BorderLayout.NORTH);

        // User Profile at bottom
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(UIUtils.PRIMARY_HOVER);
        userPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel userLabel = UIUtils.createLabel(Session.getUsername(), UIUtils.LABEL_FONT, Color.WHITE);
        userPanel.add(userLabel, BorderLayout.WEST);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Inter", Font.PLAIN, 12));
        logoutBtn.setForeground(new Color(239, 68, 68)); // Red text
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorder(null);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            Session.clear();
            new LoginView().setVisible(true);
            dispose();
        });
        userPanel.add(logoutBtn, BorderLayout.EAST);
        
        sidebar.add(userPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(240, 45));
        btn.setFont(UIUtils.REGULAR_FONT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(UIUtils.PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("JButton.buttonType", "roundRect");
        
        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        
        return btn;
    }
}
