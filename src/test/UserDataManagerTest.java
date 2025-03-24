package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.LibraryModel;
import model.Song;
import model.UserDataManager;

import static org.junit.Assert.*;
import java.io.File;

public class UserDataManagerTest {

    private static final String TEST_FILE = "user_data.ser";

    @Before
    public void setUp() {
        // Ensure the test file does not exist before each test
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @After
    public void tearDown() {
        // Clean up the test file after each test
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveAndLoadLibraryData() {
        LibraryModel library = new LibraryModel();
        library.addSongToLibrary(new Song("Test Song", "Test Artist", "Test Album"));

        // Test saving the library
        assertTrue(UserDataManager.saveLibraryData(library));

        // Test loading the library
        LibraryModel loadedLibrary = UserDataManager.loadLibraryData();
        assertNotNull(loadedLibrary);
        assertEquals(1, loadedLibrary.getLibrary().size());

        // Verify data integrity
        Song song = loadedLibrary.getLibrary().get(0);
        assertEquals("Test Song", song.getTitle());
        assertEquals("Test Artist", song.getArtist());
        assertEquals("Test Album", song.getAlbum());
    }

    @Test
    public void testLoadLibraryDataWhenFileDoesNotExist() {
        // Ensure the file does not exist
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }

        // Loading data from a non-existent file should return a new LibraryModel
        LibraryModel library = UserDataManager.loadLibraryData();
        assertNotNull(library);
        assertEquals(0, library.getLibrary().size());
    }

    @Test
    public void testLoadLibraryDataWithCorruptedFile() {
        // Create an empty/corrupted file
        try {
            File file = new File(TEST_FILE);
            file.createNewFile();
        } catch (Exception e) {
            fail("Failed to create corrupted file for test.");
        }

        // Attempt to load from a corrupted file, should return a new LibraryModel instead of failing
        LibraryModel library = UserDataManager.loadLibraryData();
        assertNotNull(library);
        assertEquals(0, library.getLibrary().size());
    }
}
