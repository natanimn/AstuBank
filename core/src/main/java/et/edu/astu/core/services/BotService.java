package et.edu.astu.core.services;

import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.transactions.Deposit;
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
        return account.linkedWithTelegram() && account.getLinkVerified();
    }

    protected void notifyDeposit(Account account, Deposit deposit){
        long userId = account.getTelegramUserId();
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
        long userId = account.getTelegramUserId();
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
}
