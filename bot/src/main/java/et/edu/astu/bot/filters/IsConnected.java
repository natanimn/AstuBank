package et.edu.astu.bot.filters;

import et.edu.astu.bot.http.HttpService;
import et.edu.astu.common.dto.UserResponse;
import io.github.natanimn.telebof.filters.CustomFilter;
import io.github.natanimn.telebof.types.updates.Update;

/**
 * IsConnected class.
 * Used for filtering whether current user is connected to their bank account or not.
 *
 * @author Natanim
 */
public class IsConnected implements CustomFilter {
    @Override
    public boolean check(Update update) {
        HttpService service = new HttpService();
        long userId = update.getMessage() != null? update.getMessage().getFrom().getId() : update.getCallbackQuery().getFrom().getId();
        UserResponse response = service.getMe(userId);

        return !response.loginRequired();
    }
}
