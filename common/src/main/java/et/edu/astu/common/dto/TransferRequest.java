package et.edu.astu.common.dto;

public record TransferRequest(Long sender, Long receiver, Integer amount) { }
