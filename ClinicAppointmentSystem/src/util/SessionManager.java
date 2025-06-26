package util;

public class SessionManager {
    private static String currentUser;
    private static String currentRole;

    public static void startSession(String user, String role) {
        currentUser = user;
        currentRole = role;
    }

    public static String getUser() {
        return currentUser;
    }

    public static String getRole() {
        return currentRole;
    }

    public static void endSession() {
        currentUser = null;
        currentRole = null;
    }

    public static boolean isActive() {
        return currentUser != null;
    }
}
