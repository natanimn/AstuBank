package et.edu.astu.core.repositories;

import et.edu.astu.common.dto.TransactionResponse;
import et.edu.astu.core.models.transactions.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * TransactionRepository interface
 *
 * @author Natanim
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(
            value = "SELECT transaction_id as transactionId, amount, transaction_type as type, created_at as createdAt " +
                    "FROM transactions WHERE account_id = :ac ORDER BY created_at DESC",
            countQuery = "SELECT count(id) FROM transactions WHERE account_id = :ac",
            nativeQuery = true
    )
    Page<TransactionResponse> findTransactions(@Param("ac") Long ac, Pageable pageable);

    Optional<Transaction> findByTransactionId(String trxId);

    long countByHolderAccountNumber(Long account);

}
