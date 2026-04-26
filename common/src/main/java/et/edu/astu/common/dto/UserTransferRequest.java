package et.edu.astu.common.dto;

public record UserTransferRequest(Long userId, Long receiver, Double amount) { }
