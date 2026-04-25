package et.edu.astu.bot.http;

import et.edu.astu.common.dto.AccountResponse;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.common.dto.UserAccountResponse;
import et.edu.astu.common.dto.UserLoginOTPRequest;
import et.edu.astu.common.dto.UserResponse;
import et.edu.astu.common.interfaces.TransactionResponse;

import java.util.List;

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

    public List<TransactionResponse> getMyTransactions(Long userId){
        return client.getList("/u/my/transactions", userId, TransactionResponse.class);
    }

    public List<TransactionResponse> getMyTransactions(Long userId, int page){
        return client.getList("/u/my/transactions?page=%d".formatted(page), userId, TransactionResponse.class);
    }

    public void login(UserLoginOTPRequest request){
        client.post("/auth/user/login", request.userId(), request, LoginResponse.class);
    }

    public AccountResponse searchAccount(Long userId, String phone){
        return client.get("/u/search/account?phone=%s".formatted(phone), userId, AccountResponse.class);
    }


}
