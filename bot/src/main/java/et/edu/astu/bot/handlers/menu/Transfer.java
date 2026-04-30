package et.edu.astu.bot.handlers.menu;

import et.edu.astu.bot.filters.IsConnected;
import et.edu.astu.bot.helpers.KeyboardHelper;
import et.edu.astu.bot.helpers.UserLock;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.common.dto.UserAccountResponse;
import et.edu.astu.common.dto.UserResponse;
import et.edu.astu.common.dto.UserTransferRequest;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.enums.MessageType;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.log.BotLog;
import io.github.natanimn.telebof.types.updates.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Transfer class.
 * Handles all updates that comes after "Transfer" button (including itself)
 *
 * @author Natanim
 */
public class Transfer {
    private final HttpService service = new HttpService();

    @MessageHandler(
            texts = "↗ Transfer",
            chatType = ChatType.PRIVATE,
            filter = IsConnected.class
    )
    private void transfer(BotContext ctx, Message message) {
        long userId = message.getFrom().getId();
        ctx.sendMessage(userId, "Enter the receiver's account number: ")
                .replyMarkup(KeyboardHelper.cancel())
                .exec();
        ctx.setState(userId, "getAccountNumber");
    }

    @MessageHandler(
            type = MessageType.TEXT,
            filter = IsConnected.class,
            state = "getAccountNumber",
            chatType = ChatType.PRIVATE
    )
    private void getAccountNumber(BotContext ctx, Message message) {
        long userId = message.getFrom().getId();

        long accountNumber;
        try {
            accountNumber = Long.parseLong(message.getText());
        } catch (NumberFormatException e) {
            ctx.sendMessage(userId, "Invalid account number. Please enter the valid one or cancel the operation")
                    .replyMarkup(KeyboardHelper.cancel())
                    .exec();
            return;
        }
        try {
            AccountResponse response = service.searchAccount(userId, accountNumber);
            String name =  response.firstName() + " " + response.middleName() + " " + response.lastName();
            ctx.sendMessage(
                            userId,
                            """
                            <b>Account Found</b>
                            
                            <b>Number</b>: <code>%d</code>
                            <b>Holder</b>: <code>%s</code>
                            
                            <b>How much money would you like to transfer from your account?</b>
                            
                            <i>Select from the bellow button or write your own</i>
                            """.formatted(
                                    response.accountNumber(),
                                    name
                            )
                    )
                    .parseMode(ParseMode.HTML)
                    .replyMarkup(KeyboardHelper.amounts())
                    .exec();
            ctx.setState(userId, "getAmount");
            var data = ctx.getStateData(userId);
            data.put("account", accountNumber);
            data.put("name", name);
        } catch (Exception e) {
            ctx.sendMessage(userId, "Account not found")
                    .replyMarkup(KeyboardHelper.cancel())
                    .exec();
        }
    }

    @MessageHandler(
            type = MessageType.TEXT,
            filter = IsConnected.class,
            state = "getAmount",
            chatType = ChatType.PRIVATE
    )
    private void getAmount(BotContext ctx, Message message) {
        long userId = message.getFrom().getId();
        String str  = message.getText()
                .replace(",","")
                .replace(" ", "");
        double amount;
        try{
            amount = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            ctx.sendMessage(userId, "Invalid amount of money.")
                    .replyMarkup(KeyboardHelper.cancel())
                    .exec();
            return;
        }
        var data = ctx.getStateData(userId);
        data.put("amount", amount);

        String name = data.get("name").toString();
        Long account = (Long) data.get("account");
        ctx.sendMessage(userId,
                """
                <b>Confirm Transaction</b>
                
                <b>To</b>: <code>%d</code>
                <b>Receiver</b>: %s
                <b>Amount</b>: %f
                """.formatted(account, name, amount)
        )
                .parseMode(ParseMode.HTML)
                .replyMarkup(KeyboardHelper.confirm())
                .exec();

        ctx.setState(userId, "confirmTransfer");
    }

    @MessageHandler(
            texts = "Confirm",
            state = "confirmTransfer",
            chatType = ChatType.PRIVATE,
            filter = IsConnected.class
    )
    private void confirm(BotContext ctx, Message message){
        long userId = message.getFrom().getId();
        Object lock = UserLock.getLock().computeIfAbsent(userId, k -> new Object());

        synchronized (lock){
            var data = ctx.getStateData(userId);
            double amount = (Double) data.get("amount");
            long account = (Long) data.get("account");

            UserTransferRequest request = new UserTransferRequest(userId, account, amount);
            UserAccountResponse response = service.getMyAccount(userId);

            if (request.receiver().equals(response.accountNumber()))
                ctx.sendMessage(
                        userId,
                        """
                                <b>Transfer Failed</b>
                                
                                <b>Reason</b>: <i>You cannot transfer to your own account</i>
                                """
                )
                        .parseMode(ParseMode.HTML)
                        .exec();
            else if (request.amount() > response.balance())
                ctx.sendMessage(
                                userId,
                                """
                                        <b>Transfer Failed</b>
                                        
                                        <b>Reason</b>: <i>You don't have sufficient balance to transfer</i>
                                        """
                        )
                        .parseMode(ParseMode.HTML)
                        .exec();
            else {
                try (ExecutorService service1 = Executors.newSingleThreadExecutor()){
                    Future<Object> task = service1.submit(() -> service.transfer(request));
                    int dot = 1;
                    StringBuilder builder = new StringBuilder();
                    builder.repeat(".", dot);

                    Message msg = ctx.sendMessage(
                                    userId,
                                    "<code>Loading%s</code>".formatted(builder.toString())
                            )
                            .parseMode(ParseMode.HTML)
                            .exec();

                    while (!task.isDone()) {
                        if (dot >= 5) dot = 1;
                        dot++;
                        builder.repeat(".", dot);
                        ctx.editMessageText(
                                        userId,
                                        "<code>Loading%s</code>".formatted(builder.toString()),
                                        msg.getMessageId()
                                )
                                .parseMode(ParseMode.HTML)
                                .exec();
                       Thread.sleep(500);
                    }
                    task.get();
                    ctx.editMessageText(userId, "<code>DONE</code>", msg.getMessageId())
                            .parseMode(ParseMode.HTML)
                            .exec();
                } catch (Exception e) {
                    BotLog.error(e.toString());
                    ctx.sendMessage(userId, "<b>Transfer Failed</b>\n\nUnexpected error happen. Please try again later")
                            .parseMode(ParseMode.HTML)
                            .exec();
                }
            }
        }
        ctx.clearState(userId);
        ctx.sendMessage(userId, "<i>Select an option</i>")
                .parseMode(ParseMode.HTML)
                .replyMarkup(KeyboardHelper.mainKeyboard(new UserResponse(false)))
                .exec();
        UserLock.getLock().remove(userId);
    }
}