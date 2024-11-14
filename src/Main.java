public class Main {
    public static void main(String[] args) {
        UserManagementSystem ums = new UserManagementSystem();

        System.out.println("Testing Authentication:");
        ums.authenticate("mira", "password123");
        

        System.out.println("\nAdding a New User:");
        if (ums.addUser("jane_smith", "jane@example.com", "password123", UserType.POWER)) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add user.");
        }

        ums.authenticate("jane_smith", "password123");
    }
}
