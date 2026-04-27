package et.edu.astu.desktop.http;

import com.google.gson.Gson;
import et.edu.astu.desktop.helpers.Mapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.lang.reflect.Type;

public class Client {
    private final OkHttpClient client;
    private final Gson gson;

    public Client() {
        client = new OkHttpClient.Builder().build();
        gson = new Gson();
    }

    private Request.Builder prepareRequest(String endpoint, String token){
        String URL = "http://localhost:8080/api";
        return new Request.Builder()
                .addHeader("Authorization", "BANK " + token)
                .url(URL + endpoint);
    }

    private Request.Builder prepareRequestWithNoHeader(String endpoint){
        String URL = "http://localhost:8080/api";
        return new Request.Builder()
                .url(URL + endpoint);
    }

    public <T> T post(String path, String token,  Object body, Type type){
        Request.Builder builder = prepareRequest(path, token);
        RequestBody requestBody = RequestBody.create(gson.toJson(body), MediaType.get("application/json"));
        return makeRequest(builder.post(requestBody).build(), type);
    }

    public <T> T post(String path,  Object body, Type type){
        Request.Builder builder = prepareRequestWithNoHeader(path);
        RequestBody requestBody = RequestBody.create(gson.toJson(body), MediaType.get("application/json"));
        return makeRequest(builder.post(requestBody).build(), type);
    }

    public <T> T post(String path,  String token, Type type){
        Request.Builder builder = prepareRequestWithNoHeader(path);
        RequestBody requestBody = RequestBody.create("{}", MediaType.get("application/json"));
        return makeRequest(builder.post(requestBody).build(), type);
    }

    public <T> T get(String path, String token, Type type){
        Request.Builder builder = prepareRequest(path, token);
        return makeRequest(builder.get().build(), type);
    }

    public <T> T get(String path, Type type){
        Request.Builder builder = prepareRequestWithNoHeader(path);
        return makeRequest(builder.get().build(), type);
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
