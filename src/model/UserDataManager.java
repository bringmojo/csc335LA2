package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the persistence of user data
 * including library songs, playlists, and user preferences
 */
public class UserDataManager {
    private static final String USER_DATA_FILE = "user_data.ser";

    /**
     * Save the library model data to file
     * @param library the library model to save
     * @return true if successful, false otherwise
     */
    public static boolean saveLibraryData(LibraryModel library) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(library);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving library data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load the library model data from file
     * @return the loaded library model or a new one if file doesn't exist
     */
    public static LibraryModel loadLibraryData() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            return new LibraryModel();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(USER_DATA_FILE))) {
            return (LibraryModel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading library data: " + e.getMessage());
            return new LibraryModel();
        }
    }
}