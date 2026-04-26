package et.edu.astu.bot.handlers.menu;

import et.edu.astu.bot.filters.IsConnected;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.TransactionResponses;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.types.updates.Message;


/**
 * MyTransactions class.
 * Handles all incoming updates after "My Transactions" button pressed (including itself)
 *
 * @author Natanim
 */
public class MyTransactions {
    private final HttpService service = new HttpService();

    @MessageHandler(
            texts = "My Transactions",
            chatType = ChatType.PRIVATE,
            filter = IsConnected.class
    )
    private void myTransactions(BotContext ctx, Message message) {
        long userId = message.getFrom().getId();
        TransactionResponses responses = service.getMyTransactions(userId);
    }
}
