/**
 * Author: Jifei Wang, Lanyue Zhang(LA2 adds some code)
 * This is the class shows the user library
 * which contains multiple playlist and corresponding operations
 * on the library
 */
package model;

import java.util.ArrayList;
import java.util.List;


public class LibraryModel {
    // list of play list object
    private List<PlayList> playLists;
    // list of favorite songs
    private List<Song> favorites;
    private List<Song> library;

    /**
     * Default constructor
     */
    public LibraryModel() {
        playLists = new ArrayList<PlayList>();
        favorites = new ArrayList<>();
        library = new ArrayList<>();
    }

    /**
     * Get all playlist of the library
     * @return list of playlist
     */
    public List<PlayList> getPlayLists() {
        return playLists;
    }

    /**
     * Get list of favorite songs
     * @return list of favorite songs
     */
    public List<Song> getFavorites() {
        return favorites;
    }

    public List<Song> getLibrary() {
        return library;
    }

    /**
     * Search specified songs by song title
     * @param title input song title
     * @return list of song objects
     */
    public List<Song> searchSongByTitle(String title) {
        List<Song> songs = new ArrayList<>();
        for (Song song : library) {
            if (song.getTitle().equals(title)) {
                songs.add(song);
            }
        }
        return songs;
    }

    /**
     * Search specified songs by song artist
     * @param artist input song artist
     * @return list of song objects
     */
    public List<Song> searchSongByArtist(String artist) {
        List<Song> songs = new ArrayList<>();
        for (Song song : library) {
            if (song.getArtist().equals(artist)) {
                songs.add(song);
            }
        }
        return songs;
    }

    /**
     * Search specified albums by song title
     * @param musicStore input music store object
     * @param title input song title
     * @return list of song objects
     */
    public List<Album> searchAlbumByTitle(MusicStore musicStore, String title) {
        List<Album> albums = new ArrayList<>();
        boolean found = false;
        for (Song song : library) {
            if (song.getAlbum().equals(title)) {
                found = true;
            }
        }
        if (found) {
            return musicStore.searchAlbumByTitle(title);
        }
        return albums;
    }

    /**
     * Search specified albums by song title
     * @param musicStore input music store object
     * @param artist input song title
     * @return list of song objects
     */
    public List<Album> searchAlbumByArtist(MusicStore musicStore, String artist) {
        List<Album> albums = new ArrayList<>();
        boolean found = false;
        for (Song song : library) {
            if (song.getArtist().equals(artist)) {
                found = true;
            }
        }
        if (found) {
            return musicStore.searchAlbumByArtist(artist);
        }
        return albums;
    }

    /**
     * Search playlist by name
     * @param title input playlist name
     * @return searched playlist object if found, else null
     */
    public PlayList searchPlayListByTitle(String title) {
        for (PlayList playList : playLists) {
            if (playList.getListName().equals(title)) {
                return playList;
            }
        }
        return null;
    }

    /**
     * Add a new play list to the library
     * @param name input play list name
     * @return true if add successfully, else false
     */
    public boolean createPlayList(String name) {
        for (PlayList playList : playLists) {
            if (playList.getListName().equals(name)) {
                return false;
            }
        }
        playLists.add(new PlayList(name));
        return true;
    }

    /**
     * Add song to the play list
     * @param song input song object
     * @param playlistName play list name
     * @return true if add successfully, else false
     */
    public boolean addSongToPlayList(Song song, String playlistName) {
        PlayList playList = searchPlayListByTitle(playlistName);
        if (playList == null) {
            return false;
        }
        if (playList.containsSong(song.getTitle(), song.getArtist())) {
            return false;
        }
        playList.addSong(song);
        if (!library.contains(song)) {
            library.add(song);
        }
        return true;
    }

    /**
     * Remove the specified song from the play list
     * @param songName input song name
     * @param artist input song artist
     * @param playlistName input play list name
     * @return true if removed successfully, else false
     */
    public boolean removeSongFromPlayList(String songName, String artist, String playlistName) {
        PlayList playList = searchPlayListByTitle(playlistName);
        if (playList == null) {
            return false;
        }
        if (!playList.containsSong(songName, artist)) {
            return false;
        }
        playList.removeSong(songName, artist);
        int idx = -1;
        for (int i = 0; i < library.size(); i++) {
            Song song = library.get(i);
            if (song.getTitle().equals(songName) && song.getArtist().equals(artist)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            library.remove(idx);
        }
        return true;
    }

    /**
     * Add song to the library
     * @param song input song object
     * @return true if add successfully, else false
     */
    public boolean addSongToLibrary(Song song) {
        if (library.contains(song)) {
            return false;
        }
        library.add(song);
        return true;
    }

    /**
     * Add album songs to the library
     * @param album input album object
     */
    public void addAlbumSongsToLibrary(Album album) {
        for (Song song : album.getSongs()) {
            addSongToLibrary(song);
        }
    }

    /**
     * Get all songs title from the library
     * @return all song title
     */
    public List<String> getAllSongs() {
        List<String> titles = new ArrayList<>();
        for (Song song : library) {
            titles.add(song.getTitle());
        }
        return titles;
    }

    /**
     * Get all songs artists from the library
     * @return all song artists
     */
    public List<String> getAllArtists() {
        List<String> artists = new ArrayList<>();
        for (Song song : library) {
            if (!artists.contains(song.getArtist())) {
                artists.add(song.getArtist());
            }
        }
        return artists;
    }

    /**
     * Get all songs albums from the library
     * @return all song albums
     */
    public List<String> getAllAlbums() {
        List<String> albums = new ArrayList<>();
        for (Song song : library) {
            if (!albums.contains(song.getAlbum())) {
                albums.add(song.getAlbum());
            }
        }
        return albums;
    }

    /**
     * Mark song as favorite
     * @param song input song object
     * @return true if marked successfully, else false
     */
    public boolean markSongAsFavorite(Song song) {
        if (!favorites.contains(song)) {
            favorites.add(song);
            if (!library.contains(song)) {
                library.add(song);
            }
            return true;
        }
        return false;
    }


    /**
     * Rate song with specified score
     * @param song input song
     * @param score input score
     * @return true if rated successfully, else false
     */
    public boolean rateSong(Song song, int score) {
        if (!library.contains(song)) {
            return false;
        }
        song.setRating(score);
        if (score == 5 && !favorites.contains(song)) {
            favorites.add(song);
        }
        return true;
    }
    
    /*
     * Play the song and update the number of plays
     * @param song 
     */
    public void playSong(Song song) {
        song.incrementPlayCount();
    }
    
}
