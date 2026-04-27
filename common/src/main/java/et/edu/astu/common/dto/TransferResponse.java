package et.edu.astu.common.dto;

import java.time.LocalDateTime;

/**
 * Represents response of Transfer transaction.
 * @param senderFullName Sender's full name
 * @param senderAccountNumber Sender's account number
 * @param receiverFullName Receiver's full name
 * @param receiverAccountNumber Receiver's account number
 * @param transactionId Transaction ID
 * @param amount amount
 * @param createdAt timestamp
 *
 * @author Natanim
 */
public record TransferResponse(
        String senderFullName,
        Long senderAccountNumber,
        String receiverFullName,
        Long receiverAccountNumber,
        String transactionId,
        Double amount,
        LocalDateTime createdAt
) { }
