package et.edu.astu.bot.handlers.commands;

import et.edu.astu.bot.helpers.KeyboardHelper;
import et.edu.astu.bot.helpers.UserLock;
import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import et.edu.astu.common.dto.UserResponse;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.ChatType;
import io.github.natanimn.telebof.enums.MessageType;
import io.github.natanimn.telebof.enums.ParseMode;
import io.github.natanimn.telebof.types.keyboard.ForceReply;
import io.github.natanimn.telebof.types.media_and_service.Contact;
import io.github.natanimn.telebof.types.updates.Message;

public class Login extends HttpService {
    @MessageHandler(commands = "login", chatType = ChatType.PRIVATE)
    @MessageHandler(texts = "Login", chatType = ChatType.PRIVATE)
    private void login(BotContext ctx, Message message){
        long userId = message.getFrom().getId();
        UserResponse response = getMe(userId);

        if (!response.loginRequired())
            ctx.sendMessage(userId, "You have already logged in")
                    .replyMarkup(KeyboardHelper.mainKeyboard(response))
                    .exec();
        else {
            ctx.sendMessage(userId, "Share your phone number using the bellow button")
                    .replyMarkup(KeyboardHelper.sharePhone())
                    .exec();
            ctx.setState(userId, "getPhone");
        }
    }

    @MessageHandler(type = MessageType.CONTACT, state="getPhone", chatType = ChatType.PRIVATE)
    private void getPhone(BotContext ctx, Message message){
        Contact contact = message.getContact();
        long userId = message.getFrom().getId();
        Object lock = UserLock.getLock().computeIfAbsent(userId, k -> new Object());

        synchronized (lock){
            UserResponse response = getMe(userId);
            if (!response.loginRequired())
                ctx.sendMessage(userId, "You have already logged in")
                        .replyMarkup(KeyboardHelper.mainKeyboard(response))
                        .exec();
            else {
                try{
                    AccountResponse account = searchAccount(userId, contact.getPhoneNumber());
                    ctx.sendMessage(
                            userId,
                            """
                            <b>Account Found</b>
                            
                            <b>Account Number</b>: <code>%d</code>
                            <b>Holder</b>: %s %s
                            
                            <i>Please enter the OTP</b>
                            """.formatted(account.accountNumber(), account.firstName(), account.middleName())
                    )
                            .parseMode(ParseMode.HTML)
                            .replyMarkup(new ForceReply())
                            .exec();
                    ctx.setState(userId, "getOtp");
                    var data = ctx.getStateData(userId);
                    data.put("tries", 0);
                    data.put("accountNumber", account.accountNumber());
                } catch (Exception e) {
                    ctx.sendMessage(userId, "No account registered with the given phone number")
                            .replyMarkup(KeyboardHelper.mainKeyboard(response))
                            .exec();
                    ctx.clearState(userId);
                }
            }
        }
        UserLock.getLock().remove(userId);
    }

    @MessageHandler(state = "getOtp", type = MessageType.TEXT, chatType = ChatType.PRIVATE)
    private void getOtp(BotContext ctx, Message message){
        long userId = message.getFrom().getId();
        var data = ctx.getStateData(userId);
        int tries = (int) data.get("tries");
        long accountNumber = (long) data.get("accountNumber");
        try{
            login(new UserLoginOTPRequest(accountNumber, userId, message.getText()));
        } catch (Exception e) {
            ctx.sendMessage(userId, "Invalid credential")
                    .replyMarkup(new ForceReply())
                    .exec();
            if (tries >= 3){
                ctx.sendMessage(userId, "Session stopped. Please ty again")
                        .replyMarkup(KeyboardHelper.mainKeyboard(new UserResponse(true)))
                        .exec();
                ctx.clearState(userId);
            } else {
                data.put("tries", ++tries);
            }
            return;
        }

        ctx.sendMessage(userId, "You have successfully logged in into your account.")
                .replyMarkup(KeyboardHelper.mainKeyboard(new UserResponse(false)))
                .exec();
        ctx.clearState(userId);
    }
}
