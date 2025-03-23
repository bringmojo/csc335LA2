package test;

import model.LibraryModel;
import model.Song;
import model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("testUser", "hashedPassword", "salt");

        assertEquals("testUser", user.getUsername());
        assertEquals("hashedPassword", user.getPasswordHash());
        assertEquals("salt", user.getSalt());

        LibraryModel library = user.getUserLibrary();
        assertNotNull(library);
        assertEquals(0, library.getLibrary().size());
    }

    @Test
    public void testSetUserLibrary() {
        User user = new User("testUser", "hashedPassword", "salt");
        LibraryModel newLibrary = new LibraryModel();

        // Add a song to the new library
        newLibrary.addSongToLibrary(new Song("Test Song", "Test Artist", "Test Album"));

        // Set the new library
        user.setUserLibrary(newLibrary);

        // Check that the library was set
        assertEquals(1, user.getUserLibrary().getLibrary().size());
    }

    @Test
    public void testUserEquals() {
        User user1 = new User("testUser", "hashedPassword1", "salt1");
        User user2 = new User("testUser", "hashedPassword2", "salt2");
        User user3 = new User("differentUser", "hashedPassword1", "salt1");

        // Same username should be equal
        assertEquals(user1, user2);

        // Different username should not be equal
        assertEquals(false, user1.equals(user3));
    }
}