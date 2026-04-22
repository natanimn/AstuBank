package et.edu.astu.core.generators;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TransactionGenerator {
    public String generateTransactionId(long count, int amount){
        long now = Instant.now().getEpochSecond() % 10_000;
        String base = "%d%01d%d".formatted(now, count + 1, amount);
        int checksum = computeChecksum(base);
        return String.format("%d%01d%d", now, count + 1, checksum);
    }

    private int computeChecksum(String number){
        int checksum = 0;
        for (char c: number.toCharArray())
            checksum += (checksum ^ c);
        return checksum % 1000;
    }
}
