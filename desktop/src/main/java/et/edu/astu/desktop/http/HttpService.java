package et.edu.astu.desktop.http;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.AdminLoginResponse;
import et.edu.astu.common.dto.CreateAccountRequest;
import et.edu.astu.common.dto.CreatedEmployeeResponse;
import et.edu.astu.common.dto.DepositRequest;
import et.edu.astu.common.dto.DepositResponse;
import et.edu.astu.common.dto.EmployeeRequest;
import et.edu.astu.common.dto.LoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.OTPResponse;
import et.edu.astu.common.dto.TransactionResponses;
import et.edu.astu.common.dto.TransferRequest;
import et.edu.astu.common.dto.TransferResponse;

/**
 * HttpService class.
 *
 * @author Natanim
 */
public class HttpService {
    private final Client client;

    public HttpService(){
        this.client = new Client();
    }

    public CreatedEmployeeResponse createEmployee(EmployeeRequest request){
        return client.post("/admin/create/employee", request, CreatedEmployeeResponse.class);
    }

    public AdminLoginResponse loginAsAdmin(LoginRequest request){
        return client.post("/auth/admin/login", request, AdminLoginResponse.class);
    }

    public LoginResponse loginAsEmployee(LoginRequest request){
        return client.post("/auth/employee/login", request, LoginResponse.class);
    }

    public AccountResponse createAccount(CreateAccountRequest request, String token){
        return client.post("/e/account/create", token, request, AccountResponse.class);
    }

    public AccountResponse getAccount(long ac, String token){
        return client.get("/e/account/"+ac, token, AccountResponse.class);
    }

    public OTPResponse generateOTP(long ac, String token){
        return client.post("/e/account/%d/generate/otp".formatted(ac), token, OTPResponse.class);
    }

    public DepositResponse deposit(DepositRequest request, String token){
        return client.post("/e/account/deposit", token, request, DepositResponse.class);
    }

    public DepositResponse withdraw(DepositRequest request, String token){
        return client.post("/e/account/withdraw", token, request, DepositResponse.class);
    }

    public TransferResponse transfer(TransferRequest request, String token){
        return client.post("/e/account/transfer", token, request, TransferResponse.class);
    }

    public TransactionResponses getTransactions(long ac, String token){
        return client.get("/e/account/%d/transactions".formatted(ac), token, TransactionResponses.class);
    }

    public TransferResponse disconnect(long ac, String token){
        return client.post("/e/account/%d/disconnect/telegram".formatted(ac), token, TransferResponse.class);
    }

}
