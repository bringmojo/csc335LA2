package model;

import java.util.Objects;

/**
 * This class represents a user in the music library system
 */
public class User {
    private String username;
    private String passwordHash;
    private String salt;
    private LibraryModel userLibrary;

    /**
     * Constructor for User
     * @param username username
     * @param passwordHash hashed password
     * @param salt salt used for hashing
     */
    public User(String username, String passwordHash, String salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.userLibrary = new LibraryModel();
    }

    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get password hash
     * @return password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Get salt
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Get user's library
     * @return library model
     */
    public LibraryModel getUserLibrary() {
        return userLibrary;
    }

    /**
     * Set user's library
     * @param library new library model
     */
    public void setUserLibrary(LibraryModel library) {
        this.userLibrary = library;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}