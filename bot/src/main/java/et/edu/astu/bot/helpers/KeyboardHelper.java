package et.edu.astu.bot.helpers;

import et.edu.astu.common.dto.UserResponse;
import et.edu.astu.common.interfaces.TransactionResponse;
import io.github.natanimn.telebof.enums.ButtonStyle;
import io.github.natanimn.telebof.types.keyboard.InlineKeyboardButton;
import io.github.natanimn.telebof.types.keyboard.InlineKeyboardMarkup;
import io.github.natanimn.telebof.types.keyboard.KeyboardButton;
import io.github.natanimn.telebof.types.keyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

/**
 * KeyboardHelper class,
 * Defines both inline and keyboard button
 *
 * @author Natanim
 */
public abstract class KeyboardHelper {
    public static ReplyKeyboardMarkup mainKeyboard(UserResponse response){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        if (response.loginRequired()) {
            markup.add(
                    new KeyboardButton("Login")
                            .setStyle(ButtonStyle.PRIMARY)
            );
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
        markup.add(
                new KeyboardButton("Cancel")
                        .setStyle(ButtonStyle.DANGER)
        );
        return markup;
    }

    public static ReplyKeyboardMarkup cancel(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        markup.add(
                new KeyboardButton("Cancel")
                        .setStyle(ButtonStyle.DANGER)
        );

        return markup;
    }

    public static ReplyKeyboardMarkup amounts(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup()
                .setResizeKeyboard(true);
        markup.add("100", "200", "500");
        markup.add("1,000", "2,000", "5,000");
        markup.add("10,000", "25,000", "50,000");
        markup.add(
                new KeyboardButton("Cancel")
                        .setStyle(ButtonStyle.DANGER)
        );

        return markup;
    }

    public static ReplyKeyboardMarkup confirm(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup().setResizeKeyboard(true);
        markup.add(
                new KeyboardButton("Confirm")
                        .setStyle(ButtonStyle.SUCCESS)
        );

        markup.add(
                new KeyboardButton("Cancel")
                        .setStyle(ButtonStyle.DANGER)
        );
        return markup;
    }

    private static List<InlineKeyboardButton> preparePagination(String name, int page, long count){
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        int size = 5;

        long totalPage = (count + size - 1) / size;

        String c = name + ":";
        if ((long) page * size > count)
            throw new IllegalArgumentException("Invalid page number. Page number must not exceed total count");
        if (count <= size)
            return buttons;
        if (page > 1){
            if (page > 5)
                buttons.add(new InlineKeyboardButton("⏪ 1", c + 1));
            else
                buttons.add(new InlineKeyboardButton("◀️ 1", c + 1));
        }

        if (page - 1 > 1) {
            int prev = page - 1;
            buttons.add(new InlineKeyboardButton("◀️ %d".formatted(prev), c + prev));
        }

        buttons.add(new InlineKeyboardButton("%d".formatted(page), c + page));

        if (page + 1 < totalPage){
            int next = page - 1;
            buttons.add(new InlineKeyboardButton("▶️ %d".formatted(next), c + next));
        }

        if (page != totalPage){
            if (page + 5 < totalPage)
                buttons.add(new InlineKeyboardButton("⏩ %d".formatted(totalPage),  c + totalPage));
            else
                buttons.add(new InlineKeyboardButton("%d".formatted(totalPage), c + totalPage));
        }
        return buttons;
    }

    public static InlineKeyboardMarkup transactions(List<TransactionResponse> responses, int page, long count){
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<InlineKeyboardButton> pagination = preparePagination("transactions", page, count);
        for (var response: responses) {
            String id = response.getTransactionId();
            String type = response.getType();
            buttons.add(new InlineKeyboardButton("Trx " + id, "trx:%s:%s".formatted(type, id)));
        }

        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{
                        buttons.toArray(new InlineKeyboardButton[0]),
                        pagination.toArray(new InlineKeyboardButton[0])
                }
        );

    }
}
