import java.util.UUID;

public class User {
    private String username;
    private String email;
    private String password;
    private String userID;
    private UserType userType;

    public User(String username, String email, String password, UserType userType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userID = UUID.randomUUID().toString();
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUserID() {
        return userID;
    }
    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return userID + " " + username + " " +email + " " + password + " " + userType;
    }
}
