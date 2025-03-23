package model;

import java.io.Serializable;
import java.util.*;

/**
 * This is the class shows the user library
 * which contains multiple playlist and corresponding operations
 * on the library
 */
public class LibraryModel implements Serializable {
    // Add serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;

    // list of play list object
    private List<PlayList> playLists;
    // list of favorite songs
    private List<Song> favorites;
    private List<Song> library;
    // Map to track play counts for songs
    private Map<Song, Integer> playCount;
    // List to track recently played songs
    private LinkedList<Song> recentlyPlayed;

    /**
     * Default constructor
     */
    public LibraryModel() {
        playLists = new ArrayList<PlayList>();
        favorites = new ArrayList<>();
        library = new ArrayList<>();
        playCount = new HashMap<>();
        recentlyPlayed = new LinkedList<>();

        // Create auto-generated playlists
        createPlayList("Most Played");
        createPlayList("Recently Played");
        createPlayList("Favorites");
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

    /**
     * Play a song, incrementing its play count and updating recently played
     * @param song The song to play
     */
    public void playSong(Song song) {
        if (library.contains(song)) {
            // Increment play count
            int count = playCount.getOrDefault(song, 0) + 1;
            playCount.put(song, count);

            // Update recently played
            if (recentlyPlayed.contains(song)) {
                recentlyPlayed.remove(song);
            }
            recentlyPlayed.addFirst(song);

            // Limit recently played to 10 songs
            while (recentlyPlayed.size() > 10) {
                recentlyPlayed.removeLast();
            }

            // Update auto playlists
            updateAutoPlaylists();
        }
    }

    /**
     * Get the recently played songs (max 10)
     * @return List of recently played songs
     */
    public List<Song> getRecentlyPlayed() {
        return new ArrayList<>(recentlyPlayed);
    }

    /**
     * Get the most played songs (max 10)
     * @return List of most played songs sorted by play count
     */
    public List<Song> getMostPlayed() {
        List<Song> sortedByPlayCount = new ArrayList<>(playCount.keySet());
        sortedByPlayCount.sort((s1, s2) ->
                Integer.compare(playCount.getOrDefault(s2, 0),
                        playCount.getOrDefault(s1, 0)));

        return sortedByPlayCount.size() <= 10 ?
                sortedByPlayCount : sortedByPlayCount.subList(0, 10);
    }

    /**
     * Get play count for a specific song
     * @param song The song
     * @return The play count
     */
    public int getPlayCount(Song song) {
        return playCount.getOrDefault(song, 0);
    }

    /**
     * Update auto-generated playlists
     */
    private void updateAutoPlaylists() {
        // Update Most Played playlist
        PlayList mostPlayedList = searchPlayListByTitle("Most Played");
        if (mostPlayedList != null) {
            // Clear and repopulate the playlist
            mostPlayedList.getSongs().clear();
            for (Song song : getMostPlayed()) {
                mostPlayedList.addSong(song);
            }
        }

        // Update Recently Played playlist
        PlayList recentlyPlayedList = searchPlayListByTitle("Recently Played");
        if (recentlyPlayedList != null) {
            recentlyPlayedList.getSongs().clear();
            for (Song song : recentlyPlayed) {
                recentlyPlayedList.addSong(song);
            }
        }

        // Update Favorites playlist
        PlayList favoritesPlaylist = searchPlayListByTitle("Favorites");
        if (favoritesPlaylist != null) {
            favoritesPlaylist.getSongs().clear();
            for (Song song : favorites) {
                favoritesPlaylist.addSong(song);
            }
        }

        // Create genre-based playlists
        updateGenrePlaylists();
    }

    /**
     * Update genre-based playlists
     */
    private void updateGenrePlaylists() {
        // Count songs by genre
        Map<String, List<Song>> songsByGenre = new HashMap<>();

        for (Song song : library) {
            String genre = song.getGenre();
            if (genre != null && !genre.isEmpty()) {
                if (!songsByGenre.containsKey(genre)) {
                    songsByGenre.put(genre, new ArrayList<>());
                }
                songsByGenre.get(genre).add(song);
            }
        }

        // Create or update playlists for genres with at least 10 songs
        for (Map.Entry<String, List<Song>> entry : songsByGenre.entrySet()) {
            String genre = entry.getKey();
            List<Song> songs = entry.getValue();

            if (songs.size() >= 10) {
                String playlistName = genre + " Mix";
                PlayList genrePlaylist = searchPlayListByTitle(playlistName);

                if (genrePlaylist == null) {
                    createPlayList(playlistName);
                    genrePlaylist = searchPlayListByTitle(playlistName);
                }

                if (genrePlaylist != null) {
                    genrePlaylist.getSongs().clear();
                    for (Song song : songs) {
                        genrePlaylist.addSong(song);
                    }
                }
            }
        }
    }

    /**
     * Get songs sorted by specified criteria
     * @param sortBy "title", "artist", or "rating"
     * @return Sorted list of songs
     */
    public List<Song> getSortedSongs(String sortBy) {
        List<Song> sortedSongs = new ArrayList<>(library);

        switch (sortBy.toLowerCase()) {
            case "title":
                sortedSongs.sort(Comparator.comparing(Song::getTitle));
                break;
            case "artist":
                sortedSongs.sort(Comparator.comparing(Song::getArtist));
                break;
            case "rating":
                sortedSongs.sort(Comparator.comparing(Song::getRating).reversed());
                break;
            default:
                // No sorting
                break;
        }

        return sortedSongs;
    }

    /**
     * Remove a song from the library
     * @param song The song to remove
     * @return true if removed, false otherwise
     */
    public boolean removeSongFromLibrary(Song song) {
        if (library.remove(song)) {
            // Also remove from favorites
            favorites.remove(song);
            // Remove from play counts
            playCount.remove(song);
            // Remove from recently played
            recentlyPlayed.remove(song);
            // Remove from all playlists
            for (PlayList playlist : playLists) {
                playlist.getSongs().remove(song);
            }
            return true;
        }
        return false;
    }

    /**
     * Remove an album from library
     * @param albumTitle The album title
     * @param artist The album artist
     * @return Number of songs removed
     */
    public int removeAlbumFromLibrary(String albumTitle, String artist) {
        List<Song> songsToRemove = new ArrayList<>();

        for (Song song : library) {
            if (song.getAlbum().equals(albumTitle) && song.getArtist().equals(artist)) {
                songsToRemove.add(song);
            }
        }

        for (Song song : songsToRemove) {
            removeSongFromLibrary(song);
        }

        return songsToRemove.size();
    }

    /**
     * Search songs by genre
     * @param genre The genre to search for
     * @return List of matching songs
     */
    public List<Song> searchSongsByGenre(String genre) {
        List<Song> result = new ArrayList<>();
        for (Song song : library) {
            if (song.getGenre() != null && song.getGenre().equalsIgnoreCase(genre)) {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * Create a RandomPlaylist iterator for shuffle play
     * @return A RandomPlaylistIterator
     */
    public RandomPlaylistIterator createRandomPlaylist() {
        return new RandomPlaylistIterator(library);
    }

    /**
     * Iterator implementation for random playback
     */
    public class RandomPlaylistIterator implements Iterable<Song>, Iterator<Song> {
        private List<Song> songs;
        private int currentIndex = 0;

        public RandomPlaylistIterator(List<Song> originalSongs) {
            // Create a copy of the list
            this.songs = new ArrayList<>(originalSongs);
            // Shuffle the list
            Collections.shuffle(this.songs);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < songs.size();
        }

        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return songs.get(currentIndex++);
        }

        @Override
        public Iterator<Song> iterator() {
            return this;
        }
    }
}
