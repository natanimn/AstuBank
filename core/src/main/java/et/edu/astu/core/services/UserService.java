package et.edu.astu.core.services;

import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.UserAccountResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import et.edu.astu.common.dto.UserResponse;
import et.edu.astu.core.models.Account;
import et.edu.astu.core.models.CustomUserDetails;
import et.edu.astu.core.models.User;
import et.edu.astu.core.repositories.UserRepository;
import et.edu.astu.core.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository repository;
    private OTPService otpService;
    private JwtService jwtService;
    private AccountService accountService;

    public User findById(Long userId){
        return repository.findById(userId).orElseThrow();
    }

    public void insert(Long userId){
        if (repository.existsByUserId(userId)) return;

        User user = new User(userId);
        repository.save(user);
    }

    public UserResponse getMe(Long userId){
        User user = repository.findByUserId(userId).orElseThrow();
        return new UserResponse(user.getAccount() == null);
    }

    public User getUser(Long userId){
        return repository.findByUserId(userId).orElse(null);
    }

    public UserAccountResponse findUserAccount(Long userId){
        User user = getUser(userId);
        Account account = user.getAccount();
        if (account == null)
            throw new RuntimeException("No account connected");
        return Mapper.mapUserAccount(account);
    }

    public CustomUserDetails getCustomUser(Long userId){
        return new CustomUserDetails(repository.findByUserId(userId).orElseThrow());
    }

    public LoginResponse login(UserLoginOTPRequest request){
        if (request.accountNumber() == null)
            throw new RuntimeException("Account number required");
        if (request.code() == null)
            throw new RuntimeException("OTP required");
        if (request.userId() == null)
            throw new RuntimeException("User id required");

        if (otpService.validate(request)){
            accountService.linkWithTelegram(request.accountNumber(), request.userId());
            return new LoginResponse(
                    jwtService.generateUserJwt(
                            request.accountNumber(),
                            request.userId()
                    )
            );
        }
        throw new RuntimeException("Invalid credentials");
    }

    public boolean exists(Long userId){
        return repository.existsByUserId(userId);
    }
}
