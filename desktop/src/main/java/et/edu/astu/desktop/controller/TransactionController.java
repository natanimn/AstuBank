package et.edu.astu.desktop.controller;


import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.DepositRequest;
import et.edu.astu.common.dto.DepositResponse;
import et.edu.astu.common.dto.TransactionResponses;
import et.edu.astu.common.dto.TransferRequest;
import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.common.dto.TransactionResponse;
import et.edu.astu.desktop.http.HttpService;
import et.edu.astu.desktop.util.Session;
import et.edu.astu.desktop.util.UIUtils;
import et.edu.astu.desktop.view.TransactionPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TransactionController {
    private final TransactionPanel view;
    private final HttpService httpService;

    public TransactionController(TransactionPanel view) {
        this.view = view;
        this.httpService = new HttpService();
        initController();
    }

    private void initController() {
        view.getDepositBtn().addActionListener(e -> deposit());
        view.getWithdrawBtn().addActionListener(e -> withdraw());
        view.getTransferBtn().addActionListener(e -> transfer());
        view.getLoadHistoryBtn().addActionListener(e -> loadHistory());
    }

    private void deposit() {
        try {
            long ac = Long.parseLong(view.getDWAccount());
            double amount = Double.parseDouble(view.getDWAmount());
            DepositResponse resp = httpService.deposit(new DepositRequest(ac, amount), Session.getToken());
            System.out.println(resp);
            if (resp != null && resp.transactionId() != null)
                UIUtils.showInfo("Deposit Successful! Transaction ID: " + resp.transactionId());
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }

    private void withdraw() {
        try {
            long ac = Long.parseLong(view.getDWAccount());
            double amount = Double.parseDouble(view.getDWAmount());
            DepositResponse resp = httpService.withdraw(new DepositRequest(ac, amount), Session.getToken());
            if (resp != null) UIUtils.showInfo("Withdraw Successful! Transaction ID: " + resp.transactionId());
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }

    private void transfer() {
        try {
            long sender = Long.parseLong(view.getSenderAccount());
            long receiver = Long.parseLong(view.getReceiverAccount());
            double amount = Double.parseDouble(view.getTransferAmount());

            AccountResponse receiverAccount = httpService.getAccount(receiver, Session.getToken());
            AccountResponse senderAccount = httpService.getAccount(sender, Session.getToken());

            var confirm = JOptionPane.showConfirmDialog(
                    view,
                    """
                    Sender Name: %s %s %s
                    Sender Account# : %d
                    
                    Receiver Name: %s %s %s
                    Receiver Account# : %d
                    
                    Amount: %.2f
                    
                    Is the information above correct?
                    """.formatted(
                            senderAccount.firstName(),
                            senderAccount.middleName(),
                            senderAccount.lastName(),
                            senderAccount.accountNumber(),
                            receiverAccount.firstName(),
                            receiverAccount.middleName(),
                            receiverAccount.lastName(),
                            receiverAccount.accountNumber(),
                            amount
                    ),
                    "Confirm",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                TransferResponse resp = httpService.transfer(new TransferRequest(sender, receiver, amount), Session.getToken());
                if (resp != null && resp.transactionId() != null)
                    UIUtils.showInfo("Transfer Successful! Transaction ID: " + resp.transactionId());
                else
                    UIUtils.showError("Unable to transfer");
            }
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }

    private void loadHistory() {
        try {
            long ac = Long.parseLong(view.getHistoryAccount());
            TransactionResponses resp = httpService.getTransactions(ac, Session.getToken());
            DefaultTableModel model = view.getTableModel();
            model.setRowCount(0);
            if (resp != null && resp.transactions() != null) {
                for (TransactionResponse t : resp.transactions()) {
                    model.addRow(new Object[]{t.getTransactionId(), t.getType(), t.getAmount(), t.getCreatedAt()});
                }
            }
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }
}
