package et.edu.astu.common.interfaces;

import java.time.LocalDateTime;

public interface TransactionResponse {
    String getTransactionId();
    Double getAmount();
    String getType();
    LocalDateTime getCreatedAt();
}
