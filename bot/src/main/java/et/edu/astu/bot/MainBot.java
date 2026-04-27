package et.edu.astu.bot;

import et.edu.astu.bot.handlers.HandlerCollector;
import io.github.natanimn.telebof.BotClient;

/**
 * Main class.
 *
 * @author Natanim
 */
public class MainBot {
    public static void main(String[] args){
        String TOKEN = System.getenv("BOT_TOKEN");

        if (TOKEN == null || TOKEN.isBlank()){
            System.err.println("BOT_TOKEN environment variable required.");
            System.exit(0);
        }

        BotClient client = new BotClient(TOKEN);
        HandlerCollector collector = new HandlerCollector();
        for (Object h: collector.getHandlers())
            client.addHandler(h);
        client.startPolling();
    }
}
