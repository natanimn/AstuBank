package et.edu.astu.core.mapper;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.DepositResponse;
import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.common.dto.UserAccountResponse;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.transactions.Transaction;
import et.edu.astu.core.models.transactions.Transfer;

/**
 * Mapper class.
 * Maps model class data into dto class.
 *
 * @author Natanim
 */
public abstract class Mapper {
    public static AccountResponse map(Account account){
        return new AccountResponse(
                account.getAccountNumber(),
                account.getFirstName(),
                account.getMiddleName(),
                account.getLastName(),
                account.linkedWithTelegram()
        );
    }

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

    public static TransferResponse map(Transfer transfer){
        Account sender = transfer.getHolder();
        Account receiver = transfer.getReceiver();
        return new TransferResponse(
                sender.getFirstName() + " " + sender.getMiddleName() + " " + sender.getLastName(),
                sender.getAccountNumber(),
                receiver.getFirstName() + " " + receiver.getMiddleName() + " " + receiver.getLastName(),
                receiver.getAccountNumber(),
                transfer.getTransactionId(),
                transfer.getAmount(),
                transfer.getCreatedAt()
        );
    }

    public static UserAccountResponse mapUserAccount(Account account){
        return new UserAccountResponse(
                account.getAccountNumber(),
                account.getFirstName(),
                account.getMiddleName(),
                account.getLastName(),
                account.getBalance(),
                account.getPhone(),
                account.getCreatedAt()
        );
    }
}
