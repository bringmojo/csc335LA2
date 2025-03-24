package test;


import model.Song;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SongTest {
    @Test
    public void testSongConstructor() {
        // test constructor and getters.
        Song song = new Song("Remember me", "Lisa", "Lily like it");
        assertEquals("Remember me", song.getTitle());
        assertEquals("Lisa", song.getArtist());
        assertEquals("Lily like it", song.getAlbum());
        assertEquals(0, song.getRating());
        assertEquals(0, song.getPlayCount());
    }

    @Test
    public void testSetRating() {
        //test set and get the rating.
        Song song = new Song("Remember me", "Lisa", "Lily like it");
        song.setRating(5);
        assertEquals(5, song.getRating());
    }

    @Test
    public void testSongMethods() {
    public void testIncrementPlayCount() {
        //test the play count increment
        Song song = new Song("Remember me", "Lisa", "Lily like it");
        assertEquals(song.getArtist(), "Lisa");
        assertEquals(song.getTitle(), "Remember me");
        assertEquals(song.getAlbum(), "Lily like it");
        Song song1 = new Song("Remember me", "Lisa", "Lily like it 1");
        assertEquals(false, song1.equals(song));
        assertEquals(0, song.getPlayCount());
        song.incrementPlayCount();
        assertEquals(1, song.getPlayCount());//after increment, should be 1
    }

    @Test
    public void testEqualsMethod() {   
        //test eauals
        Song first = new Song("Remember me", "Lisa", "Lily like it");
        Song sed = new Song("Second Song", "me", "I like it");
        Song third = new Song("Remember me", "Lisa", "Lily like it");
        assertTrue(first.equals(third));
        assertFalse(first.equals(sed)); 
        assertFalse(first.equals(null));
        assertFalse(first.equals(new Object()));
    }

    @Test
    public void testToStringMethod() {   
        //test toString
        Song first = new Song("Remember me", "Lisa", "Lily like it");
        String message = String.format("%-50s%-50s%s", "Remember me", "Lisa", "Lily like it");
        assertEquals(message, first.toString());
    }

    @Test
    public void testSongWithDifferentAlbums() {   
        //test equality with different album names
        Song first = new Song("Remember me", "Lisa", "Lily like it");
        Song sed = new Song("Remember me", "Lisa", "Lily like it ?");
        assertFalse(first.equals(sed));
    }
}
