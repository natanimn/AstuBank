package et.edu.astu.bot.helpers;

import et.edu.astu.common.dto.UserResponse;
import io.github.natanimn.telebof.types.keyboard.KeyboardButton;
import io.github.natanimn.telebof.types.keyboard.ReplyKeyboardMarkup;

public abstract class KeyboardHelper {
    public static ReplyKeyboardMarkup mainKeyboard(UserResponse response){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        if (response.loginRequired()) {
            markup.add("Login");
            return markup;
        }

        markup.add("My Account", "Transfer");
        markup.add("Transactions");
        return markup;
    }

    public static ReplyKeyboardMarkup sharePhone(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        markup.add(
                new KeyboardButton("Share Phone")
                        .setRequestContact(true)
        );
        markup.add("Cancel");
        return markup;
    }
}
