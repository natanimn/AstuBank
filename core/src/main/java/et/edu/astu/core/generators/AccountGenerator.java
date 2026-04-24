package et.edu.astu.core.generators;

import et.edu.astu.common.dto.CreateAccountRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates unique account number based on the users information provided.
 * @author Natanim
 */
@Component
public class AccountGenerator {
    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    public Long generate(CreateAccountRequest dto){
        int seq = atomicInteger.getAndIncrement();
        long now = Instant.now().getEpochSecond() / 1_000_000;
        long birth = dto.birthDate().toEpochSecond(ZoneOffset.UTC) / 1_000_000;
        long time = now - birth;

        if (time <=0)
            throw new RuntimeException("Invalid birth date");

        if (atomicInteger.get() > 999)
            atomicInteger.set(1);

        String base = "100" + String.format("%04d", time) + String.format("%03d", seq);
        int checksum = computeChecksum(base + dto.firstName() + dto.middleName() + dto.lastName());
        return Long.parseLong(base + checksum);
    }

    private int computeChecksum(String number){
        int checkSum = 0;
        for (var c: number.toCharArray()){
            checkSum += c;
        }

        return checkSum % 10;
    }
}
