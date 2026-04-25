package et.edu.astu.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "accounts")
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

    @Column(nullable = false)
    @Pattern(regexp = "(251[97])\\d{8}", message = "Invalid phone number format. Only 2519... or 2517... format is allowed")
    private String phone;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Min(0)
    private Double balance;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "account")
    private User user;

    public Account(Long accountNumber, String firstName, String middleName, String lastName, String phone, LocalDateTime birthDate, Double balance) {
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
    }

    public boolean linkedWithTelegram(){
        return user != null;
    }
}
