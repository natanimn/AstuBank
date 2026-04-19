package et.edu.astu.core.models.transactions;

import et.edu.astu.core.models.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@DiscriminatorColumn(name = "transaction_type")
@NoArgsConstructor
public class Transaction {
    @Id
    private Long id;

    @Column(nullable = false)
    @Min(5)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account holder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
