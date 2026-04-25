package et.edu.astu.common.dto;

import java.time.LocalDateTime;

public record UserAccountResponse(
        Long accountNumber,
        String firstName,
        String middleName,
        String lastName,
        Double balance,
        String phone,
        LocalDateTime createdAt
) { }
