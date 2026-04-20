package et.edu.astu.core.repositories;

import et.edu.astu.core.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNumber(Long accountNumber);

    @Query("SELECT a FROM Account a WHERE a.phone = :phone AND a.phone_search = TRUE")
    Optional<Account> findByPhoneNumber(@Param("phone") String phone);
}
