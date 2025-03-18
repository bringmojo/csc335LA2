/*
 * Author: Lanyue Zhang
 * This class shows the basic info and opertaions for the Song
 */
package model;

import java.util.Objects;


public class Song {

    private String title;
    private String artist;
    private String album;
    private int rating;
    private int playCount;

    /**
     * Default constructor
     * @param title input title
     * @param artist input artist
     * @param album input album
     */
    public Song(String title, String artist, String album) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.rating = 0;
        this.playCount = 0;
    }

    /**
     * Get the song title
     * @return song title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the song artist
     * @return
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Get the song album
     * @return song album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Get the song rating
     * @return song rating between 1 and 5
     */
    public int getRating() {
        return rating;
    }
    
    /**
     * Get the playCount
     * @return playCount
     */
    public int getPlayCount() {
        return playCount;
    }

    /**
     * Increase play times
     */
    public void incrementPlayCount() {
        this.playCount++;
    }

    /**
     * Set the rating of the song
     * @param rating input rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Check if two songs equal to each other
     * @param o another song object
     * @return true if equal, else false
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(album, song.album);
    }

    /**
     * Get the string of the song object
     * @return string of the song object
     */
    public String toString() {
        return String.format("%-50s%-50s%s", title, artist, album);
    }
}
