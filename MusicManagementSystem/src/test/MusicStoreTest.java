
/*
 * Author: Jifei Wang
 * This test file is designed to verify the functionality of the MusicStore class. 
 * It checks if the data is properly loaded from the store, ensuring that 
 * albums and songs are not empty. 
 * Additionally, it tests the search functionality for songs by 
 * title, artist, and albums by title and artist, 
 * ensuring that the MusicStore correctly handles these queries.
 */
package test;
import model.MusicStore;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MusicStoreTest {
    private MusicStore store;

    @Test
    public void testDataLoaded() {
        store = new MusicStore();
        assertEquals(true, !store.getAlbums().isEmpty());
        assertEquals(true, !store.getAllSongs().isEmpty());
    }

    @Test
    public void testFindSongAndAlbum() {
        store = new MusicStore();
        assertEquals(true, !store.searchSongByTitle("Daydreamer").isEmpty());
        assertEquals(true, !store.searchSongByArtist("Adele").isEmpty());
        assertEquals(true, !store.searchAlbumByTitle("21").isEmpty());
        assertEquals(true, !store.searchAlbumByArtist("Adele").isEmpty());
    }

}
