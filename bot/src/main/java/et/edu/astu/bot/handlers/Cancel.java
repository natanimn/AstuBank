package et.edu.astu.bot.handlers;

import et.edu.astu.bot.helpers.KeyboardHelper;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.UserResponse;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.types.updates.Message;

/**
 * Cancel class.
 * Handles "Cancel" button
 *
 * @author Natanim
 */
public class Cancel {
    private final HttpService service = new HttpService();

    @MessageHandler(texts = "Cancel", chatType = ChatType.PRIVATE)
    @MessageHandler(commands = "cancel", chatType = ChatType.PRIVATE)
    private void on(BotContext context, Message message){
        long userId = message.getFrom().getId();
        UserResponse response = service.getMe(userId);

        context.sendMessage(
                userId,
                "<b>\uD83D\uDEAB Action cancelled</b>"
        )
                .parseMode(ParseMode.HTML)
                .replyMarkup(KeyboardHelper.mainKeyboard(response))
                .exec();
        context.clearState(userId);
    }
}
