package et.edu.astu.core.controllers;

import et.edu.astu.common.dto.AdminLoginResponse;
import et.edu.astu.common.dto.LoginRequest;
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

/**
 * AuthController class.
 *
 * @author Natanim
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserLoginOTPRequest request){
        return new ResponseEntity<>(service.loginUser(request), HttpStatus.OK);
    }

    @PostMapping("/employee/login")
    public ResponseEntity<LoginResponse> employeeLogin(@RequestBody LoginRequest request){
        return new ResponseEntity<>(service.loginEmployee(request), HttpStatus.OK);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AdminLoginResponse> adminLogin(@RequestBody LoginRequest request){
        return new ResponseEntity<>(service.loginAdmin(request), HttpStatus.OK);
    }
}
