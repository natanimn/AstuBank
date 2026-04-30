package et.edu.astu.common.dto;

import java.util.List;

public record TransactionResponses(long count, List<TransactionResponse> transactions) { }
