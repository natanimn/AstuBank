package et.edu.astu.bot.handlers;

import et.edu.astu.bot.handlers.commands.Login;
import et.edu.astu.bot.handlers.commands.Start;
import et.edu.astu.bot.handlers.menu.MyAccount;
import et.edu.astu.bot.handlers.menu.MyTransactions;
import et.edu.astu.bot.handlers.menu.Transfer;

import java.util.List;

/**
 * HandlerCollector class
 *
 * @author Natanim
 */
public class HandlerCollector implements Handler{
    @Override
    public List<Object> getHandlers() {
        return List.of(
                new Cancel(),
                new Start(),
                new Login(),
                new NoLogin(),
                new MyAccount(),
                new MyTransactions(),
                new Transfer()
        );
    }
}
