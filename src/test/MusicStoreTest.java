package test;

import model.MusicStore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


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
