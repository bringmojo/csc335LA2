
/*
 * Author: Jifei Wang
 * This test file focuses on the functionality of the LibraryModel class. 
 * It checks the behavior of various methods in the LibraryModel, 
 * such as the constructor, searching songs by title or artist, managing albums, 
 * creating playlists, and rating songs. 
 * It also tests the integration of the library with the MusicStore and ensures 
 * the proper functionality of methods for managing and interacting 
 * with a music library.
 */
package test;

import model.LibraryModel;
import model.MusicStore;
import model.Song;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryModelTest {

    private LibraryModel libraryModel;


    @Test
    public void testConstructor() {
        libraryModel = new LibraryModel();
        assertEquals(libraryModel.getLibrary().isEmpty(), true);
        assertEquals(libraryModel.getAllAlbums().isEmpty(), true);
        assertEquals(libraryModel.getAllArtists().isEmpty(), true);
        assertEquals(libraryModel.getFavorites().isEmpty(), true);
    }

    @Test
    public void testSearchSong() {
        MusicStore store = new MusicStore();
        libraryModel = new LibraryModel();
        libraryModel.addSongToLibrary(new Song("Daydreamer", "Adele", "21"));
        libraryModel.addSongToLibrary(new Song("Hometown Glory", "Adele", "12"));

        assertEquals(true, !libraryModel.searchSongByTitle("Daydreamer").isEmpty());
        assertEquals(true, libraryModel.searchSongByTitle("Daydramer").isEmpty());

        assertEquals(true, !libraryModel.searchSongByArtist("Adele").isEmpty());
        assertEquals(true, libraryModel.searchSongByArtist("Aele").isEmpty());

        assertEquals(true, !libraryModel.searchAlbumByTitle(store, "21").isEmpty());
        assertEquals(true, libraryModel.searchAlbumByTitle(store, "120").isEmpty());

        assertEquals(true, !libraryModel.searchAlbumByArtist(store, "Adele").isEmpty());
        assertEquals(true, libraryModel.searchAlbumByArtist(store, "aa").isEmpty());
    }

    @Test
    public void playListTest() {
        MusicStore store = new MusicStore();
        libraryModel = new LibraryModel();

        Song song1 = new Song("Daydreamer", "Adele", "21");
        Song song2 = new Song("Hometown Glory", "Adele", "12");
        libraryModel.addSongToLibrary(song1);
        libraryModel.addSongToLibrary(song2);

        assertEquals(true, libraryModel.getPlayLists().isEmpty());
        libraryModel.createPlayList("Lisa");
        assertEquals(true, libraryModel.getPlayLists().size() > 0);

        libraryModel.addSongToPlayList(song1, "Lisa");
        libraryModel.addSongToPlayList(song2, "Lisa");

        assertEquals(true, libraryModel.getFavorites().isEmpty());
        assertEquals(true, libraryModel.rateSong(song1, 5));
        assertEquals(true, !libraryModel.getFavorites().isEmpty());

        assertEquals(true, libraryModel.getLibrary().size() > 0);
        assertEquals(true, libraryModel.getAllAlbums().size() > 0);
        assertEquals(true, libraryModel.getAllSongs().size() > 0);
        assertEquals(true, libraryModel.getAllArtists().size() > 0);

    }
}
