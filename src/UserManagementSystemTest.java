import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagementSystemTest {
    private UserManagementSystem ums;

    @BeforeEach
    public void setUp() {
        ums = new UserManagementSystem();
    }


    @Test
    public void testAuthenticateValidUser() {
        // Add a test user to authenticate
        ums.addUser("john_doe", "john@example.com", "password123", UserType.REGULAR);


        User user = ums.authenticate("john_doe", "password123");
        assertNotNull(user, "User should be authenticated");
        assertEquals("john_doe", user.getUsername());
        assertEquals(UserType.REGULAR, user.getUserType());
    }


    @Test
    public void testAuthenticateInvalidPassword() {
        ums.addUser("jane_doe", "jane@example.com", "password123", UserType.POWER);


        User user = ums.authenticate("jane_doe", "wrongpassword");
        assertNull(user, "Authentication should fail with incorrect password");
    }


    @Test
    public void testAuthenticateNonExistentUser() {
        User user = ums.authenticate("non_existent_user", "any_password");
        assertNull(user, "Authentication should fail for non-existent user");
    }


    @Test
    public void testAddUserSuccess() {
        boolean isAdded = ums.addUser("new_user", "new_user@example.com", "securepassword", UserType.REGULAR);
        assertTrue(isAdded, "New user should be added successfully");


        User user = ums.authenticate("new_user", "securepassword");
        assertNotNull(user, "Newly added user should be able to authenticate");
        assertEquals("new_user", user.getUsername());
        assertEquals(UserType.REGULAR, user.getUserType());
    }


    @Test
    public void testAddUserDuplicateUsername() {
        ums.addUser("duplicate_user", "user1@example.com", "password123", UserType.POWER);


        boolean isAdded = ums.addUser("duplicate_user", "user2@example.com", "differentpass", UserType.REGULAR);
        assertFalse(isAdded, "Should not add a user with a duplicate username");
    }


    @Test
    public void testAccessControlRegularUser() {
        ums.addUser("readonly_user", "readonly@example.com", "readonlypass", UserType.REGULAR);
        User user = ums.authenticate("readonly_user", "readonlypass");

        assertNotNull(user, "Regular user should authenticate");
        assertEquals(UserType.REGULAR, user.getUserType());


        boolean isAdded = ums.addUser("readonly_user2", "another@example.com", "password", UserType.REGULAR);
        assertFalse(isAdded, "Regular user should not be able to add another user (assuming restricted in implementation)");
    }


    @Test
    public void testAccessControlAdminUser() {
        ums.addUser("admin_user", "admin@example.com", "adminpass", UserType.ADMIN);
        User user = ums.authenticate("admin_user", "adminpass");

        assertNotNull(user, "Admin user should authenticate");
        assertEquals(UserType.ADMIN, user.getUserType());


        boolean isAdded = ums.addUser("admin_added_user", "newadmin@example.com", "newpass", UserType.POWER);
        assertTrue(isAdded, "Admin user should be able to add another user");
    }
}
