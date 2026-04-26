package et.edu.astu.bot.handlers.menu;

import et.edu.astu.bot.filters.IsConnected;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.UserAccountResponse;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.types.updates.Message;

import java.time.format.DateTimeFormatter;

/**
 * MyAccount class.
 * Handles incoming updates after "My Account" button (including itself)
 *
 * @author Natanim
 */
public class MyAccount {
    private final HttpService service = new HttpService();

    @MessageHandler(texts = "My Account", chatType = ChatType.PRIVATE, filter = IsConnected.class)
    @MessageHandler(commands = "/myaccount")
    private void myAccount(BotContext ctx, Message message){
        long userId = message.getFrom().getId();
        UserAccountResponse response = service.getMyAccount(userId);

        ctx.sendMessage(
                userId,
                """
                <b>Account Information</b>
                
                <b>Number</b>: <code>%d</code>
                <b>Holder</b>: <code>%s %s %s</code>
                <b>Balance</b>: %f BIRR
                <b>Phone</b>: <code>%s</code>
                <b>Created At</b>: %s
                
                <i>Thank you for your trust</i>
                """.formatted(
                        response.accountNumber(),
                        response.firstName(),
                        response.middleName(),
                        response.lastName(),
                        response.balance(),
                        response.phone(),
                        response.createdAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
                )
        )
                .parseMode(ParseMode.HTML)
                .exec();

    }
}
