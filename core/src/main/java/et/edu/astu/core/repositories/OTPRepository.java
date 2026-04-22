package et.edu.astu.core.repositories;

import et.edu.astu.core.models.otp.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    @Query(value = "SELECT * FROM temporary_otp WHERE account_number = :ac", nativeQuery = true)
    Optional<OTP> findOTP(@Param("ac") long accountNumber);
}
