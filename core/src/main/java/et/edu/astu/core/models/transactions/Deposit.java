package et.edu.astu.core.models.transactions;

import et.edu.astu.core.models.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("DEPOSIT")
public class Deposit extends Transaction{
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee performer;
}
