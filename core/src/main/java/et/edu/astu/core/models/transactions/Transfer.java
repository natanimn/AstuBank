package et.edu.astu.core.models.transactions;

import et.edu.astu.core.models.Account;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("TRANSFER")
public class Transfer extends Transaction {
    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiver;
}
