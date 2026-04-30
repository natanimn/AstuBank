package et.edu.astu.desktop.controller;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.CreateAccountRequest;
import et.edu.astu.common.dto.OTPResponse;
import et.edu.astu.desktop.http.HttpService;
import et.edu.astu.desktop.util.Session;
import et.edu.astu.desktop.util.UIUtils;
import et.edu.astu.desktop.view.AccountPanel;

import javax.swing.JOptionPane;

public class AccountController {
    private final AccountPanel view;
    private final HttpService httpService;

    public AccountController(AccountPanel view) {
        this.view = view;
        this.httpService = new HttpService();
        initController();
    }

    private void initController() {
        view.getCreateBtn().addActionListener(e -> createAccount());
        view.getSearchBtn().addActionListener(e -> fetchAccount());
        view.getTelegramBtn().addActionListener(e -> handleTelegramAction());
    }

    private void createAccount() {
        CreateAccountRequest request = view.getCreateRequest();
        if (request == null) return;

        try {
            AccountResponse response = httpService.createAccount(request, Session.getToken());
            if (response != null) {
                UIUtils.showInfo("Account Created Successfully! Account Number: " + response.accountNumber());
                view.setAccountInfo(response);
            } else {
                UIUtils.showError("Failed to create account.");
            }
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }

    private void fetchAccount() {
        String accStr = view.getSearchAccountNumber();
        if (accStr.isEmpty()) {
            UIUtils.showError("Please enter account number.");
            return;
        }

        try {
            long accNum = Long.parseLong(accStr);
            AccountResponse response = httpService.getAccount(accNum, Session.getToken());
            view.setAccountInfo(response);
        } catch (NumberFormatException ex) {
            UIUtils.showError("Invalid account number format.");
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }

    private void handleTelegramAction() {
        AccountResponse acc = view.getCurrentAccount();

        if (acc == null) return;

        if (acc.connectedWithTelegram()) {
            disconnectTelegram(acc);
        } else {
            connectTelegram(acc);
        }
    }

    private void connectTelegram(AccountResponse acc) {
        try {
            OTPResponse otpResp = httpService.generateOTP(acc.accountNumber(), Session.getToken());
            if (otpResp != null) {
                JOptionPane.showMessageDialog(
                        view,
                        "OTP for the customer is %s.\n\nEnter this code inside telegram bot".formatted(otpResp.code()),
                        "OTP generated!",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else
                JOptionPane.showMessageDialog(
                        view,
                        "Unable to generate OTP",
                        "SYSTEM ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }

    private void disconnectTelegram(AccountResponse acc) {
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to disconnect Telegram?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                httpService.disconnect(acc.accountNumber(), Session.getToken());
                UIUtils.showInfo("Telegram Disconnected Successfully!");
                AccountResponse updated = new AccountResponse(
                        acc.accountNumber(), acc.firstName(), acc.middleName(), acc.lastName(), false
                );
                view.setAccountInfo(updated);
            } catch (Exception ex) {
                UIUtils.showError("Error: " + ex.getMessage());
            }
        }
    }
}
