package test;
import model.LibraryModel;
import model.MusicStore;
import model.Song;
import model.PlayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class LibraryModelTest {
    private LibraryModel libraryModel;
    private Song song1, song2, song3;
    private MusicStore store;

    @Before
    public void setUp() {
        libraryModel = new LibraryModel();
        store = new MusicStore();
        song1 = new Song("Daydreamer", "Adele", "21");
        song2 = new Song("Hometown Glory", "Adele", "12");
        song3 = new Song("Someone Like You", "Adele", "21");

        libraryModel.addSongToLibrary(song1);
        libraryModel.addSongToLibrary(song2);
        libraryModel.addSongToLibrary(song3);
    }

    @Test
    public void testPlaySongAndRecentlyPlayed() {
        libraryModel.playSong(song1);
        libraryModel.playSong(song2);
        libraryModel.playSong(song1); 

        assertEquals(2, libraryModel.getPlayCount(song1));
        assertEquals(1, libraryModel.getPlayCount(song2));

        List<Song> recent = libraryModel.getRecentlyPlayed();
        assertEquals(2, recent.size());
        assertEquals(song1, recent.get(0));
    }

    @Test
    public void testMostPlayedSongs() {
        libraryModel.playSong(song1);
        libraryModel.playSong(song1);
        libraryModel.playSong(song2);

        List<Song> mostPlayed = libraryModel.getMostPlayed();
        assertEquals(2, mostPlayed.size());
        assertEquals(song1, mostPlayed.get(0));
    }

    @Test
    public void testRemoveSongFromLibrary() {
        assertTrue(libraryModel.removeSongFromLibrary(song1));
        assertFalse(libraryModel.getLibrary().contains(song1));

        Song song4 = new Song("Rolling in the Deep", "Adele", "21");
        assertFalse(libraryModel.removeSongFromLibrary(song4));
    }

    @Test
    public void testRemoveAlbumFromLibrary() {
        int removedCount = libraryModel.removeAlbumFromLibrary("21", "Adele");
        assertEquals(2, removedCount); 
        assertFalse(libraryModel.getLibrary().contains(song1));
        assertFalse(libraryModel.getLibrary().contains(song3));
    }

    @Test
    public void testMarkSongAsFavorite() {
        assertTrue(libraryModel.markSongAsFavorite(song1));
        assertFalse(libraryModel.markSongAsFavorite(song1));

        assertTrue(libraryModel.getFavorites().contains(song1));
    }

    @Test
    public void testSortingFunctions() {
        List<Song> sortedByTitle = libraryModel.getSortedSongs("title");
        assertEquals("Daydreamer", sortedByTitle.get(0).getTitle());

        List<Song> sortedByArtist = libraryModel.getSortedSongs("artist");
        assertEquals("Adele", sortedByArtist.get(0).getArtist());

        List<Song> sortedByRating = libraryModel.getSortedSongs("rating");
        assertEquals(3, sortedByRating.size());
    }

    @Test
    public void testRandomPlaylistIterator() {
        LibraryModel.RandomPlaylistIterator iterator = libraryModel.createRandomPlaylist();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void testPlayListManagement() {
        libraryModel.createPlayList("My Playlist");
        assertNotNull(libraryModel.searchPlayListByTitle("My Playlist"));

        assertTrue(libraryModel.addSongToPlayList(song1, "My Playlist"));
        assertFalse(libraryModel.addSongToPlayList(song1, "My Playlist"));

        assertTrue(libraryModel.removeSongFromPlayList("Daydreamer", "Adele", "My Playlist"));
    }

    @Test
    public void testAutoPlaylists() {
        libraryModel.playSong(song1);
        libraryModel.playSong(song1);
        libraryModel.playSong(song2);

        PlayList mostPlayed = libraryModel.searchPlayListByTitle("Most Played");
        assertNotNull(mostPlayed);
        assertEquals(2, mostPlayed.getSongs().size());

        PlayList recentlyPlayed = libraryModel.searchPlayListByTitle("Recently Played");
        assertNotNull(recentlyPlayed);
        assertEquals(2, recentlyPlayed.getSongs().size());

        PlayList favorites = libraryModel.searchPlayListByTitle("Favorites");
        assertNotNull(favorites);
    }

    @Test
    public void testRemoveSongNotInLibrary() {
        Song fakeSong = new Song("Fake Song", "Unknown Artist", "Unknown Album");
        assertFalse(libraryModel.removeSongFromLibrary(fakeSong));
    }

}
