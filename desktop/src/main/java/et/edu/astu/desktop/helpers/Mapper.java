package et.edu.astu.desktop.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import et.edu.astu.desktop.http.ApiResponse;

import java.lang.reflect.Type;

public abstract class Mapper {
    private final static Gson gson;

    static {
        gson = new Gson();
    }

    public static <T> ApiResponse<T> map(String json, Type type){
        Type resultType = TypeToken.getParameterized(ApiResponse.class, type).getType();
        return gson.fromJson(json, resultType);
    }
}
