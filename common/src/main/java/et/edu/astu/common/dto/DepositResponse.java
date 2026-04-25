package et.edu.astu.common.dto;

import java.time.LocalDateTime;

public record DepositResponse(
        String transactionId,
        Long accountNumber,
        String firstName,
        String middleName,
        String lastName,
        Double amount,
        LocalDateTime createdAt
) { }
