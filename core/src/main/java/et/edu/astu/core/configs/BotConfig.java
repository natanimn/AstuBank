package et.edu.astu.core.configs;

import io.github.natanimn.telebof.BotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    @Value("${app.bot.token}")
    private String TOKEN;

    @Bean
    public BotClient botClient(){
        return new BotClient(TOKEN);
    }
}
