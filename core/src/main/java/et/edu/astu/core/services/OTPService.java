package et.edu.astu.core.services;

import et.edu.astu.core.dtos.OTPResponse;
import et.edu.astu.core.dtos.UserLoginOTPValidationRequest;
import et.edu.astu.core.generators.OTPGenerator;
import et.edu.astu.core.models.otp.OTP;
import et.edu.astu.core.repositories.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepository repository;
    private final OTPGenerator generator;

    public OTPResponse generateUserLoginOtp(Long accountNumber){
        if (accountNumber == null)
            throw new RuntimeException("Account number cannot be null nor empty");

        OTP otp = repository.findOTP(accountNumber).orElse(new OTP(accountNumber));
        String code = generator.generate();
        otp.setCode(code);
        repository.save(otp);
        return new OTPResponse(code);
    }

    public boolean validate(UserLoginOTPValidationRequest request){
        OTP otp = repository.findOTP(request.accountNumber()).orElseThrow();
        return otp.getCode().equals(request.code());
    }

}
