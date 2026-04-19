package et.edu.astu.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents user account information.
 * @author Natanim
 */
@Entity
@Table(
        name = "accounts",
        indexes = {
                @Index(
                        name = "telegram_user_id_account_index",
                        columnList = "linked_telegram_user_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Min(0)
    private Integer balance;

    @Column(name = "linked_telegram_user_id")
    private Long telegramUserId;
    
    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    public Account(Long accountNumber, String firstName, String middleName, String lastName, LocalDateTime birthDate, Integer balance) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.balance = balance;
    }

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public boolean linkedWithTelegram(){
        return telegramUserId != null;
    }
}
