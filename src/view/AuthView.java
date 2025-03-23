package view;

import model.UserManager;
import java.util.Scanner;

/**
 * Authentication view for handling user login and registration
 */
public class AuthView {
    private UserManager userManager;
    private Scanner scanner;

    /**
     * Constructor
     * @param userManager User manager instance
     */
    public AuthView(UserManager userManager) {
        this.userManager = userManager;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display the authentication menu
     */
    public void showAuthMenu() {
        System.out.println("\n*************************************");
        System.out.println("*        Music Library System       *");
        System.out.println("*************************************");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("*************************************");
    }

    /**
     * Get user input for authentication
     * @return true if authenticated, false if exit requested
     */
    public boolean authenticate() {
        boolean done = false;

        while (!done) {
            showAuthMenu();
            System.out.print("Enter your choice (1-3): ");
            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Login
                    if (login()) {
                        return true; // Successfully logged in
                    }
                    break;
                case 2:
                    // Register
                    register();
                    break;
                case 3:
                    // Exit
                    return false;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        return false;
    }

    /**
     * Handle user login
     * @return true if login successful, false otherwise
     */
    private boolean login() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = userManager.login(username, password);

        if (success) {
            System.out.println("Login successful! Welcome, " + username + "!");
            return true;
        } else {
            System.out.println("Login failed. Invalid username or password.");
            return false;
        }
    }

    /**
     * Handle user registration
     */
    private void register() {
        System.out.println("\n--- Register ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean success = userManager.registerUser(username, password);

        if (success) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }
}