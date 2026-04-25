package et.edu.astu.common.dto;

public record TransferResponse(
        String senderFullName,
        Long senderAccountNumber,
        String receiverFullName,
        Long receiverAccountNumber,
        String transactionId,
        Double amount
) { }
