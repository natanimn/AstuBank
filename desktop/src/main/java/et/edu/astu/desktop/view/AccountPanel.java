package et.edu.astu.desktop.view;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.CreateAccountRequest;
import et.edu.astu.desktop.controller.AccountController;
import et.edu.astu.desktop.util.UIUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;

public class AccountPanel extends JPanel {
    private JTextField fNameField, mNameField, lNameField, phoneField, balanceField, dobField;
    private JButton createBtn;

    private JTextField searchAccField;
    private JButton searchBtn;
    private JLabel infoLabel;
    private JButton telegramBtn;
    
    private AccountResponse currentAccount;

    public AccountPanel() {
        setLayout(new GridLayout(1, 2, 30, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(createLeftPanel());
        add(createRightPanel());

        new AccountController(this);
    }

    private JPanel createLeftPanel() {
        JPanel panel = UIUtils.createCardPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(
                UIUtils.createLabel(
                        "Open New Account",
                        UIUtils.SUBHEADER_FONT,
                        UIUtils.TEXT_PRIMARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 5, 0);
        panel.add(
                UIUtils.createLabel(
                        "First Name",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        fNameField = UIUtils.createStyledTextField("First name");
        panel.add(fNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(
                UIUtils.createLabel(
                        "Middle Name",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mNameField = UIUtils.createStyledTextField("Father name");
        panel.add(mNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);

        panel.add(
                UIUtils.createLabel(
                        "Last Name",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        lNameField = UIUtils.createStyledTextField("Last name");
        panel.add(lNameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(
                UIUtils.createLabel(
                        "Phone Number",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        phoneField = UIUtils.createStyledTextField("251912345678");
        panel.add(phoneField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(
                UIUtils.createLabel(
                        "Initial Deposit",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        balanceField = UIUtils.createStyledTextField();
        panel.add(balanceField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(
                UIUtils.createLabel(
                        "Birth Date (YYYY-MM-DD)",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        dobField = UIUtils.createStyledTextField("2002-03-22");
        panel.add(dobField, gbc);

        gbc.gridy++; gbc.insets = new Insets(25, 0, 10, 0);
        createBtn = UIUtils.createStyledButton("Confirm & Open Account");
        panel.add(createBtn, gbc);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = UIUtils.createCardPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(
                UIUtils.createLabel(
                        "Customer Lookup",
                        UIUtils.SUBHEADER_FONT,
                        UIUtils.TEXT_PRIMARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 5, 0);
        panel.add(
                UIUtils.createLabel(
                        "Account Number",
                        UIUtils.LABEL_FONT,
                        UIUtils.TEXT_SECONDARY
                ),
                gbc
        );

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        searchAccField = UIUtils.createStyledTextField();
        panel.add(searchAccField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 0, 10, 0);
        searchBtn = UIUtils.createSecondaryButton("Search Database");
        panel.add(searchBtn, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 10, 0);
        infoLabel = new JLabel("<html><body style='width: 250px; color: #6B7280'>Perform a lookup to view account status and Telegram connectivity.</body></html>");
        infoLabel.setFont(UIUtils.REGULAR_FONT);
        panel.add(infoLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 10, 0);
        telegramBtn = UIUtils.createStyledButton("TELEGRAM ACTIONS");
        telegramBtn.setVisible(false);
        panel.add(telegramBtn, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        panel.add(new JPanel() {{ setOpaque(false); }}, gbc);

        return panel;
    }

    public CreateAccountRequest getCreateRequest() {
        try {
            return new CreateAccountRequest(
                    fNameField.getText(),
                    mNameField.getText(),
                    lNameField.getText(),
                    phoneField.getText(),
                    Double.parseDouble(balanceField.getText()),
                    LocalDate.parse(dobField.getText()).atStartOfDay()
            );
        } catch (Exception e) {
            UIUtils.showError("Invalid input fields. Date format: YYYY-MM-DD");
            return null;
        }
    }

    public String getSearchAccountNumber() { return searchAccField.getText(); }
    public JButton getCreateBtn() { return createBtn; }
    public JButton getSearchBtn() { return searchBtn; }
    public JButton getTelegramBtn() { return telegramBtn; }

    public void setAccountInfo(AccountResponse acc) {
        this.currentAccount = acc;
        if (acc == null) {
            infoLabel.setText("Account not found.");
            telegramBtn.setVisible(false);
            return;
        }
        
        String info = String.format("<html><b>Full Name:</b> %s %s %s<br><b>Account #:</b> %d<br><b>Telegram Connected:</b> %s</html>",
                acc.firstName(), acc.middleName(), acc.lastName(),
                acc.accountNumber(),
                acc.connectedWithTelegram() ? "Yes" : "No");
        infoLabel.setText(info);
        
        telegramBtn.setVisible(true);
        if (acc.connectedWithTelegram()) {
            telegramBtn.setText("DISCONNECT TELEGRAM");
            telegramBtn.setBackground(new Color(220, 53, 69));
        } else {
            telegramBtn.setText("CONNECT WITH TELEGRAM");
            telegramBtn.setBackground(UIUtils.PRIMARY_COLOR);
        }
    }

    public AccountResponse getCurrentAccount() { return currentAccount; }
}
