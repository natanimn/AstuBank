package et.edu.astu.core.controllers.user;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.UserAccountResponse;
import et.edu.astu.common.dto.UserResponse;
import et.edu.astu.common.interfaces.TransactionResponse;
import et.edu.astu.core.services.AccountService;
import et.edu.astu.core.services.TransactionService;
import et.edu.astu.core.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/u")
@RequiredArgsConstructor
public class BasicUserAccountController {
    private final UserService service;
    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        return new ResponseEntity<>(service.getMe(userId), HttpStatus.OK);
    }

    @GetMapping("/my/account")
    @PreAuthorize("hasAuthority('connected')")
    public ResponseEntity<UserAccountResponse> getMyAccount(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        return new ResponseEntity<>(service.findUserAccount(userId), HttpStatus.OK);
    }

    @GetMapping("/my/transactions")
    @PreAuthorize("hasAuthority('connected')")
    public ResponseEntity<List<TransactionResponse>> myTransactions(HttpServletRequest request, @PageableDefault Pageable pageable){
        Long ac = (Long) request.getAttribute("account");
        return new ResponseEntity<>(transactionService.findTransaction(ac, pageable), HttpStatus.OK);
    }

    @GetMapping("/my/transactions/{id}")
    @PreAuthorize("hasAuthority('connected')")
    public ResponseEntity<Object> myTransaction(@PathVariable String trxId){
        return new ResponseEntity<>(transactionService.findTransaction(trxId), HttpStatus.OK);
    }

    @GetMapping("/search/account")
    public ResponseEntity<AccountResponse> searchAccount(@RequestParam String phone){
        return new ResponseEntity<>(accountService.searchByPhone(phone), HttpStatus.OK);
    }
}
