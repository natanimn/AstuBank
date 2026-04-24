package et.edu.astu.core.controllers;

import et.edu.astu.common.dto.EmployeeLoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import et.edu.astu.core.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService service;

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserLoginOTPRequest request){
        return new ResponseEntity<>(service.loginUser(request), HttpStatus.OK);
    }

    @PostMapping("/employee/login")
    public ResponseEntity<LoginResponse> employeeLogin(@RequestBody EmployeeLoginRequest request){
        return new ResponseEntity<>(service.loginEmployee(request), HttpStatus.OK);
    }
}
