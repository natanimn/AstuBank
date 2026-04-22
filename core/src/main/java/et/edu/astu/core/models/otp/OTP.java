package et.edu.astu.core.models.otp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "temporary_otp")
@Getter
@Setter
@DiscriminatorColumn(name = "otp_type")
@NoArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountNumber;

    public OTP(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
