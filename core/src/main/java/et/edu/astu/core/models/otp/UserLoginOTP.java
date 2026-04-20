package et.edu.astu.core.models.otp;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("USER_LOGIN")
@Getter
@Setter
@NoArgsConstructor
public class UserLoginOTP extends OTP{
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public UserLoginOTP(Long accountNumber, Long userId) {
        this.accountNumber = accountNumber;
        this.userId = userId;
    }
}
