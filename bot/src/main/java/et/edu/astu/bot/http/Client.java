package et.edu.astu.bot.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.lang.reflect.Type;

public class Client {
    private final OkHttpClient client;
    private final Gson gson;

    public Client() {
        client = new OkHttpClient.Builder().build();
        gson = new Gson();
    }

    public <T> ApiResponse<T> makeRequest(String endpoint, Method method, Long userId, Object body, Type type){
        RequestBody requestBody = RequestBody.create(gson.toJson(body), MediaType.get("application/json"));
        String URL = "http://localhost:8080/api";
        Request.Builder builder = new Request.Builder()
                .url(URL + endpoint)
                .addHeader("Authorization", "USER " + userId);
        builder.setBody$okhttp(requestBody);
        builder.setMethod$okhttp(method.name());

        try (Response response = client.newCall(builder.build()).execute()){
            Type result = TypeToken.getParameterized(ApiResponse.class, type).getType();
            return gson.fromJson(response.body().string(), result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
