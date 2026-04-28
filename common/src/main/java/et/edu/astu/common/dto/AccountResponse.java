package et.edu.astu.common.dto;

/**
 * Describes response of an account
 * @param accountNumber account number
 * @param firstName first name
 * @param middleName father name
 * @param lastName grandfather name
 * @param connectedWithTelegram account connection with telegram
 * @author Natanim
 */
public record AccountResponse(
        Long accountNumber,
        String firstName,
        String middleName,
        String lastName,
        boolean connectedWithTelegram
) { }
