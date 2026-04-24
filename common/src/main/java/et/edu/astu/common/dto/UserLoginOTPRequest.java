package et.edu.astu.common.dto;

public record UserLoginOTPRequest(Long accountNumber, Long userId, String code) { }
