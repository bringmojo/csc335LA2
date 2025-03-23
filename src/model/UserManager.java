package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Manager class for user authentication and user data
 */
public class UserManager {
    private List<User> users;
    private User currentUser;
    private static final String USER_FILE = "users.json";
    private static final String USER_DATA_DIR = "userdata/";

    /**
     * Constructor for UserManager
     */
    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
        // Create user data directory if it doesn't exist
        File userDataDir = new File(USER_DATA_DIR);
        if (!userDataDir.exists()) {
            userDataDir.mkdir();
        }
    }

    /**
     * Generate a random salt for password hashing
     * @return Base64 encoded salt string
     */
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hash password with salt using SHA-256
     * @param password plain text password
     * @param salt salt string
     * @return hashed password
     */
    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Register a new user
     * @param username username
     * @param password plain text password
     * @return true if registration successful, false if username already exists
     */
    public boolean registerUser(String username, String password) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        // Generate salt and hash password
        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);

        // Create new user
        User newUser = new User(username, passwordHash, salt);
        users.add(newUser);
        saveUsers();
        return true;
    }

    /**
     * Login a user
     * @param username username
     * @param password plain text password
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                String passwordHash = hashPassword(password, user.getSalt());
                if (passwordHash.equals(user.getPasswordHash())) {
                    currentUser = user;
                    loadUserData(username);
                    return true;
                }
                return false; // Password incorrect
            }
        }
        return false; // Username not found
    }

    /**
     * Get current logged in user
     * @return current user or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logout current user
     */
    public void logout() {
        if (currentUser != null) {
            saveUserData(currentUser.getUsername());
            currentUser = null;
        }
    }

    /**
     * Save users to JSON file
     */
    private void saveUsers() {
        JSONArray userArray = new JSONArray();

        for (User user : users) {
            JSONObject userObj = new JSONObject();
            userObj.put("username", user.getUsername());
            userObj.put("passwordHash", user.getPasswordHash());
            userObj.put("salt", user.getSalt());
            userArray.add(userObj);
        }

        try (FileWriter file = new FileWriter(USER_FILE)) {
            file.write(userArray.toJSONString());
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Load users from JSON file
     */
    private void loadUsers() {
        File userFile = new File(USER_FILE);
        if (!userFile.exists()) {
            return; // No users file yet
        }

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(USER_FILE)) {
            JSONArray userArray = (JSONArray) parser.parse(reader);

            for (Object obj : userArray) {
                JSONObject userObj = (JSONObject) obj;
                String username = (String) userObj.get("username");
                String passwordHash = (String) userObj.get("passwordHash");
                String salt = (String) userObj.get("salt");

                User user = new User(username, passwordHash, salt);
                users.add(user);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    /**
     * Save user library data to file
     * @param username username
     */
    public void saveUserData(String username) {
        if (currentUser == null || !currentUser.getUsername().equals(username)) {
            return;
        }

        String filename = USER_DATA_DIR + username + ".dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            LibraryModel library = currentUser.getUserLibrary();
            oos.writeObject(library);
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    /**
     * Load user library data from file
     * @param username username
     */
    private void loadUserData(String username) {
        String filename = USER_DATA_DIR + username + ".dat";
        File userDataFile = new File(filename);

        if (!userDataFile.exists()) {
            return; // No user data file yet
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            LibraryModel library = (LibraryModel) ois.readObject();
            currentUser.setUserLibrary(library);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }
}