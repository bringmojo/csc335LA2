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

    /*
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

    /*
     * get the song title
     */
    public String getTitle() {
        return title;
    }

    /*
     * get the song artist
     */
    public String getArtist() {
        return artist;
    }

    /*
     *get the song album
     */
    public String getAlbum() {
        return album;
    }

    /*
     *get the song rating
     */
    public int getRating() {
        return rating;
    }
    
    /*
     * get the playCount
     */
    public int getPlayCount() {
        return playCount;
    }

    /*
     * increase play times
     */
    public void incrementPlayCount() {
        this.playCount++;
    }

    /*
     * set the rating of the song
     * @param rating input rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /*
     * check if two songs equal to each other
     * @param o
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

    /*
     * get the string of the song object
     */
    public String toString() {
        return String.format("%-50s%-50s%s", title, artist, album);
    }
}
