package et.edu.astu.bot.handlers.menu;

import com.google.gson.internal.LinkedTreeMap;
import et.edu.astu.bot.filters.IsConnected;
import et.edu.astu.bot.helpers.KeyboardHelper;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.TransactionResponses;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.CallbackHandler;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.types.keyboard.InlineKeyboardMarkup;
import io.github.natanimn.telebof.types.updates.CallbackQuery;
import io.github.natanimn.telebof.types.updates.Message;

import java.time.format.DateTimeFormatter;


/**
 * MyTransactions class.
 * Handles all incoming updates after "My Transactions" button pressed (including itself)
 *
 * @author Natanim
 */
public class MyTransactions {
    private final HttpService service = new HttpService();

    private record TransactionInfo(String message, InlineKeyboardMarkup markup){}
    
    private TransactionInfo fetchTransactions(long userId, int page) {
        TransactionResponses responses = service.getMyTransactions(userId, page);
        StringBuilder builder = new StringBuilder();
        long count = responses.count();
        int size = 5;
        int i = (size * page) - size + 1;
        for (var trx : responses.transactions()) {
            builder.append(("""
                            <i>#%d.</i> <code>%s</code>
                            <b>TYPE</b>: %s
                            <b>AMOUNT</b>: %.2f
                            <b>DATE</b>: %s
                            
                            """).formatted(
                            i,
                            trx.getTransactionId(),
                            trx.getType(),
                            trx.getAmount().floatValue(),
                            trx.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    )
            );
            i++;
        }
        InlineKeyboardMarkup markup = KeyboardHelper.transactions(responses.transactions(), page, count);
        if (builder.isEmpty())
            builder.append("<b>No committed transactions so far.</b>");

        return new TransactionInfo(builder.toString(), markup);
    }
    
    @MessageHandler(
            texts = "\uD83D\uDCB3 Transactions",
            chatType = ChatType.PRIVATE,
            filter = IsConnected.class
    )
    private void myTransactions(BotContext ctx, Message message) {
        long userId = message.getFrom().getId();
        TransactionInfo info = fetchTransactions(userId, 1);
        ctx.sendMessage(userId, info.message())
                    .parseMode(ParseMode.HTML)
                    .replyMarkup(info.markup())
                    .exec();
            
    }
    
    
    @CallbackHandler(
            regex = "^transactions",
            chatType = ChatType.PRIVATE
    )
    private void onTransactions(BotContext ctx, CallbackQuery query){
        long userId = query.getFrom().getId();
        int page = Integer.parseInt(query.getData().split(":")[1]);
        TransactionInfo info = fetchTransactions(userId, page);
        
        ctx.answerCallbackQuery(query.getId()).exec();
        
        ctx.editMessageText(userId, info.message(), query.getMessage().getMessageId())
                .parseMode(ParseMode.HTML)
                .replyMarkup(info.markup())
                .exec();
    }
    
    @CallbackHandler(
            regex = "^trx",
            chatType = ChatType.PRIVATE
    )
    private void onTransaction(BotContext ctx, CallbackQuery query){
        ctx.answerCallbackQuery(query.getId()).exec();

        long userId = query.getFrom().getId();
        String[] args = query.getData().split(":");
        String type = args[1];
        String id = args[2];
        int page = Integer.parseInt(args[3]);

        Object object = service.getTransaction(userId, id);
        StringBuilder builder = new StringBuilder();

        if (object == null)
            builder.append("Not found");

        LinkedTreeMap<String, Object> map = (LinkedTreeMap) object;

        switch (type) {
            case "DEPOSIT" -> {
                builder.append(
                        """
                                <b>DEPOSIT</b>
                                
                                <b>ID</b>: <code>%s</code>
                                <b>AMOUNT</b>: %.2f
                                <b>DEPOSITED AT</b>: %s
                                """.formatted(
                                map.get("transactionId"),
                                (Double) map.get("amount"),
                                ((String)map.get("createdAt")).split("\\.")[0]                        )
                );
            }
            case "WITHDRAW" -> {
                builder.append(
                        """
                                <b>WITHDRAW</b>
                                
                                <b>ID</b>: <code>%s</code>
                                <b>AMOUNT</b>: %.2f
                                <b>WITHDREW AT</b>: %s
                                """.formatted(
                                map.get("transactionId"),
                                (Double) map.get("amount"),
                                ((String)map.get("createdAt")).split("\\.")[0]
                        )
                );
            }
            case "TRANSFER" -> {
                builder.append(
                        """
                                <b>TRANSFER</b>
                                
                                <b>ID</b>: <code>%s</code>
                                <b>AMOUNT</b>: %.2f
                                <b>RECEIVER</b>:
                                  - <b>NAME</b>: %s
                                  - <b>ACCOUNT</b>: %.0f
                                <b>CREATED AT</b>: %s
                                """.formatted(
                                map.get("transactionId"),
                                (Double) map.get("amount"),
                                map.get("receiverFullName"),
                                (Double) map.get("receiverAccountNumber"),
                                ((String)map.get("createdAt")).split("\\.")[0]                        )
                );
            }
        }

        ctx.editMessageText(
                userId,
                builder.toString(),
                query.getMessage().getMessageId()
        )
                .parseMode(ParseMode.HTML)
                .replyMarkup(KeyboardHelper.back(page))
                .exec();
    }
}
