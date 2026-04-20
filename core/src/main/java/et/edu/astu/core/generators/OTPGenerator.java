package et.edu.astu.core.generators;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class OTPGenerator {
    public String generate(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder builder = new StringBuilder();
        for (int i=6; i >= 0; --i){
            int rand = random.nextInt(0, 10);
            builder.append(rand);
        }
        return builder.toString();
    }
}
