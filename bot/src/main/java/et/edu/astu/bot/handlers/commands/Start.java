package et.edu.astu.bot.handlers.commands;

import et.edu.astu.bot.helpers.KeyboardHelper;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.UserResponse;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.types.updates.Message;

public class Start {
    private final HttpService service = new HttpService();

    @MessageHandler(commands = "start", chatType = ChatType.PRIVATE)
    private void start(BotContext ctx, Message message){
        long userId = message.getFrom().getId();
        UserResponse response = service.getMe(userId);

        ctx.sendMessage(userId, "<b>Welcome to AstuBankBot</b>")
                .parseMode(ParseMode.HTML)
                .replyMarkup(KeyboardHelper.mainKeyboard(response))
                .exec();

    }
}
