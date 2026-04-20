package et.edu.astu.core.repositories;

import et.edu.astu.core.models.otp.OTP;
import et.edu.astu.core.models.otp.UserLoginOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    @Query(value = "SELECT * FROM temporary_otp WHERE account_number = :ac AND user_id = :id", nativeQuery = true)
    Optional<UserLoginOTP> findOTP(@Param("ac") long accountNumber, @Param("id") long userId);
}
