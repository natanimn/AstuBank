package et.edu.astu.bot.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import et.edu.astu.bot.helpers.Mapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.lang.reflect.Type;
import java.util.List;

public class Client {
    private final OkHttpClient client;
    private final Gson gson;

    public Client() {
        client = new OkHttpClient.Builder().build();
        gson = new Gson();
    }

    private Request.Builder prepareRequest(String endpoint, Long userId){
        String URL = "http://localhost:8080/api";
        return new Request.Builder()
                .addHeader("Authorization", "USER " + userId)
                .url(URL + endpoint);
    }

    public <T> T post(String path, long userId,  Object body, Type type){
        Request.Builder builder = prepareRequest(path, userId);
        RequestBody requestBody = RequestBody.create(gson.toJson(body), MediaType.get("application/json"));
        return makeRequest(builder.post(requestBody).build(), type);
    }
    public <T> T get(String path, long userId, Type type){
        Request.Builder builder = prepareRequest(path, userId);
        return makeRequest(builder.get().build(), type);
    }

    public <T> T getList(String path, long userId, Type type){
        Request.Builder builder = prepareRequest(path, userId);
        Type listType = TypeToken.getParameterized(List.class, type).getType();
        return makeRequest(builder.get().build(), listType);
    }

    private <T> T makeRequest(Request request, Type type){
        try (Response response = client.newCall(request).execute()){
            ResponseBody body = response.body();
            String json = body.string();
            ApiResponse<T> apiResponse = Mapper.map(json, type);
            return apiResponse.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
