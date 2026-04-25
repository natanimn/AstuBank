package et.edu.astu.common.dto;

public record CreatedEmployeeResponse(
        String firstName,
        String middleName,
        String lastName,
        String username,
        String password
) { }
