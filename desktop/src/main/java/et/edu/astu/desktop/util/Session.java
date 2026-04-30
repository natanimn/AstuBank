package et.edu.astu.desktop.util;

public class Session {
    private static String token;
    private static String username;
    private static String role;

    public static void setSession(String t, String u, String r) {
        token = t;
        username = u;
        role = r;
    }

    public static String getToken() { return token; }
    public static String getUsername() { return username; }
    public static String getRole() { return role; }
    
    public static void clear() {
        token = null;
        username = null;
        role = null;
    }
}
