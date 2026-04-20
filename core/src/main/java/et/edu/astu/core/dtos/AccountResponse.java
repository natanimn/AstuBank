package et.edu.astu.core.dtos;

import et.edu.astu.core.models.Account;

/**
 * Describes response of an account
 * @param accountNumber account number
 * @param firstName first name
 * @param middleName father name
 * @param lastName grandfather name
 * @author Natanim
 */
public record AccountResponse(Long accountNumber, String firstName, String middleName, String lastName) {
    public static AccountResponse map(Account account){
        return new AccountResponse(
                account.getAccountNumber(),
                account.getFirstName(),
                account.getMiddleName(),
                account.getLastName()
        );
    }
}
