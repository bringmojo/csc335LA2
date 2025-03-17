/*
 * Author: Jifei Wang, Lanyue Zhang(part)
 * This class would load the song data from the specified file
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

}
