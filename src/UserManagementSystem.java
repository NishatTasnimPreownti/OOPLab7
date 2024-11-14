import java.io.*;
import java.util.*;

public class UserManagementSystem {
    private static final String USER_FILE = "User.csv";
    private static final String ADMIN_FILE = "Admin.csv";

    private Map<String, User> users = new HashMap<>();

    public UserManagementSystem() {
        loadUsers();
    }

    // Load users from files
    private void loadUsers() {
        loadFromFile(USER_FILE, false);
        loadFromFile(ADMIN_FILE, true);
    }

    private void loadFromFile(String fileName, boolean isAdmin) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                UserType userType = isAdmin ? UserType.ADMIN : UserType.valueOf(data[4]);
                User user = new User(data[1], data[2], data[3], userType);
                users.put(data[1], user);
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // Authenticate user
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Welcome, " + user.getUsername() + "! Your role is: " + user.getUserType());
            return user;
        } else {
            System.out.println("Authentication failed.");
            return null;
        }
    }

    // Add user (for admin use)
    public boolean addUser(String username, String email, String password, UserType userType) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return false;
        }
        User newUser = new User(username, email, password, userType);
        users.put(username, newUser);
        saveUserToFile(newUser);
        return true;
    }

    private void saveUserToFile(User user) {
        String fileName = (user.getUserType() == UserType.ADMIN) ? ADMIN_FILE : USER_FILE;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(user.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
}
