package et.edu.astu.core.repositories;

import et.edu.astu.core.interfaces.CustomerResponse;
import et.edu.astu.core.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(Long accountNumber);

    Optional<Account> findByPhone(String phone);

    @Query(value =
            "SELECT account_number as accountNumber, first_name as firstName, middle_name as middleName, last_name as lastName, " +
            "linked_telegram_user_id as telegramUserId, telegram_link_verified as telegramLinkVerified FROM accounts WHERE account_number = :ac",
     nativeQuery = true
    )
    CustomerResponse findAccount(@Param("ac") Long ac);
}
