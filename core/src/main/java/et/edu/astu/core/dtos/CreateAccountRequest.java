package et.edu.astu.core.dtos;

import java.time.LocalDateTime;

/**
 * Account creation DTO class. Account number is generated automatically.
 * @param firstName first name
 * @param middleName father name
 * @param lastName grandfather name
 * @param phone phone number
 * @param initialBalance initial deposit money
 * @param birthDate birthdate
 * @author Natanim
 */
public record CreateAccountRequest(
        String firstName,
        String middleName,
        String lastName,
        String phone,
        Integer initialBalance,
        LocalDateTime birthDate
) { }
