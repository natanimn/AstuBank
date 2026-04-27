package et.edu.astu.core.services;

import et.edu.astu.common.dto.AdminLoginResponse;
import et.edu.astu.common.dto.LoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * AuthService class.
 *
 * @author Natanim
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("{app.admin.username}")
    private String ADMIN_USERNAME;

    @Value("{app.admin.password}")
    private String ADMIN_PASSWORD;

    private final EmployeeService employeeService;
    private final UserService userService;

    public LoginResponse loginEmployee(LoginRequest request){
        return employeeService.login(request);
    }

    public LoginResponse loginUser(UserLoginOTPRequest request){
        return userService.login(request);
    }

    public AdminLoginResponse loginAdmin(LoginRequest request){
        if (request.username() == null || request.password() == null)
            throw new RuntimeException("Both username and password is required");

        if (request.username().equals(ADMIN_USERNAME) && request.password().equals(ADMIN_PASSWORD))
            return new AdminLoginResponse(true);

        return new AdminLoginResponse(false);
    }
}
