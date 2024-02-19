// Session.java
package mikeTest;

public class Session {
    private static session_user_data loggedInUser;

    public static session_user_data getUser() {
        return loggedInUser;
    }

    public static void setUser(session_user_data user) {
        loggedInUser = user;
    }
}
