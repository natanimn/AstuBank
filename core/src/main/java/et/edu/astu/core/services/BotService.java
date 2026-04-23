package et.edu.astu.core.services;

import et.edu.astu.core.dtos.TransferResponse;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.transactions.Deposit;
import et.edu.astu.core.models.transactions.Transfer;
import et.edu.astu.core.models.transactions.Withdraw;
import io.github.natanimn.telebof.BotClient;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.log.BotLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService {
    private final BotClient bot;

    private boolean canBeNotified(Account account){
        return account.linkedWithTelegram();
    }

    protected void notifyDeposit(Account account, Deposit deposit){
        long userId = account.getUser().getUserId();
        if (canBeNotified(account)){
            try{
                Thread.sleep(500);
                bot.context.sendMessage(
                        userId,
                        """
                        <b>New Deposit</b>
                        
                        <i>You have successfully deposited %d BIRR to your account</i>
                        
                        <b>Transaction ID</b>: <code>%s</code>
                        <b>Amount</b>: %d BIRR
                        <b>Current Balance<b>: %d BIRR
                        """.formatted(deposit.getAmount(), deposit.getTransactionId(), deposit.getAmount(), account.getBalance())
                )
                        .messageEffectId("")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                BotLog.error("Unable to send deposit notification to {}. {}", userId, e.getMessage());
            }
        }
    }

    protected void notifyWithdraw(Account account, Withdraw withdraw){
        long userId = account.getUser().getUserId();
        if (canBeNotified(account)){
            try{
                Thread.sleep(500);
                bot.context.sendMessage(
                                userId,
                                """
                                <b>New Withdraw</b>
                                
                                <i>You have successfully withdrew %d BIRR from your account</i>
                                
                                <b>Transaction ID</b>: <code>%s</code>
                                <b>Amount</b>: %d BIRR
                                <b>Current Balance<b>: %d BIRR
                                """.formatted(withdraw.getAmount(), withdraw.getTransactionId(), withdraw.getAmount(), account.getBalance())
                        )
                        .messageEffectId("")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                BotLog.error("Unable to send withdraw notification to {}. {}", userId, e.getMessage());
            }
        }
    }

    protected void notifyTransfer(Transfer transfer) {
        Account sender = transfer.getHolder();
        Account receiver = transfer.getReceiver();
        TransferResponse response = TransferResponse.map(transfer);

        if (sender.linkedWithTelegram()) {
            long senderTelegramId = sender.getUser().getUserId();
            try {
                Thread.sleep(500);
                bot.context.sendMessage(
                                senderTelegramId,
                                """
                                <b>New Transfer</b>
                                
                                <i>You have successfully transferred %d BIRR from your account</i>
                                
                                <b>From Account</b>: <code>%d</b>
                                <b>To Account</b>: <code>%d</b>
                                <b>Receiver Name</b>: <code>%s</code>
                                <b>Transaction ID</b>: <code>%s</code>
                                <b>Amount</b>: %d BIRR
                                <b>Current Balance<b>: %d BIRR
                                """.formatted(
                                    response.amount(),
                                    response.senderAccountNumber(),
                                    response.receiverAccountNumber(),
                                    response.receiverFullName(),
                                    response.transactionId(),
                                    response.amount(),
                                    sender.getBalance()
                                )
                        )
                        .messageEffectId("")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                BotLog.error("Unable to send transfer notification to {}. {}", senderTelegramId, e.getMessage());
            }
        }

        if (receiver.linkedWithTelegram()) {
            long receiverTelegramId = receiver.getUser().getUserId();
            try {
                bot.context.sendMessage(
                                receiverTelegramId,
                                """
                                <b>New Receive</b>
                                
                                <i>You have successfully received %d BIRR transfer.
                                
                                <b>From Account</b>: <code>%d</b>
                                <b>To Account</b>: <code>%d</b>
                                <b>Sender Name</b>: <code>%s</code>
                                <b>Transaction ID</b>: <code>%s</code>
                                <b>Amount</b>: %d BIRR
                                <b>Current Balance<b>: %d BIRR
                                """.formatted(
                                    response.amount(),
                                    response.senderAccountNumber(),
                                    response.receiverAccountNumber(),
                                    response.senderFullName(),
                                    response.transactionId(),
                                    response.amount(),
                                    receiver.getBalance()
                                )
                        )
                        .messageEffectId("")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                BotLog.error("Unable to send receive notification to {}. {}", receiverTelegramId, e.getMessage());
            }
        }
    }
}
