package et.edu.astu.desktop.view;

import et.edu.astu.desktop.controller.TransactionController;
import et.edu.astu.desktop.util.UIUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class TransactionPanel extends JPanel {
    private JTextField dwAccField, dwAmountField;
    private JButton depositBtn, withdrawBtn;

    private JTextField senderAccField, receiverAccField, transferAmountField;
    private JButton transferBtn;

    private JTextField historyAccField;
    private JButton loadHistoryBtn;
    private JTable historyTable;
    private DefaultTableModel tableModel;


    public TransactionPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("JTabbedPane.tabType", "card");
        tabbedPane.setFont(UIUtils.LABEL_FONT);

        tabbedPane.addTab("Cash Operations", createDepositWithdrawPanel());
        tabbedPane.addTab("Internal Transfer", createTransferPanel());
        tabbedPane.addTab("Transaction History", createHistoryPanel());

        add(tabbedPane, BorderLayout.CENTER);

        new TransactionController(this);
    }

    private JPanel createDepositWithdrawPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel card = UIUtils.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(500, 400));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(
                UIUtils.createLabel(
                        "Cash Deposit & Withdrawal",
                        UIUtils.SUBHEADER_FONT,
                        UIUtils.TEXT_PRIMARY
                ),
                gbc
        );

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 5, 0);
        card.add(
                UIUtils.createLabel(
                        "Target Account Number",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        dwAccField = UIUtils.createStyledTextField();
        card.add(dwAccField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        card.add(
                UIUtils.createLabel(
                        "Amount (Birr)",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        dwAmountField = UIUtils.createStyledTextField();
        card.add(dwAmountField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(30, 0, 10, 5);
        depositBtn = UIUtils.createStyledButton("Execute Deposit");
        card.add(depositBtn, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(30, 5, 10, 0);
        withdrawBtn = UIUtils.createSecondaryButton("Execute Withdrawal");
        withdrawBtn.setForeground(UIUtils.DANGER);
        card.add(withdrawBtn, gbc);

        panel.add(card);
        return panel;
    }

    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel card = UIUtils.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(500, 450));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(UIUtils.createLabel("Internal Funds Transfer", UIUtils.SUBHEADER_FONT, UIUtils.TEXT_PRIMARY), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 5, 0);
        card.add(UIUtils.createLabel("Sender Account", UIUtils.LABEL_FONT, UIUtils.TEXT_SECONDARY), gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        senderAccField = UIUtils.createStyledTextField();
        card.add(senderAccField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        card.add(UIUtils.createLabel("Receiver Account", UIUtils.LABEL_FONT, UIUtils.TEXT_SECONDARY), gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        receiverAccField = UIUtils.createStyledTextField();
        card.add(receiverAccField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        card.add(UIUtils.createLabel("Transfer Amount", UIUtils.LABEL_FONT, UIUtils.TEXT_SECONDARY), gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        transferAmountField = UIUtils.createStyledTextField();
        card.add(transferAmountField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 10, 0);
        transferBtn = UIUtils.createStyledButton("Authorize Transfer");
        card.add(transferBtn, gbc);

        panel.add(card);
        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        0,
                        0,
                        0
                )
        );

        JPanel topCard = UIUtils.createCardPanel();
        topCard.setLayout(new BorderLayout(15, 0));
        topCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER_COLOR,
                                1
                        ),
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        ));
        
        JPanel inputWrapper = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );
        inputWrapper.setOpaque(false);
        inputWrapper.add(
                UIUtils.createLabel(
                        "Account Number:",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_PRIMARY
                )
        );

        historyAccField = UIUtils.createStyledTextField();
        historyAccField.setPreferredSize(new Dimension(250, 35));
        inputWrapper.add(historyAccField);
        
        topCard.add(inputWrapper, BorderLayout.WEST);
        
        loadHistoryBtn = UIUtils.createStyledButton("Fetch Records");
        topCard.add(loadHistoryBtn, BorderLayout.EAST);

        panel.add(topCard, BorderLayout.NORTH);

        String[] cols = {"ID", "Type", "Amount", "Timestamp"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        historyTable = new JTable(tableModel);
        historyTable.setShowVerticalLines(false);
        historyTable.setRowHeight(40);
        historyTable.setSelectionBackground(new Color(238, 242, 255));
        historyTable.setSelectionForeground(UIUtils.TEXT_PRIMARY);
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getTableHeader().setFont(UIUtils.LABEL_FONT);
        historyTable.getTableHeader().setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIUtils.BORDER_COLOR));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public String getDWAccount() {
        return dwAccField.getText();
    }

    public String getDWAmount() {
        return dwAmountField.getText();
    }

    public JButton getDepositBtn() {
        return depositBtn;
    }

    public JButton getWithdrawBtn() {
        return withdrawBtn;
    }

    public String getSenderAccount() {
        return senderAccField.getText();
    }

    public String getReceiverAccount() {
        return receiverAccField.getText();
    }

    public String getTransferAmount() {
        return transferAmountField.getText();
    }

    public JButton getTransferBtn() {
        return transferBtn;
    }

    public String getHistoryAccount() {
        return historyAccField.getText();
    }

    public JButton getLoadHistoryBtn() {
        return loadHistoryBtn;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
