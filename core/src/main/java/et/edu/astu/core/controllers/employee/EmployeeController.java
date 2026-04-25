package et.edu.astu.core.controllers.employee;

import et.edu.astu.common.dto.EmployeeLoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.core.services.AccountService;
import et.edu.astu.core.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/e")
@RequiredArgsConstructor
public class EmployeeController {
    private final AccountService accountService;
    private final EmployeeService service;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody EmployeeLoginRequest request){
        return new ResponseEntity<>(service.login(request), HttpStatus.OK);
    }
}
