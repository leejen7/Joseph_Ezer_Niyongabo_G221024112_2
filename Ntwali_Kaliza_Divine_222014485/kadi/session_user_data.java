// session_user_data.java
package kadi;

public class session_user_data {
    private String username;
    private String email;
    private String role;

    public session_user_data(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
