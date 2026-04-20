package et.edu.astu.core.models.otp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "temporary_otp",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "account_number",
                                "user_id"
                        }
                )
        }
)
@Getter
@Setter
@DiscriminatorColumn(name = "otp_type")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
