package et.edu.astu.bot.handlers;

import et.edu.astu.bot.filters.IsNotConnected;
import et.edu.astu.bot.helpers.KeyboardHelper;
import et.edu.astu.common.dto.UserResponse;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.types.updates.Message;

/**
 * NoLogin class.
 * Handles all incoming update actions outside login
 *
 * @author Natanim
 */
public class NoLogin {
    @MessageHandler(filter = IsNotConnected.class, chatType = ChatType.PRIVATE)
    private void on(BotContext ctx, Message message){
        ctx.sendMessage(
                message.getFrom().getId(),
                "LOGIN REQUIRED!!"
        )
                .replyMarkup(KeyboardHelper.mainKeyboard(new UserResponse(true)))
                .exec();
    }
}
