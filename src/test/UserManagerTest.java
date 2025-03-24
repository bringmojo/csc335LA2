package test;

import model.LibraryModel;
import model.Song;
import model.UserManager;
import org.junit.After;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.junit.Before;


public class UserManagerTest {
    
    private static final String TEST_USER = "testUser";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String INVALID_USER = "invalidUser";
    private static final String WRONG_PASSWORD = "wrongPassword";

    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = new UserManager();
    }

    @After
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get("users.json"));
            Files.deleteIfExists(Paths.get("userdata/" + TEST_USER + ".dat"));
            Files.deleteIfExists(Paths.get("userdata/" + INVALID_USER + ".dat"));
        } catch (Exception e) {
            System.out.println("Error cleaning up test files: ");
        }
    }

    @Test
    public void testUserRegistration() {
        assertTrue(userManager.registerUser(TEST_USER, TEST_PASSWORD));

        // Duplicate registration should fail
        assertFalse(userManager.registerUser(TEST_USER, TEST_PASSWORD));

        
    }

    @Test
    public void testUserLogin() {
        userManager.registerUser(TEST_USER, TEST_PASSWORD);

        // Successful login
        assertTrue(userManager.login(TEST_USER, TEST_PASSWORD));
        assertNotNull(userManager.getCurrentUser());
        assertEquals(TEST_USER, userManager.getCurrentUser().getUsername());

        // Incorrect password should fail
        assertFalse(userManager.login(TEST_USER, WRONG_PASSWORD));

        // Non-existent user should fail
        assertFalse(userManager.login(INVALID_USER, TEST_PASSWORD));
    }

    @Test
    public void testUserLogout() {
        userManager.registerUser(TEST_USER, TEST_PASSWORD);
        userManager.login(TEST_USER, TEST_PASSWORD);
        assertNotNull(userManager.getCurrentUser());

        userManager.logout();
        assertNull(userManager.getCurrentUser());
    }

    @Test
    public void testSaveAndLoadUsers() {
        userManager.registerUser(TEST_USER, TEST_PASSWORD);
        UserManager newUserManager = new UserManager();
        assertTrue(newUserManager.login(TEST_USER, TEST_PASSWORD));
    }

    @Test
    public void testSaveAndLoadUserData() {
        userManager.registerUser(TEST_USER, TEST_PASSWORD);
        userManager.login(TEST_USER, TEST_PASSWORD);

        LibraryModel library = new LibraryModel();
        library.addSongToLibrary(new Song("Test Song", "Artist", "Album"));
        userManager.getCurrentUser().setUserLibrary(library);

        userManager.saveUserData(TEST_USER);

        UserManager newUserManager = new UserManager();
        newUserManager.login(TEST_USER, TEST_PASSWORD);

        assertNotNull(newUserManager.getCurrentUser().getUserLibrary());
        assertEquals(1, newUserManager.getCurrentUser().getUserLibrary().getLibrary().size());
    }

    @Test
    public void testRegisterAndLoginMultipleUsers() {
        assertTrue(userManager.registerUser("user1", "password1"));
        assertTrue(userManager.registerUser("user2", "password2"));

        assertTrue(userManager.login("user1", "password1"));
        assertEquals("user1", userManager.getCurrentUser().getUsername());

        userManager.logout();
        assertTrue(userManager.login("user2", "password2"));
        assertEquals("user2", userManager.getCurrentUser().getUsername());
    }

    @Test
    public void testLogoutWithoutLogin() {
        userManager.logout();
        assertNull(userManager.getCurrentUser());
    }

    @Test
    public void testSavingDataWithoutCurrentUser() {
        userManager.saveUserData(TEST_USER); // Should not throw any exceptions
    }
}
