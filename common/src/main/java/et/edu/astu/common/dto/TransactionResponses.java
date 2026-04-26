package et.edu.astu.common.dto;

import et.edu.astu.common.interfaces.TransactionResponse;

import java.util.List;

public record TransactionResponses(long count, List<TransactionResponse> transactions) { }
