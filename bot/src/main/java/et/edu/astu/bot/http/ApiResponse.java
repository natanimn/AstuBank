package et.edu.astu.bot.http;

public class ApiResponse<T> {
    private T result;

    public ApiResponse(T result){
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
