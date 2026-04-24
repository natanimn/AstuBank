package et.edu.astu.core.services;

import et.edu.astu.common.dto.EmployeeLoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeService employeeService;
    private final UserService userService;

    public LoginResponse loginEmployee(EmployeeLoginRequest request){
        return employeeService.login(request);
    }

    public LoginResponse loginUser(UserLoginOTPRequest request){
        return userService.login(request);
    }
}
