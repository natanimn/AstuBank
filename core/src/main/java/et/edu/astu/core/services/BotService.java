package et.edu.astu.core.services;

import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.core.mapper.Mapper;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.transactions.Deposit;
import et.edu.astu.core.models.transactions.Transfer;
import et.edu.astu.core.models.transactions.Withdraw;
import io.github.natanimn.telebof.BotClient;
import io.github.natanimn.telebof.enums.ParseMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
                        <b>➕ New Deposit</b>
                        
                        <i>✅ You have successfully deposited %.2f BIRR to your account</i>
                        
                        <b>Transaction ID:</b> <code>%s</code>
                        <b>Amount:</b> %.2f BIRR
                        <b>Current Balance:</b> %.2f BIRR
                        """.formatted(
                                deposit.getAmount(),
                                deposit.getTransactionId(),
                                deposit.getAmount().floatValue(),
                                account.getBalance().floatValue()
                        )
                )
                        .messageEffectId("5046509860389126442")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                throw new RuntimeException(e);
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
                                <b>🔼 New Withdraw</b>
                                
                                <i>✅ You have successfully withdrew %.2f BIRR from your account</i>
                                
                                <b>Transaction ID</b>: <code>%s</code>
                                <b>Amount</b>: %.2f BIRR
                                <b>Current Balance:</b> %.2f BIRR
                                """.formatted(
                                        withdraw.getAmount(),
                                        withdraw.getTransactionId(),
                                        withdraw.getAmount(),
                                        account.getBalance()
                                )
                        )
                        .messageEffectId("5046509860389126442")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void notifyTransfer(Transfer transfer) {
        Account sender = transfer.getHolder();
        Account receiver = transfer.getReceiver();
        TransferResponse response = Mapper.map(transfer);

        if (receiver.linkedWithTelegram()) {
            long receiverTelegramId = receiver.getUser().getUserId();
            try {
                bot.context.sendMessage(
                                receiverTelegramId,
                                """
                                <b>🔽 New TopUp</b>
                                
                                <i>✅ You have successfully received %.2f BIRR from account <code>%d</code></i>
                                
                                <b>Sender Name</b>: <code>%s</code>
                                <b>Transaction ID</b>: <code>%s</code>
                                <b>Amount</b>: %.2f BIRR
                                <b>Current Balance</b>: %.2f BIRR
                                """.formatted(
                                    response.amount(),
                                    response.senderAccountNumber(),
                                    response.senderFullName(),
                                    response.transactionId(),
                                    response.amount(),
                                    receiver.getBalance()
                                )
                        )
                        .messageEffectId("5046509860389126442")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                log.error("Unable to send topUp notification to {}. {}", receiverTelegramId, e.getMessage());
            }
        }

        if (sender.linkedWithTelegram()) {
            long senderTelegramId = sender.getUser().getUserId();
            try {
                Thread.sleep(500);
                bot.context.sendMessage(
                                senderTelegramId,
                                """
                                <b> ↗️ New Transfer </b>
                                
                                <i>✅ You have successfully transferred %.2f BIRR to account <code>%d</code> </i>
                                
                                <b>Receiver Name</b>: <code>%s</code>
                                <b>Transaction ID</b>: <code>%s</code>
                                <b>Amount</b>: %.2f BIRR
                                <b>Current Balance</b>: %.2f BIRR
                                """.formatted(
                                        response.amount(),
                                        response.receiverAccountNumber(),
                                        response.receiverFullName(),
                                        response.transactionId(),
                                        response.amount(),
                                        sender.getBalance()
                                )
                        )
                        .messageEffectId("5046509860389126442")
                        .parseMode(ParseMode.HTML)
                        .exec();
            } catch (Exception e) {
                log.error("Unable to send transfer notification to {}. {}", senderTelegramId, e.getMessage());
            }
        }
    }
}
