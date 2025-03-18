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
    
    //Store the last 10 songs played
    private List<Song>recentPlayed = new LinkedList<>();
    //store play count
    private Map<Song, Integer> playCounts = new HashMap<>();
    private static final int recentPlayLimit = 10;
    
    /**
     * constructor
     */
    public LibraryModel() {
        playLists = new ArrayList<PlayList>();
        favorites = new ArrayList<>();
        library = new ArrayList<>();
    }

    /*
     *get the 10 most recently played songs
     * @return list of recentPlayed
     */
    public List<Song>getRecentPayedSongs(){
        return new ArrayList<>(recentPlayed);
    }

    /*
    * get the top 10 most played songs
    * @return list of 10 songs
    */
    public List<Song> getTopPlayedSongs() {
        //a list to store the sorted songs
        List<Map.Entry<Song, Integer>> sortedList = new ArrayList<>(playCounts.entrySet());
        //decending sort
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        List<Song> topPlayedSongs = new ArrayList<>();
        //find the top 10 most played songs
        for (int i = 0; i < Math.min(10, sortedList.size()); i++) {
            topPlayedSongs.add(sortedList.get(i).getKey());
        }
        return topPlayedSongs;
    }

    @Override
    //Implement random play
    public Iterator<Song> iterator() {
        List<Song> newList = new ArrayList<>(library);
        Collections.newList(newList);
        return newList.iterator;
    }

   /*
    * play the specified song and update the playback data.
    * @param title
    * @param artist
    */
    public void playSong(String title, String artist){
        for (Song song:library){  
            if (song.getTitle().equals(title)&&song.getArtist().equals(artist)){      
                song.incrementPlayCount();
                playCounts.put(song, song.getPlayCount());
                //update recent list
                recentPlayed.remove(song);
                recentPlayed.add(0, song);
                if (recentPlayed.size() > recentPlayLimit) {
                    recentPlayed.remove(recentPlayLimit);
                }
            return;
            }
        }
        System.out.println("Song not found");
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

    
}
