/*
 * Author: Lanyue Zhang
 * The AlbumTest file tests the Album class. 
 * It ensures that the Album object is correctly initialized with a title, 
 * artist, genre, and year. 
 * The tests also verify that songs can be added to the album and 
 * that the size of the song list increases as expected when a song is added.
 * */
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
