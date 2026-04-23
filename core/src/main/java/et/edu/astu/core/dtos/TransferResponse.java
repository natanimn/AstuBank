package et.edu.astu.core.dtos;

import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.transactions.Transfer;

public record TransferResponse(
        String senderFullName,
        Long senderAccountNumber,
        String receiverFullName,
        Long receiverAccountNumber,
        String transactionId,
        Integer amount
) {
    public static TransferResponse map(Transfer transfer){
        Account sender = transfer.getHolder();
        Account receiver = transfer.getReceiver();
        return new TransferResponse(
                sender.getFirstName() + " " + sender.getMiddleName() + " " + sender.getLastName(),
                sender.getAccountNumber(),
                receiver.getFirstName() + " " + receiver.getMiddleName() + " " + receiver.getLastName(),
                receiver.getAccountNumber(),
                transfer.getTransactionId(),
                transfer.getAmount()
        );
    }
}
