package et.edu.astu.core.controllers.user;

import et.edu.astu.core.dtos.TransferRequest;
import et.edu.astu.core.dtos.TransferResponse;
import et.edu.astu.core.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/u/transaction")
@PreAuthorize("hasAuthority('connected')")
@RequiredArgsConstructor
public class TransactionUserAccountController {
    private final TransactionService service;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request){
        return new ResponseEntity<>(service.transfer(request), HttpStatus.CREATED);
    }
}
