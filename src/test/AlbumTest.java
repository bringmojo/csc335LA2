package test;

import model.Album;
import model.Song;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AlbumTest {

    @Test
    public void testAlbum() {
        Album album = new Album("19", "Adele", "Pop", 2015);
        assertEquals(album.getTitle(), "19");
        assertEquals(album.getArtist(), "Adele");
        assertEquals(album.getYear(), 2015);
        assertEquals(album.getGenre(), "Pop");
        assertEquals(album.getSongs().size(), 0);

        album.addSong(new Song("19", "Adele", "Pop"));
        assertEquals(album.getSongs().size(), 1);
    }
}
