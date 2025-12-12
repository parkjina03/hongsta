package hongsta;

public class SessionUser {
    public static long id = 0;
    public static String username = null;

    public static void clear() {
        id = 0;
        username = null;
    }

    public static long getUserId() {
        return id;
    }

    public static String getUsername() {
        return username;
    }
}
