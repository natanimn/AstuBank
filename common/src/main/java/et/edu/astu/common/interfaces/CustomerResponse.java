package et.edu.astu.common.interfaces;

public interface CustomerResponse {
    Long getAccountNumber();
    String getFirstName();
    String getMiddleName();
    String getLastName();
    Long getTelegramUserId();
    Boolean getTelegramLinkVerified();
}
