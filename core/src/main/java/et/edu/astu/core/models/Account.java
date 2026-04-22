package et.edu.astu.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
                ),
                @Index(
                        name = "account_number_account_index",
                        columnList = "account_number"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String id;

    @Column(name = "account_number", unique = true)
    private Long accountNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Pattern(regexp = "(251[97])\\d{8}", message = "Invalid phone number format. Only 2519... or 2517... format is allowed")
    private String phone;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Min(0)
    private Integer balance;

    @Column(name = "linked_telegram_user_id", unique = true)
    private Long telegramUserId;

    @Column(name = "telegram_link_verified")
    @JsonIgnore
    private Boolean linkVerified;

    @Column(name = "phone_search")
    private Boolean phoneSearch;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    public Account(Long accountNumber, String firstName, String middleName, String lastName, String phone, LocalDateTime birthDate, Integer balance) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.balance = balance;
    }

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.phoneSearch = false;
    }

    public boolean linkedWithTelegram(){
        return telegramUserId != null;
    }
}
