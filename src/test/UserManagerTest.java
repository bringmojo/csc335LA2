package test;

import model.User;
import model.UserManager;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class UserManagerTest {

    private static final String TEST_USER = "testUser";
    private static final String TEST_PASSWORD = "testPassword";

    @After
    public void cleanup() {
        // Clean up test files after test
        try {
            Files.deleteIfExists(Paths.get("users.json"));
            Files.deleteIfExists(Paths.get("userdata/" + TEST_USER + ".dat"));
        } catch (Exception e) {
            System.out.println("Error cleaning up test files: " + e.getMessage());
        }
    }

    @Test
    public void testUserRegistration() {
        UserManager userManager = new UserManager();

        // Register a new user
        boolean result = userManager.registerUser(TEST_USER, TEST_PASSWORD);
        assertTrue(result);

        // Try to register the same user again
        result = userManager.registerUser(TEST_USER, TEST_PASSWORD);
        assertFalse(result);
    }

    @Test
    public void testUserLogin() {
        UserManager userManager = new UserManager();

        // Register a new user
        userManager.registerUser(TEST_USER, TEST_PASSWORD);

        // Test successful login
        boolean loginResult = userManager.login(TEST_USER, TEST_PASSWORD);
        assertTrue(loginResult);

        User currentUser = userManager.getCurrentUser();
        assertNotNull(currentUser);
        assertEquals(TEST_USER, currentUser.getUsername());

        // Test unsuccessful login with wrong password
        loginResult = userManager.login(TEST_USER, "wrongPassword");
        assertFalse(loginResult);

        // Test unsuccessful login with wrong username
        loginResult = userManager.login("wrongUser", TEST_PASSWORD);
        assertFalse(loginResult);
    }

    @Test
    public void testUserLogout() {
        UserManager userManager = new UserManager();

        // Register and login
        userManager.registerUser(TEST_USER, TEST_PASSWORD);
        userManager.login(TEST_USER, TEST_PASSWORD);

        // Logout
        userManager.logout();

        // Check that current user is null
        assertNull(userManager.getCurrentUser());
    }
}