
/*
 * Author: Lanyue Zhang
 * The SongTest file is dedicated to testing the Song class. 
 * It verifies that the Songobject correctly stores the artist, title, and album information.
 */
package test;
import model.Song;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SongTest {

    @Test
    public void testSongMethods() {
        Song song = new Song("Remember me", "Lisa", "Lily like it");
        assertEquals(song.getArtist(), "Lisa");
        assertEquals(song.getTitle(), "Remember me");
        assertEquals(song.getAlbum(), "Lily like it");
        Song song1 = new Song("Remember me", "Lisa", "Lily like it 1");
        assertEquals(false, song1.equals(song));
    }
}
