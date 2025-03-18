/**
 * Author: Lanyue Zhang
 * This class represents an album with the detailed information of the album and
 * corresponding operations
 * 1sttry to use
 */
package model;

import java.util.ArrayList;
import java.util.List;
2

public class Album {

    private String title;
    private String artist;
    private String genre;
    private int year;
    private List<Song> songs;

    /**
     * This method returns the title of the album
     * @param title title of the album
     * @param artist artist of the album
     * @param genre genre of the album
     * @param year year of the album
     */
    public Album(String title, String artist, String genre, int year) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.songs = new ArrayList<>();
    }

    /**
     * This method returns the title of the album
     * @return title of the album
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method returns the artist of the album
     * @return artist of the album
     */
    public String getArtist() {
        return artist;
    }

    /**
     * This method returns the genre of the album
     * @return genre of the album 
     */
    public String getGenre() {
        return genre;
    }

    /**
     * This method returns the year of the album
     * @return year of the album
     */
    public int getYear() {
        return year;
    }

    /**
     * This method returns the list of songs on the album
     * @return list of songs on the album
     */
    public List<Song> getSongs() {
        return this.songs;
    }

    /**
     * Add the song to the album
     * @param song new song object
     */
    public void addSong(Song song) {
        this.songs.add(song);
    }

    /**
     * Get the string representation of the album
     * @return string of the album object
     */
    public String toString() {
        String res = String.format("%-50s%-30s%-30s%s\n", "Album Title", "Album Artist",
                "Album Genre", "Album publish year");
        res += String.format("%-50s%-30s%-30s%d\n", title, artist, genre, year);
        res += "\nSongs:\n";
        res += String.format("%-50s%-50s%s\n", "Song title", "Song artist", "Song Album");
        for (int i = 0; i < songs.size(); i++) {
            res += songs.get(i).toString() + "\n";
        }
        return res;
    }
}
