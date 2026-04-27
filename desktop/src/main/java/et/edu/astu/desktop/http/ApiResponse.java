package et.edu.astu.desktop.http;

/**
 * AoiResponse class
 * @param <T>
 *
 * @author Natanim
 */
public class ApiResponse<T> {
    private T result;

    public T getResult() {
        return result;
    }
}
