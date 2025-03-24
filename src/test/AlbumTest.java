package test;

import model.Album;
import model.Song;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.List;


public class AlbumTest {

    @Test
    public void testAlbum() {
        // Test album constructor
        Album album = new Album("19", "Adele", "Pop", 2015);
        assertEquals("19", album.getTitle());
        assertEquals("Adele", album.getArtist());
        assertEquals(2015, album.getYear());
        assertEquals("Pop", album.getGenre());
        assertEquals(0, album.getSongs().size()); // Initially, no songs in album

        // Test adding a song
        Song song1 = new Song("Hello", "Adele", "19");
        album.addSong(song1);
        assertEquals(1, album.getSongs().size());
        assertEquals("Hello", album.getSongs().get(0).getTitle());

        // Test adding multiple songs
        Song song2 = new Song("Someone Like You", "Adele", "19");
        album.addSong(song2);
        assertEquals(2, album.getSongs().size());

        // Test getSongs returns a correct list
        List<Song> songs = album.getSongs();
        assertEquals(2, songs.size());
        assertEquals("Hello", songs.get(0).getTitle());
        assertEquals("Someone Like You", songs.get(1).getTitle());

        // Test toString method
        String expectedString = String.format(
            "%-50s%-30s%-30s%s\n", "Album Title", "Album Artist", "Album Genre", "Album publish year") +
            String.format("%-50s%-30s%-30s%d\n", "19", "Adele", "Pop", 2015) +
            "\nSongs:\n" +
            String.format("%-50s%-50s%s\n", "Song title", "Song artist", "Song Album") +
            song1.toString() + "\n" + song2.toString() + "\n";
        assertEquals(expectedString, album.toString());

        // Test copy method
        Album albumCopy = album.copy();
        assertEquals(album.getTitle(), albumCopy.getTitle());
        assertEquals(album.getArtist(), albumCopy.getArtist());
        assertEquals(album.getYear(), albumCopy.getYear());
        assertEquals(album.getGenre(), albumCopy.getGenre());
        assertEquals(album.getSongs().size(), albumCopy.getSongs().size());
        assertNotSame(album.getSongs(), albumCopy.getSongs()); // Ensure it's a deep copy

        // Ensure song objects in the copy are distinct from the original
        for (int i = 0; i < album.getSongs().size(); i++) {
            assertNotSame(album.getSongs().get(i), albumCopy.getSongs().get(i));
        }
    }
}

