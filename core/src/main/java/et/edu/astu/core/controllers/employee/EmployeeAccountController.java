package et.edu.astu.core.controllers.employee;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.CreateAccountRequest;
import et.edu.astu.common.dto.DepositRequest;
import et.edu.astu.common.dto.DepositResponse;
import et.edu.astu.common.dto.OTPResponse;
import et.edu.astu.common.dto.TransactionResponses;
import et.edu.astu.common.dto.TransferRequest;
import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.core.services.AccountService;
import et.edu.astu.core.services.OTPService;
import et.edu.astu.core.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EmployeeAccountController class.
 *
 * @author Natanim
 */
@RestController
@RequestMapping("/api/e/account")
@RequiredArgsConstructor
public class EmployeeAccountController {
    private final AccountService accountService;
    private final OTPService otpService;
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request){
        return new ResponseEntity<>(accountService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{ac}")
    public ResponseEntity<AccountResponse> findCustomer(@PathVariable Long ac){
        return new ResponseEntity<>(accountService.findCustomer(ac), HttpStatus.OK);
    }

    @PostMapping("/{ac}/generate/otp")
    public ResponseEntity<OTPResponse> generateOTP(@PathVariable Long ac){
        return new ResponseEntity<>(otpService.generateUserLoginOtp(ac), HttpStatus.CREATED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(HttpServletRequest http, @RequestBody DepositRequest request){
        String employeeId = http.getAttribute("employeeId").toString();
        return new ResponseEntity<>(transactionService.deposit(employeeId, request), HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<DepositResponse> withdraw(HttpServletRequest http, @RequestBody DepositRequest request){
        String employeeId = http.getAttribute("employeeId").toString();
        return new ResponseEntity<>(transactionService.withdraw(employeeId, request), HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request){
        return new ResponseEntity<>(transactionService.transfer(request), HttpStatus.CREATED);
    }

    @GetMapping("/{ac}/transactions")
    public ResponseEntity<TransactionResponses> findTransactions(@PathVariable Long ac, @PageableDefault Pageable pageable){
        return new ResponseEntity<>(transactionService.findTransaction(ac, pageable), HttpStatus.OK);
    }

    @PostMapping("/{ac}/disconnect/telegram")
    public ResponseEntity<AccountResponse> disconnectTelegram(@PathVariable Long ac){
        accountService.unlinkWithTelegram(ac);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
