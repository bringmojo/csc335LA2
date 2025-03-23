package test;

import model.PlayList;
import model.Song;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayListTest {

    @Test
    public void testPlayList() {
        PlayList playList = new PlayList("Lisa");
        assertEquals(playList.getListName(), "Lisa");
        assertEquals(playList.getSongs().size(), 0);

        playList.addSong(new Song("Remember me", "Adele", "Pop"));
        assertEquals(playList.getSongs().size(), 1);
        playList.addSong(new Song("Remember me", "Adele", "Pop"));
        assertEquals(playList.getSongs().size(), 1);

        playList.addSong(new Song("Joking", "Adele", "Pop"));
        assertEquals(playList.getSongs().size(), 2);

        playList.removeSong("Joking", "Adele");
        assertEquals(playList.getSongs().size(), 1);

        assertEquals(true, playList.containsSong("Remember me", "Adele"));

    }
}
