package et.edu.astu.core.repositories;

import et.edu.astu.core.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(Long accountNumber);

    Optional<Account> findByPhone(String phone);
}
