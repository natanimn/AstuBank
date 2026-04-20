package et.edu.astu.core.dtos;

public record UserLoginOTPValidationRequest(Long accountNumber, Long userId, String code) { }
