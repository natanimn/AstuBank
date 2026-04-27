package et.edu.astu.bot.http;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.TransactionResponses;
import et.edu.astu.common.dto.TransferResponse;
import et.edu.astu.common.dto.UserAccountResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import et.edu.astu.common.dto.UserResponse;
import et.edu.astu.common.dto.UserTransferRequest;

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

    public UserResponse getMe(Long userId){
        return client.get("/u/me", userId, UserResponse.class);
    }

    public UserAccountResponse getMyAccount(Long userId){
        return client.get("/u/my/account", userId, UserAccountResponse.class);
    }

    public TransactionResponses getMyTransactions(Long userId){
        return client.get("/u/my/transactions", userId, TransactionResponses.class);
    }

    public TransactionResponses getMyTransactions(Long userId, int page){
        return client.getList("/u/my/transactions?page=%d".formatted(page), userId, TransactionResponses.class);
    }

    public void login(UserLoginOTPRequest request){
        client.post("/auth/user/login", request.userId(), request, LoginResponse.class);
    }

    public AccountResponse searchAccount(Long userId, String phone){
        return client.get("/u/search/account?phone=%s".formatted(phone), userId, AccountResponse.class);
    }

    public AccountResponse searchAccount(Long userId, Long accountNumber){
        return client.get("/u/search/account?account=%s".formatted(accountNumber), userId, AccountResponse.class);
    }

    public TransferResponse transfer(UserTransferRequest request){
        return client.post("/u/transfer", request.userId(), request, TransferResponse.class);
    }

    public Object getTransaction(Long userId, String id){
        return client.get("/u/my/transactions/%s".formatted(id), userId, Object.class);
    }
}
