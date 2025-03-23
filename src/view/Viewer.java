package view;

import model.*;

import java.util.List;
import java.util.Scanner;

/**
 * This is the main viewer class for the overall program
 */
public class Viewer {

    private Scanner scanner;
    private UserManager userManager;

    /**
     * Default constructor
     */
    public Viewer() {
        this.scanner = new Scanner(System.in);
        this.userManager = new UserManager();
    }

    /**
     * Main menu for program
     */
    public void mainMenu() {
        System.out.println("***************************************************");
        System.out.println("  1. Music Store                                   ");
        System.out.println("  2. User Library                                  ");
        System.out.println("  3. Logout                                        ");
        System.out.println("  4. Exit                                          ");
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
     * Start the application
     */
    public void start() {
        AuthView authView = new AuthView(userManager);

        boolean running = true;

        while (running) {
            boolean authenticated = authView.authenticate();

            if (!authenticated) {
                System.out.println("Exiting application...");
                break;
            }

            // User is now authenticated
            MusicStore musicStore = new MusicStore();
            LibraryModel libraryModel = userManager.getCurrentUser().getUserLibrary();

            boolean userSessionActive = true;
            while (userSessionActive) {
                mainMenu();
                int option = getUserInputOption(1, 4);
                switch (option) {
                    case 1:
                        musicStoreRun(musicStore);
                        break;
                    case 2:
                        userLibraryRun(musicStore, libraryModel);
                        break;
                    case 3: // Logout
                        userManager.logout();
                        userSessionActive = false;
                        System.out.println("You have been logged out.");
                        break;
                    case 4: // Exit
                        userManager.logout();
                        running = false;
                        userSessionActive = false;
                        System.out.println("Exiting application...");
                        break;
                }
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
        System.out.println("  5. Find Songs by Genre                           ");
        System.out.println("  6. Add Song To Library                           ");
        System.out.println("  7. Add Album to Library                          ");
        System.out.println("  8. Remove Song from Library                      ");
        System.out.println("  9. Remove Album from Library                     ");
        System.out.println("  10. Create a PlayList                            ");
        System.out.println("  11. Add Song to PlayList                         ");
        System.out.println("  12. Remove Song from PlayList                    ");
        System.out.println("  13. View Playlist Songs                          ");
        System.out.println("  14. List all Songs in library                    ");
        System.out.println("  15. List all Artists in library                  ");
        System.out.println("  16. List all Albums in library                   ");
        System.out.println("  17. List all PlayList in library                 ");
        System.out.println("  18. List all Favorite songs in library           ");
        System.out.println("  19. List Recently Played songs                   ");
        System.out.println("  20. List Most Played songs                       ");
        System.out.println("  21. Play a Song                                  ");
        System.out.println("  22. Play Random Playlist                         ");
        System.out.println("  23. Sort Songs                                   ");
        System.out.println("  24. Rating Song                                  ");
        System.out.println("  25. Mark Song as Favorite                        ");
        System.out.println("  0.  Exit                                         ");
        System.out.println("***************************************************");
        System.out.println();
    }

    /**
     * Play a song from the library
     */
    private void playSong(LibraryModel library) {
        List<Song> songs = library.getLibrary();
        if (songs.isEmpty()) {
            System.out.println("Error: Library is empty");
            return;
        }

        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song album"));
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(String.format("%-5d%s", i, songs.get(i).toString()));
        }

        int option = getUserInputOption(0, songs.size() - 1);
        Song song = songs.get(option);

        // Simulate playing the song
        System.out.println("\n▶ Now playing: " + song.getTitle() + " by " + song.getArtist());
        System.out.println("From album: " + song.getAlbum());

        // Track play
        library.playSong(song);

        // Wait for a key press to "finish" playing
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Play a random playlist
     */
    private void playRandomPlaylist(LibraryModel library) {
        LibraryModel.RandomPlaylistIterator randomPlaylist = library.createRandomPlaylist();

        if (!randomPlaylist.hasNext()) {
            System.out.println("Error: Library is empty");
            return;
        }

        System.out.println("\n▶ Starting shuffle play...");
        System.out.println("Press Enter after each song to continue or type 'stop' to end playback.");

        for (Song song : randomPlaylist) {
            System.out.println("\n▶ Now playing: " + song.getTitle() + " by " + song.getArtist());
            System.out.println("From album: " + song.getAlbum());

            // Track play
            library.playSong(song);

            System.out.print("Press Enter for next song or type 'stop': ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("stop")) {
                System.out.println("Stopping playback...");
                break;
            }
        }
    }

    /**
     * Sort songs in the library
     */
    private void sortSongs(LibraryModel library) {
        System.out.println("\n--- Sort Songs ---");
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Artist");
        System.out.println("3. Sort by Rating");

        int option = getUserInputOption(1, 3);
        String sortBy;

        switch (option) {
            case 1:
                sortBy = "title";
                break;
            case 2:
                sortBy = "artist";
                break;
            case 3:
                sortBy = "rating";
                break;
            default:
                return;
        }

        List<Song> sortedSongs = library.getSortedSongs(sortBy);

        System.out.println("\nSorted Songs:");
        System.out.printf(String.format("%-50s%-50s%-30s%s\n", "Song title", "Song artist", "Song album", "Rating"));
        for (Song song : sortedSongs) {
            System.out.println(song.toString());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * List recently played songs
     */
    private void listRecentlyPlayed(LibraryModel library) {
        List<Song> recentlyPlayed = library.getRecentlyPlayed();

        if (recentlyPlayed.isEmpty()) {
            System.out.println("No songs have been played yet.");
            return;
        }

        System.out.println("\n--- Recently Played Songs ---");
        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song album"));

        for (int i = 0; i < recentlyPlayed.size(); i++) {
            System.out.println(String.format("%-5d%s", i + 1, recentlyPlayed.get(i).toString()));
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * List most played songs
     */
    private void listMostPlayed(LibraryModel library) {
        List<Song> mostPlayed = library.getMostPlayed();

        if (mostPlayed.isEmpty()) {
            System.out.println("No songs have been played yet.");
            return;
        }

        System.out.println("\n--- Most Played Songs ---");
        System.out.printf(String.format("%-5s%-50s%-50s%-30s%s\n", " ", "Song title", "Song artist", "Song album", "Play count"));

        for (int i = 0; i < mostPlayed.size(); i++) {
            Song song = mostPlayed.get(i);
            int playCount = library.getPlayCount(song);
            System.out.println(String.format("%-5d%-50s%-50s%-30s%d",
                    i + 1, song.getTitle(), song.getArtist(), song.getAlbum(), playCount));
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Remove song from library
     */
    private void removeSongFromLibrary(LibraryModel library) {
        List<Song> songs = library.getLibrary();
        if (songs.isEmpty()) {
            System.out.println("Error: Library is empty");
            return;
        }

        System.out.printf(String.format("%-5s%-50s%-50s%s\n", " ", "Song title", "Song artist", "Song album"));
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(String.format("%-5d%s", i, songs.get(i).toString()));
        }

        int option = getUserInputOption(0, songs.size() - 1);
        Song song = songs.get(option);

        if (library.removeSongFromLibrary(song)) {
            System.out.println("Song removed from library successfully.");
        } else {
            System.out.println("Error: Failed to remove song from library.");
        }
    }

    /**
     * Remove album from library
     */
    private void removeAlbumFromLibrary(LibraryModel library) {
        // Get unique album titles
        List<String> albums = library.getAllAlbums();
        if (albums.isEmpty()) {
            System.out.println("Error: No albums in library");
            return;
        }

        System.out.println("Available albums:");
        for (int i = 0; i < albums.size(); i++) {
            System.out.println(i + ". " + albums.get(i));
        }

        int albumIndex = getUserInputOption(0, albums.size() - 1);
        String albumTitle = albums.get(albumIndex);

        // Find an artist for this album
        String artist = "";
        for (Song song : library.getLibrary()) {
            if (song.getAlbum().equals(albumTitle)) {
                artist = song.getArtist();
                break;
            }
        }

        int songsRemoved = library.removeAlbumFromLibrary(albumTitle, artist);
        System.out.println(songsRemoved + " songs removed from library.");
    }

    /**
     * Search for songs by genre
     */
    private void findSongsByGenre(MusicStore musicStore, LibraryModel library) {
        Scanner scanner = new Scanner(System.in);

        // Get all available genres from the music store
        List<String> availableGenres = musicStore.getAllGenres();
        System.out.println("Available genres:");
        for (int i = 0; i < availableGenres.size(); i++) {
            System.out.println(i + ". " + availableGenres.get(i));
        }

        System.out.print("Select a genre number: ");
        int genreIndex;
        try {
            genreIndex = Integer.parseInt(scanner.nextLine());
            if (genreIndex < 0 || genreIndex >= availableGenres.size()) {
                System.out.println("Invalid genre selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        String selectedGenre = availableGenres.get(genreIndex);

        // Search for songs by genre in the library
        List<Song> songs = library.searchSongsByGenre(selectedGenre);

        if (songs.isEmpty()) {
            System.out.println("No songs found in your library with genre: " + selectedGenre);

            // Offer to search the music store
            System.out.print("Would you like to search the music store for songs with this genre? (y/n): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("y")) {
                songs = musicStore.searchSongsByGenre(selectedGenre);
                if (songs.isEmpty()) {
                    System.out.println("No songs found in the music store with genre: " + selectedGenre);
                } else {
                    displaySongs(songs);
                }
            }
        } else {
            System.out.println("Songs in your library with genre " + selectedGenre + ":");
            displaySongs(songs);
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
            option = getUserInputOption(0, 25);
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
                    findSongsByGenre(musicStore, library);
                    break;
                case 6:
                    addSongToLibrary(musicStore, library);
                    break;
                case 7:
                    addAlbumToLibrary(musicStore, library);
                    break;
                case 8:
                    removeSongFromLibrary(library);
                    break;
                case 9:
                    removeAlbumFromLibrary(library);
                    break;
                case 10:
                    createPlayList(library);
                    break;
                case 11:
                    addSongToPlayList(musicStore, library);
                    break;
                case 12:
                    removeSongFromPlayList(library);
                    break;
                case 13:
                    viewPlaylistSongs(library);
                    break;
                case 14:
                    listAllSongInLibrary(library);
                    break;
                case 15:
                    listAllArtistInLibrary(library);
                    break;
                case 16:
                    listAllAlbumInLibrary(library);
                    break;
                case 17:
                    listAllPlayListInLibrary(library);
                    break;
                case 18:
                    listAllFavoriteSongInLibrary(library);
                    break;
                case 19:
                    listRecentlyPlayed(library);
                    break;
                case 20:
                    listMostPlayed(library);
                    break;
                case 21:
                    playSong(library);
                    break;
                case 22:
                    playRandomPlaylist(library);
                    break;
                case 23:
                    sortSongs(library);
                    break;
                case 24:
                    rateSong(library);
                    break;
                case 25:
                    markSongAsFavorite(library);
                    break;
            }
        }
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

    /**
     * View songs in a playlist
     * @param library input user library
     */
    private void viewPlaylistSongs(LibraryModel library) {
        List<PlayList> playlists = library.getPlayLists();

        if (playlists.isEmpty()) {
            System.out.println("No playlists found in your library.");
            return;
        }

        System.out.println("\n--- Available Playlists ---");
        for (int i = 0; i < playlists.size(); i++) {
            PlayList playlist = playlists.get(i);
            String autoGenerated = playlist.isAutoGenerated() ? " (Auto-generated)" : "";
            System.out.println(i + ". " + playlist.getListName() + autoGenerated);
        }

        System.out.print("\nSelect a playlist number to view its songs: ");
        int option;

        try {
            option = Integer.parseInt(scanner.nextLine());
            if (option < 0 || option >= playlists.size()) {
                System.out.println("Invalid playlist selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        PlayList selectedPlaylist = playlists.get(option);
        List<Song> songs = selectedPlaylist.getSongs();

        if (songs.isEmpty()) {
            System.out.println("\nPlaylist '" + selectedPlaylist.getListName() + "' is empty.");
            return;
        }

        System.out.println("\n--- Songs in '" + selectedPlaylist.getListName() + "' ---");
        System.out.printf(String.format("%-5s%-50s%-50s%-30s%s\n",
                "#", "Song title", "Song artist", "Album", "Rating"));

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            String ratingStr = song.getRating() > 0 ? " ★" + song.getRating() : "";
            String favoriteStr = song.isFavorite() ? " ♥" : "";

            System.out.println(String.format("%-5d%-50s%-50s%-30s%s%s",
                    i + 1, song.getTitle(), song.getArtist(),
                    song.getAlbum(), ratingStr, favoriteStr));
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
