package et.edu.astu.core.dtos;

public record TransferRequest(Long sender, Long receiver, Integer amount) { }
