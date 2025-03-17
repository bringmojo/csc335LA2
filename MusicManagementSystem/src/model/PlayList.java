/*
 * Author: Lanyue Zhang
 * This class represents a playlist with the 
 * detailed information of the playlist and operations 
 * on the playlist
 */
package model;

import java.util.ArrayList;
import java.util.List;


public class PlayList {

    private String listName;
    private List<Song> songs;

    /**
     * Constructor to create a playlist
     * @param listName input list name
     */
    public PlayList(String listName) {
        this.listName = listName;
        this.songs = new ArrayList<>();
    }

    /**
     * Get the play list name
     * @return name of the playlist
     */
    public String getListName() {
        return listName;
    }

    /**
     * Get the song list of the play list
     * @return song list
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Check if the play list contains specified song
     * @param songName input song name
     * @param artist input artist
     * @return true if exists, else false
     */
    public boolean containsSong(String songName, String artist) {
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            if (song.getTitle().equals(songName) && song.getArtist().equals(artist)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add song to the play list
     * @param song input song object
     * @return true if add successfully, else false
     */
    public boolean addSong(Song song) {
        for (int i = 0; i < this.songs.size(); i++) {
            if (songs.get(i).equals(song)) {
                return false;
            }
        }
        this.songs.add(song);
        return true;
    }

    /**
     * Remove the song from the play list
     * @param songName input song name
     * @param artist input artist
     * @return true if remove successfully, else return false
     */
    public boolean removeSong(String songName, String artist) {
        int idx = -1;
        for (int i = 0; i < this.songs.size(); i++) {
            Song song = this.songs.get(i);
            if (song.getTitle().equals(songName) && song.getArtist().equals(artist)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            this.songs.remove(idx);
            return true;
        }
        return false;
    }
}
