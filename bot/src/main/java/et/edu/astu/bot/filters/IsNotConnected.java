package et.edu.astu.bot.filters;

import io.github.natanimn.telebof.filters.CustomFilter;
import io.github.natanimn.telebof.types.updates.Update;

/**
 * IsNotConnected class.
 * Used for filtering whether current user is connected to their bank account or not.
 *
 * @author Natanim
 */
public class IsNotConnected implements CustomFilter {
    @Override
    public boolean check(Update update) {
        return !new IsConnected().check(update);
    }
}
