package et.edu.astu.common.dto;

import java.time.LocalDateTime;

public class TransactionResponse {
    private String transactionId;
    private Double Amount;
    private String type;
    private LocalDateTime createdAt;

    public TransactionResponse(){}

    public TransactionResponse(String transactionId, Double amount, String type, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        Amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Double getAmount() {
        return Amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
