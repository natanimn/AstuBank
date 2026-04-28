package et.edu.astu.core.generators;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EmployeeGenerator {

    public String generateUsername(long lastCount){
        int year = LocalDateTime.now().getYear() % 100;
        return "BE/%04d/%d".formatted(lastCount + 1, year);
    }

    public String generatePassword(){
        StringBuilder passwords = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String passwordKeys = "abcdefghijklmnopqrstuvwxyz@!0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i<6; i++){
            int r = random.nextInt(0, passwordKeys.length());
            passwords.append(passwordKeys.charAt(r));
        }
        return passwords.toString();
    }
}
