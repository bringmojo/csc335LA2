package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Author: Jifei Wang
 * This class would load the song data from the specified file
 */
public class MusicStore {

    private List<Album> albums;

    /**
     * Default constructor
     */
    public MusicStore() {
        albums = new ArrayList<>();
        loadAlbum();
    }

    private Album readAlbum(String filename) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            // split the header line of the album file
            String[] items = line.split(",");
            String albumTitle = items[0];
            String artist = items[1];
            String genre = items[2];
            Album album = new Album(albumTitle, artist, genre, Integer.parseInt(items[3]));
            // get all lines of songs
            while ((line = br.readLine()) != null) {
                album.addSong(new Song(line.trim(), artist, albumTitle));
            }
            return album;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load the album from the specified file
     * @return list of album object
     */
    public void loadAlbum() {
        String filename = "albums/albums.txt";
        try {
            // read the data
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] items = line.split(",");
                String albumFilename = "albums/" + items[0] + "_" + items[1] + ".txt";
                albums.add(readAlbum(albumFilename));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public static void main(String[] args) {
        model.MusicStore store = new model.MusicStore();
        List<Album> albums = store.loadAlbum();
        for (Album album : albums) {
            System.out.println(String.format("%-20s%-20s%-20s%d", album.getTitle(),
                    album.getArtist(), album.getGenre(), album.getYear()));
            for (Song song : album.getSongs()) {
                System.out.println(String.format("%-60s%-20s%-20s",
                        song.getTitle(), song.getArtist(), song.getAlbum()));
            }
            System.out.println();
        }
    }

     */

    /**
     * Search specified songs by song title
     * @param title input song title
     * @return list of song objects
     */
    public List<Song> searchSongByTitle(String title) {
        List<Song> songs = new ArrayList<>();
        for (Album album : albums) {
            for (Song song : album.getSongs()) {
                if (song.getTitle().equals(title)) {
                    songs.add(song);
                }
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
        for (Album album : albums) {
            for (Song song : album.getSongs()) {
                if (song.getArtist().equals(artist)) {
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    /**
     * Search albums by album title
     * @param title input album title
     * @return list of album objects
     */
    public List<Album> searchAlbumByTitle(String title) {
        List<Album> res = new ArrayList<>();
        for (Album album : albums) {
            if (album.getTitle().equals(title)) {
                res.add(album);
            }
        }
        return res;
    }

    /**
     * Search albums by album artist
     * @param artist input album artist
     * @return list of album objects
     */
    public List<Album> searchAlbumByArtist(String artist) {
        List<Album> res = new ArrayList<>();
        for (Album album : albums) {
            if (album.getArtist().equals(artist)) {
                res.add(album);
            }
        }
        return res;
    }

    /**
     * Get all songs from the music store
     * @return all songs in music store
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        for (Album album : albums) {
            songs.addAll(album.getSongs());
        }
        return songs;
    }

    /**
     * Get all albums
     * @return album list
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Get complete album information for a song
     * @param song The song to get album info for
     * @return The album containing the song, or null if not found
     */
    public Album getAlbumForSong(Song song) {
        for (Album album : albums) {
            if (album.getTitle().equals(song.getAlbum()) &&
                    album.getArtist().equals(song.getArtist())) {
                return album;
            }
        }

        // Try searching by song title and artist
        for (Album album : albums) {
            for (Song albumSong : album.getSongs()) {
                if (albumSong.getTitle().equals(song.getTitle()) &&
                        albumSong.getArtist().equals(song.getArtist())) {
                    return album;
                }
            }
        }

        return null;
    }

    /**
     * Search songs by genre
     * @param genre Genre to search for
     * @return List of songs matching the genre
     */
    public List<Song> searchSongsByGenre(String genre) {
        List<Song> result = new ArrayList<>();
        for (Album album : albums) {
            if (album.getGenre().equalsIgnoreCase(genre)) {
                result.addAll(album.getSongs());
            }
        }
        return result;
    }

    /**
     * Get all available genres
     * @return List of unique genres
     */
    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        for (Album album : albums) {
            String genre = album.getGenre();
            if (!genres.contains(genre)) {
                genres.add(genre);
            }
        }
        return genres;
    }

    /**
     * Add genre information to a song
     * @param song The song to add genre info to
     */
    public void addGenreInfoToSong(Song song) {
        Album album = getAlbumForSong(song);
        if (album != null) {
            song.setGenre(album.getGenre());
        }
    }
}
