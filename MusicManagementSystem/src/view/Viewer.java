/*
 * Author: Jifei Wang, Lanyue Zhang(LA2 change)
 * This is the main viewer class for the overall program
 */
package view;

import model.*;
import java.util.List;
import java.util.Scanner;

public class Viewer {
    private final Scanner scanner =new Scanner(System.in);
    
    /*
     * constructor
     */
    public Viewer(){}

    /*
     * Run the main menu loop for user interaction with the music library.
     * Users can select different options to manage and play music.
     * @param store, which contain available songs and albums.
     * @param library, which manage song data and playback history.
     */
    public void run(MusicStore store, LibraryModel library) {
        while (true) {
            System.out.println("""
                \n--- Music Library Menu ---
                1. Play a song
                2. Show recent played songs
                3. Show top played songs
                4. Sort songs
                5. Delete song/album
                6. Shuffle play
                7. Exit
                Choose an option:
                """);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> playSong(library);
                case "2" -> listRecentPlayed(library);
                case "3" -> listTopPlayed(library);
                case "4" -> sortSongs(library);
                case "5" -> deleteMusic(library);
                case "6" -> shufflePlay(library);
                case "7" -> {
                    System.out.println("Exit!");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }
     /*
     * Allows the user to play a song by entering the title and artist.
     * The song is then played and recorded in the playback history.
     * @param library
     */
    private void playSong(LibraryModel library){
        System.out.println("Please enter song title: ");
        String songTitle= scanner.nextLine();
        System.out.println("Please enter artist: ");
        String artist= scanner.nextLine();
        library.playSong(songTitle, artist);
    }
    
    /*
     * Displays a list of recently played songs from the library.
     * @param library
     */
    private void listRecentPlayed(LibraryModel library){
        System.out.println("\n--- Recent Played Songs ---");
        library.getRecentPlayedSongs().forEach(System.out::println);
    }
    
    /*
     * display a list of top-played songs based on play counts.
     * @param library
     */
    private voidnlistTopPlayed(LibraryModel library){
        System.out.println("\n--- Top Played Songs ---");
        library.getTopPlayedSongs().forEach(System.out::println);
    }
    
    /*
     * Allows the user to sort songs by title, artist, or rating and displays the sorted list.
     * @param library
     */
    private void sortSongs(LibraryModel library){
        System.out.println("Sort by: 1. Title;  2. Artist;  3. Rating.");
        String choice = scanner.nextLine();
        List<Song> sortedSongs = switch (choice) {
            case "1" -> library.getSongsSortedByTitle();
            case "2" -> library.getSongsSortedByArtist();
            case "3" -> library.getSongsSortedByRating();
            default -> {
                System.out.println("Invalid choice, return to menu.");
                yield List.of();
            }
        };
        sortedSongs.forEach(System.out::println);
    }
    
    /*
     * Allows the user to delete either a specific song or an entire album.
     * @param library
     */
    private void deleteMusic(LibraryModel library){
        System.out.print("Delete: 1. Song  2. Album\nChoose: ");
        String choice = scanner.nextLine();
        if (choice.equals("1")){
            System.out.print("Enter song title: ");
            String title = scanner.nextLine();
            System.out.print("Enter artist: ");
            String artist = scanner.nextLine();
            library.deleteSong(title, artist);
        } else if (choice.equals("2")){
            System.out.print("Enter album name: ");
            String album = scanner.nextLine();
            library.deleteAlbum(album);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /*
     * play all songs in the library in a shuffled order.
     * @param library
     */
    private void shufflePlay(LibraryModel library){  
        System.out.println("\n--- Shuffle Play ---");
        for(Song song: library){
            System.out.println("Playing: " + song);
        }
    }

    /*
     * Main menu for program
     */
    public void mainMenu() {
        System.out.println("***************************************************");
        System.out.println("  1. Music Store                                   ");
        System.out.println("  2. User Library                                  ");
        System.out.println("  3. Exit                                          ");
        System.out.println("***************************************************");
    }

    /**
     * Get the user input option
     * @param min input min option
     * @param max input max option
     * @return valid option between min and max
     */
    private int getUserInputOption(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Please input the option between %d - %d: ", min, max);
            try {
                int option = scanner.nextInt();
                if (option < min || option > max) {
                    System.out.println("Error: Invalid option, Please try again");
                }
                else {
                    return option;
                }
            }
            catch (Exception e) {
                System.out.println("Error: Invalid option, Please try again");
            }
        }
    }

    /**
     * Music store menu
     */
    private void musicStoreMenu() {
        System.out.println();
        System.out.println("***************************************************");
        System.out.println("     Music Store                                   ");
        System.out.println("  1. Find Song by Title                            ");
        System.out.println("  2. Find Song by Artist                           ");
        System.out.println("  3. Find Album by Title                           ");
        System.out.println("  4. Find Album by Artist                          ");
        System.out.println("  5. Exit                                          ");
        System.out.println("***************************************************");
        System.out.println();
    }

    /**
     * Display list of songs
     * @param songs list of input songs
     */
    private void displaySongs(List<Song> songs) {
        System.out.printf(String.format("%-50s%-50s%s\n", "Song title", "Song artist", "Song album"));
        for (Song song : songs) {
            System.out.println(song);
        }
        System.out.println();
    }

    /**
     * Search the store song by the title
     */
    private void searchStoreSongByTitle(MusicStore musicStore) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your song title: ");
        String title = scanner.nextLine();
        List<Song> songs = musicStore.searchSongByTitle(title);
        if (songs.isEmpty()) {
            System.out.println("Error: Song does not exist");
            return;
        }

        displaySongs(songs);
    }

    /**
     * Search the store song by the artist
     */
    private void searchStoreSongByArtist(MusicStore musicStore) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your song artist: ");
        String artist = scanner.nextLine();
        List<Song> songs = musicStore.searchSongByArtist(artist);
        if (songs.isEmpty()) {
            System.out.println("Error: Song does not exist");
            return;
        }

        displaySongs(songs);
    }

    /**
     * Search the store album by title
     */
    private void searchStoreAlbumByTitle(MusicStore musicStore) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your album title: ");
        String title = scanner.nextLine();
        List<Album> albums = musicStore.searchAlbumByTitle(title);
        if (albums.isEmpty()) {
            System.out.println("Error: Album does not exist");
            return;
        }

        for (Album album : albums) {
            System.out.println(album);
        }
        System.out.println();
    }

    /**
     * Search the store album by artist
     */
    private void searchStoreAlbumByArtist(MusicStore musicStore) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your album artist: ");
        String artist = scanner.nextLine();
        List<Album> albums = musicStore.searchAlbumByArtist(artist);
        if (albums.isEmpty()) {
            System.out.println("Error: Album does not exist");
            return;
        }

        for (int i = 0; i < albums.size(); i++) {
            System.out.println("The " + i + " index album shown as below: ");
            System.out.println(albums.get(i));
        }
        System.out.println();
    }

    /**
     * Music store run function
     */
    private void musicStoreRun(MusicStore musicStore) {
        boolean running = true;
        int option = 0;
        while (running) {
            musicStoreMenu();
            option = getUserInputOption(1, 5);
            switch (option) {
                case 1:
                    searchStoreSongByTitle(musicStore);
                    break;
                case 2:
                    searchStoreSongByArtist(musicStore);
                    break;
                case 3:
                    searchStoreAlbumByTitle(musicStore);
                    break;
                case 4:
                    searchStoreAlbumByArtist(musicStore);
                    break;
                case 5:
                    running = false;
                    break;
            }
        }
    }

    /**
     * user library menu
     */
    private void userLibraryMenu() {
        System.out.println();
        System.out.println("***************************************************");
        System.out.println("     User Library                                  ");
        System.out.println("  1. Find Song in User Library by Title            ");
        System.out.println("  2. Find Song in User Library by Artist           ");
        System.out.println("  3. Find Album in User Library by Title           ");
        System.out.println("  4. Find Album in User Library by Artist          ");
        System.out.println("  5. Add Song To Library                           ");
        System.out.println("  6. Add Album to Library                          ");
        System.out.println("  7. Create a PlayList                             ");
        System.out.println("  8. Add Song to PlayList                          ");
        System.out.println("  9. Remove Song from PlayList                     ");
        System.out.println("  10. List all Songs in library                    ");
        System.out.println("  11. List all Artists in library                  ");
        System.out.println("  12. List all Albums in library                   ");
        System.out.println("  13. List all PlayList in library                 ");
        System.out.println("  14. List all Favorite songs in library           ");
        System.out.println("  15. Rating Song                                  ");
        System.out.println("  16. Mark Song as Favorite                        ");
        System.out.println("  0.  Exit                                         ");
        System.out.println("***************************************************");
        System.out.println();
    }



    /**
     * List all play list in the user library
     * @param library input library
     */
    private void listAllPlayListInLibrary(LibraryModel library) {
        List<PlayList> playlists = library.getPlayLists();
        System.out.printf("All PlayList in Library shown as below: \n");
        for (PlayList playlist : playlists) {
            System.out.println(playlist.getListName());
        }
        System.out.println();
    }


    /**
     * List all albums in the user library
     * @param library input library
     */
    private void listAllAlbumInLibrary(LibraryModel library) {
        List<String> albums = library.getAllAlbums();
        System.out.printf("All album in Library shown as below: \n");
        for (String album : albums) {
            System.out.println(album);
        }
        System.out.println();
    }

    /**
     * List all artists in the user library
     * @param library input library
     */
    private void listAllArtistInLibrary(LibraryModel library) {
        List<String> artists = library.getAllArtists();
        System.out.printf("All artists in Library shown as below: \n");
        for (String artist : artists) {
            System.out.println(artist);
        }
        System.out.println();
    }

    /**
     * List all favorite songs in the user library
     * @param library input library
     */
    private void listAllFavoriteSongInLibrary(LibraryModel library) {
        List<Song> songs = library.getFavorites();
        if (songs.isEmpty()) {
            System.out.println("Error: favorite songs empty.");
            return;
        }
        System.out.printf("All favorite songs in Library shown as below: \n");
        displaySongs(songs);
    }

    /**
     * List all songs in the user library
     * @param library input library
     */
    private void listAllSongInLibrary(LibraryModel library) {
        List<String> songs = library.getAllSongs();
        System.out.printf("All songs in Library shown as below: \n");
        for (String song : songs) {
            System.out.println(song);
        }
        System.out.println();
    }

    /**
     * Remove song from the play list
     * @param library input user library
     */
    private void removeSongFromPlayList(LibraryModel library) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input the song title: ");
        String title = scanner.nextLine();

        System.out.printf("Please input the song artist: ");
        String artist = scanner.nextLine();

        System.out.printf("Please input the play list name: ");
        String name = scanner.nextLine();

        if (library.removeSongFromPlayList(title, artist, name)) {
            System.out.println("Song deleted successfully from " + name);
        }
        else {
            System.out.println("Error: Song does not exist in " + name);
        }
    }

    /**
     * Add songs to the play list
     * @param musicStore input music store
     * @param library input user library
     */
    private void addSongToPlayList(MusicStore musicStore, LibraryModel library) {
        List<Song> songs = musicStore.getAllSongs();
        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song " +
                "album"));
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(String.format("%-5d%s", i, songs.get(i).toString()));
        }
        int option = getUserInputOption(0, songs.size() - 1);
        Song song = songs.get(option);
        Song newSong = new Song(song.getTitle(), song.getArtist(), song.getAlbum());

        System.out.printf("Please input the PlayList name: ");
        Scanner scanner = new Scanner(System.in);
        String playListName = scanner.nextLine();
        if (library.addSongToPlayList(newSong, playListName)) {
            System.out.println("Song added to PlayList " + playListName);
        }
        else {
            System.out.println("Error: Song failed added to PlayList " + playListName);
        }
    }

    /**
     * Find the song by title in the user library
     * @param library input user library
     */
    private void findSongByTitleInUserLibrary(LibraryModel library) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your title: ");
        String title = scanner.nextLine();
        List<Song> songs = library.searchSongByTitle(title);
        if (songs.isEmpty()) {
            System.out.println("Error: Song does not exist");
            return;
        }

        displaySongs(songs);
    }

    /**
     * Find the song by title in the user library
     * @param library input user library
     */
    private void findSongByArtistInUserLibrary(LibraryModel library) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your artist: ");
        String artist = scanner.nextLine();
        List<Song> songs = library.searchSongByArtist(artist);
        if (songs.isEmpty()) {
            System.out.println("Error: Song does not exist");
            return;
        }

        displaySongs(songs);
    }

    /**
     * Find the album by title in the user library
     * @param library input user library
     */
    private void findAlbumByTitleInUserLibrary(MusicStore musicStore, LibraryModel library) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your title: ");
        String title = scanner.nextLine();
        List<Album> albums = library.searchAlbumByTitle(musicStore, title);
        if (albums.isEmpty()) {
            System.out.println("Error: Album does not exist");
            return;
        }

        for (Album album : albums) {
            System.out.println(album);
        }
        System.out.println();
    }

    /**
     * Find the album by title in the user library
     * @param library input user library
     */
    private void findAlbumByArtistInUserLibrary(MusicStore musicStore, LibraryModel library) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input your artist: ");
        String artist = scanner.nextLine();
        List<Album> albums = library.searchAlbumByArtist(musicStore, artist);
        if (albums.isEmpty()) {
            System.out.println("Error: Album does not exist");
            return;
        }

        for (Album album : albums) {
            System.out.println(album);
        }
        System.out.println();
    }

    /**
     * add songs to the library
     * @param musicStore input music store
     * @param library input library
     */
    private void addSongToLibrary(MusicStore musicStore, LibraryModel library) {
        List<Song> songs = musicStore.getAllSongs();
        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song " +
                "album"));
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(String.format("%-5d%s", i, songs.get(i).toString()));
        }
        int option = getUserInputOption(0, songs.size() - 1);
        Song song = songs.get(option);
        Song newSong = new Song(song.getTitle(), song.getArtist(), song.getAlbum());
        if (library.addSongToLibrary(newSong)) {
            System.out.println("Added song to library successfully.");
        }
        else {
            System.out.println("Error adding song.");
        }
    }

    /**
     * Add a album to the library
     * @param musicStore input music store
     * @param library input library
     */
    private void addAlbumToLibrary(MusicStore musicStore, LibraryModel library) {
        List<Album> albums = musicStore.getAlbums();

        System.out.println(String.format("%-10s%-50s%-50s",
                "Index", "Album Title", "Album Artist"));
        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            System.out.println(String.format("%-10d%-50s%-50s",
                    i, album.getTitle(), album.getArtist()));
        }

        int option = getUserInputOption(0, albums.size() - 1);
        List<Song> songs = albums.get(option).getSongs();
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            Song newSong = new Song(song.getTitle(), song.getArtist(), song.getAlbum());
            library.addSongToLibrary(newSong);
        }
    }

    /**
     * Create a new play list in the library
     * @param library input library
     */
    private void createPlayList(LibraryModel library) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Please input the new play list name: ");
        String name = scanner.nextLine();
        if (library.createPlayList(name)) {
            System.out.println("Playlist added successfully.");
        }
        else {
            System.out.println("Error adding playlist.");
        }
    }

    /**
     * Rate song in the library
     * @param library input library
     */
    private void rateSong(LibraryModel library) {
        List<Song> songs = library.getLibrary();
        if (songs.isEmpty()) {
            System.out.println("Error: Library is empty");
            return;
        }

        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song " +
                "album"));
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(String.format("%-5d%s", i, songs.get(i).toString()));
        }
        int option = getUserInputOption(0, songs.size() - 1);
        Song song = songs.get(option);
        Scanner scanner = new Scanner(System.in);

        int score = 0;
        while (true) {
            System.out.printf("Please input the score [1 - 5]: ");
            score = scanner.nextInt();
            if (score < 1 || score > 5) {
                System.out.println("Error: Invalid score " + score);
            }
            else {
                break;
            }
        }
        library.rateSong(song, score);
    }

    /**
     * Mark song as favorite
     * @param library input library
     */
    private void markSongAsFavorite(LibraryModel library) {
        List<Song> songs = library.getLibrary();
        if (songs.isEmpty()) {
            System.out.println("Error: Library is empty");
            return;
        }

        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song " +
                "album"));
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(String.format("%-5d%s", i, songs.get(i).toString()));
        }
        int option = getUserInputOption(0, songs.size() - 1);
        Song song = songs.get(option);
        if (library.markSongAsFavorite(song)) {
            System.out.println("Song added to favorite successfully.");
        }
        else {
            System.out.println("Error: Song added to favorite failed.");
        }
    }

    /**
     * This is the user library operations function
     * @param musicStore input music store
     * @param library input library
     */
    private void userLibraryRun(MusicStore musicStore, LibraryModel library) {
        boolean running = true;
        int option = 0;
        while (running) {
            userLibraryMenu();
            option = getUserInputOption(0, 16);
            switch (option) {
                case 0:
                    running = false;
                    break;
                case 1:
                    findSongByTitleInUserLibrary(library);
                    break;
                case 2:
                    findSongByArtistInUserLibrary(library);
                    break;
                case 3:
                    findAlbumByTitleInUserLibrary(musicStore, library);
                    break;
                case 4:
                    findAlbumByArtistInUserLibrary(musicStore, library);
                    break;
                case 5:
                    addSongToLibrary(musicStore, library);
                    break;
                case 6:
                    addAlbumToLibrary(musicStore, library);
                    break;
                case 7:
                    createPlayList(library);
                    break;
                case 8:
                    addSongToPlayList(musicStore, library);
                    break;
                case 9:
                    removeSongFromPlayList(library);
                    break;
                case 10:
                    listAllSongInLibrary(library);
                    break;
                case 11:
                    listAllArtistInLibrary(library);
                    break;
                case 12:
                    listAllAlbumInLibrary(library);
                    break;
                case 13:
                    listAllPlayListInLibrary(library);
                    break;
                case 14:
                    listAllFavoriteSongInLibrary(library);
                    break;
                case 15:
                    rateSong(library);
                    break;
                case 16:
                    markSongAsFavorite(library);
                    break;
            }
        }
    }


    /**
     * main entrance of the program
     */
    public void run(MusicStore musicStore, LibraryModel library) {
        boolean running = true;
        int option = 0;
        while (running) {
            mainMenu();
            option = getUserInputOption(1, 3);
            switch (option) {
                case 1:
                    musicStoreRun(musicStore);
                    break;
                case 2:
                    userLibraryRun(musicStore, library);
                    break;
                case 3:
                    running = false;
                    System.out.println("Byebye...");
                    break;
            }
        }
    }
}
