package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class shows the basic info and operations for the Song
 */
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String artist;
    private String album;
    private String genre;
    private int rating;
    private boolean isFavorite;

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
        this.isFavorite = false;
        this.genre = ""; // Default empty genre
    }

    /**
     * Constructor with genre
     */
    public Song(String title, String artist, String album, String genre) {
        this(title, artist, album);
        this.genre = genre;
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
     * Get the song genre
     * @return song genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Set the song genre
     * @param genre The genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Get the song rating
     * @return song rating between 1 and 5
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set the rating of the song
     * @param rating input rating
     */
    public void setRating(int rating) {
        this.rating = rating;
        if (rating == 5) {
            this.isFavorite = true;
        }
    }

    /**
     * Check if the song is marked as favorite
     * @return true if favorite, false otherwise
     */
    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Mark song as favorite
     * @param favorite true to mark as favorite, false otherwise
     */
    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    /**
     * Check if two songs equal to each other
     * @param o another song object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(album, song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, album);
    }

    /**
     * Get the string of the song object
     * @return string of the song object
     */
    @Override
    public String toString() {
        String ratingStr = rating > 0 ? " ★" + rating : "";
        String favoriteStr = isFavorite ? " ♥" : "";
        return String.format("%-50s%-50s%-30s%s%s", title, artist, album, ratingStr, favoriteStr);
    }
}