package et.edu.astu.core.dtos;

public record UserLoginOTPRequest(Long accountNumber, Long userId, String code) { }
