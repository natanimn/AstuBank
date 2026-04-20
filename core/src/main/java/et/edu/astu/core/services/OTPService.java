package et.edu.astu.core.services;

import et.edu.astu.core.dtos.OTPResponse;
import et.edu.astu.core.dtos.UserLoginOTPRequest;
import et.edu.astu.core.dtos.UserLoginOTPValidationRequest;
import et.edu.astu.core.generators.OTPGenerator;
import et.edu.astu.core.models.otp.UserLoginOTP;
import et.edu.astu.core.repositories.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepository repository;
    private final OTPGenerator generator;

    public OTPResponse generateUserLoginOtp(UserLoginOTPRequest request){
        if (request.accountNumber() == null)
            throw new RuntimeException("Account number cannot be null nor empty");
        if (request.userId() == null)
            throw new RuntimeException("User ID cannot be null nor empty");

        UserLoginOTP otp = repository.findOTP(request.accountNumber(), request.userId())
                .orElse(new UserLoginOTP(request.accountNumber(), request.userId()));
        String code = generator.generate();
        otp.setCode(code);
        repository.save(otp);
        return new OTPResponse(code);
    }

    public boolean validate(UserLoginOTPValidationRequest request){
        UserLoginOTP otp = repository.findOTP(request.accountNumber(), request.userId()).orElseThrow();
        return otp.getCode().equals(request.code());
    }

}
