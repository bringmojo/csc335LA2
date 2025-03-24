package test;

import model.Album;
import model.MusicStore;
import model.Song;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import java.util.List;
import static org.junit.Assert.*;

public class MusicStoreTest {
    private MusicStore store;

    @Before
    public void setUp() {
        store = new MusicStore();
    }

    @Test
    public void testDataLoaded() {
        assertFalse(store.getAlbums().isEmpty());
        assertFalse(store.getAllSongs().isEmpty());
    }

    @Test
    public void testFindSongAndAlbum() {
        assertFalse(store.searchSongByTitle("Daydreamer").isEmpty());
        assertFalse(store.searchSongByArtist("Adele").isEmpty());
        assertFalse(store.searchAlbumByTitle("21").isEmpty());
        assertFalse(store.searchAlbumByArtist("Adele").isEmpty());
    }

    @Test
    public void testSearchSongsByGenre() {
        List<Song> rockSongs = store.searchSongsByGenre("Rock");
        assertNotNull(rockSongs);
        assertFalse(rockSongs.isEmpty());
        
    }

    @Test
    public void testGetAllGenres() {
        List<String> genres = store.getAllGenres();
        assertNotNull(genres);
        assertFalse(genres.isEmpty());
        assertTrue(genres.contains("Pop"));
        assertTrue(genres.contains("Rock"));
    }

    @Test
    public void testGetAlbumForSong() {
        Song testSong = new Song("Rolling in the Deep", "Adele", "21");
        Album album = store.getAlbumForSong(testSong);
        assertNotNull(album);
        assertEquals("21", album.getTitle());
        assertEquals("Adele", album.getArtist());
    }

    @Test
    public void testAddGenreInfoToSong() {
        Song song = new Song("Rolling in the Deep", "Adele", "21");
        store.addGenreInfoToSong(song);
        assertEquals("Pop", song.getGenre());
    }
}


