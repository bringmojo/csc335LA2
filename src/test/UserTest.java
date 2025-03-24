package test;

import model.LibraryModel;
import model.Song;
import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

public class UserTest {

    /*Test User object creation and getter methods */
    @Test
    public void testUserCreation() {
        User user = new User("testUser", "hashedPassword", "salt");

        assertEquals("testUser", user.getUsername());
        assertEquals("hashedPassword", user.getPasswordHash());
        assertEquals("salt", user.getSalt());

        LibraryModel library = user.getUserLibrary();
        assertNotNull("Library should not be null", library);
        assertEquals("Library should be empty initially", 0, library.getLibrary().size());
    }

    /*Test setting and getting a user's library */
    @Test
    public void testSetUserLibrary() {
        User user = new User("testUser", "hashedPassword", "salt");
        LibraryModel newLibrary = new LibraryModel();

        // Add a song to the new library
        newLibrary.addSongToLibrary(new Song("Test Song", "Test Artist", "Test Album"));

        // Set the new library
        user.setUserLibrary(newLibrary);

        // Check that the library was correctly set
        assertEquals("Library should contain one song", 1, user.getUserLibrary().getLibrary().size());
    }

    /*Test equals() method for different scenarios */
    @Test
    public void testUserEquals() {
        User user1 = new User("testUser", "hashedPassword1", "salt1");
        User user2 = new User("testUser", "hashedPassword2", "salt2");
        User user3 = new User("differentUser", "hashedPassword1", "salt1");
        User user4 = new User("testUser", "hashedPassword1", "salt1");

        // Same username should be equal (even if password/salt differ)
        assertEquals(user1, user2);
        assertEquals(user1, user4);

        // Different username should not be equal
        assertNotEquals(user1, user3);

        // Check against null and different class
        assertNotEquals(user1, null);
        assertNotEquals(user1, "stringObject");
    }

    /*Test hashCode consistency */
    @Test
    public void testUserHashCode() {
        User user1 = new User("testUser", "hashedPassword1", "salt1");
        User user2 = new User("testUser", "hashedPassword2", "salt2");

        // Users with the same username should have the same hashcode
        assertEquals(user1.hashCode(), user2.hashCode());

        // Users with different usernames should have different hashcodes
        User user3 = new User("differentUser", "hashedPassword1", "salt1");
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    /*Test edge cases */
    @Test
    public void testUserEdgeCases() {
        // Test empty username
        User emptyUser = new User("", "hashedPassword", "salt");
        assertEquals("", emptyUser.getUsername());

        // Test null username
        User nullUser = new User(null, "hashedPassword", "salt");
        assertNull(nullUser.getUsername());
    }
}
