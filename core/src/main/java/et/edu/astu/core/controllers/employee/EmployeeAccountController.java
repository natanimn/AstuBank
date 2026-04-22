package et.edu.astu.core.controllers.employee;

import et.edu.astu.core.dtos.AccountResponse;
import et.edu.astu.core.dtos.CreateAccountRequest;
import et.edu.astu.core.dtos.OTPResponse;
import et.edu.astu.core.interfaces.CustomerResponse;
import et.edu.astu.core.services.AccountService;
import et.edu.astu.core.services.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/e/account")
@RequiredArgsConstructor
public class EmployeeAccountController {
    private final AccountService accountService;
    private final OTPService otpService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request){
        return new ResponseEntity<>(accountService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{ac}")
    public ResponseEntity<CustomerResponse> findCustomer(@PathVariable Long ac){
        return new ResponseEntity<>(accountService.findCustomer(ac), HttpStatus.OK);
    }

    @PostMapping("/{ac}/generate/otp")
    public ResponseEntity<OTPResponse> generateOTP(@PathVariable Long ac){
        return new ResponseEntity<>(otpService.generateUserLoginOtp(ac), HttpStatus.CREATED);
    }
}
