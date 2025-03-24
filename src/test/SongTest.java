package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.Song;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SongTest {

    @Test
    public void testSongMethods() {
        // Test basic constructor
        Song song = new Song("Remember me", "Lisa", "Lily like it");
        assertEquals("Lisa", song.getArtist());
        assertEquals("Remember me", song.getTitle());
        assertEquals("Lily like it", song.getAlbum());
        assertEquals("", song.getGenre()); // Default genre should be an empty string
        assertEquals(0, song.getRating()); // Default rating should be 0
        assertFalse(song.isFavorite()); // Default favorite status should be false

        // Test constructor with genre
        Song songWithGenre = new Song("Remember me", "Lisa", "Lily like it", "Pop");
        assertEquals("Pop", songWithGenre.getGenre());

        // Test setGenre
        song.setGenre("Rock");
        assertEquals("Rock", song.getGenre());

        // Test setRating
        song.setRating(4);
        assertEquals(4, song.getRating());
        assertFalse(song.isFavorite()); // Rating 4 should NOT mark it as favorite

        song.setRating(5);
        assertEquals(5, song.getRating());
        assertTrue(song.isFavorite()); // Rating 5 should automatically mark it as favorite

        // Test setFavorite
        song.setFavorite(false);
        assertFalse(song.isFavorite());
        song.setFavorite(true);
        assertTrue(song.isFavorite());

        // Test equals method
        Song song1 = new Song("Remember me", "Lisa", "Lily like it 1");
        assertFalse(song1.equals(song)); // Different album, should return false

        Song song2 = new Song("Remember me", "Lisa", "Lily like it");
        assertTrue(song.equals(song2)); // Identical attributes, should return true

        // Test hashCode
        assertEquals(song.hashCode(), song2.hashCode()); // Hash codes should be equal for identical objects

        // Test toString
        String expectedString = String.format("%-50s%-50s%-30s%s%s", "Remember me", "Lisa", "Lily like it", " ★5", " ♥");
        assertEquals(expectedString, song.toString());
    }
}
