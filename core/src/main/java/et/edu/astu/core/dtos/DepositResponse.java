package et.edu.astu.core.dtos;

import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.transactions.Transaction;

import java.time.LocalDateTime;

public record DepositResponse(
        String transactionId,
        Long accountNumber,
        String firstName,
        String middleName,
        String lastName,
        Integer amount,
        LocalDateTime createdAt
) {
    public static DepositResponse map(Transaction deposit){
        Account holder = deposit.getHolder();
        return new DepositResponse(
                deposit.getTransactionId(),
                holder.getAccountNumber(),
                holder.getFirstName(),
                holder.getMiddleName(),
                holder.getLastName(),
                deposit.getAmount(),
                deposit.getCreatedAt()
        );
    }

}
