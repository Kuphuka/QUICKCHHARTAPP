package com.mycompany.quickchartpart1;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * This is the main class for my chat application.
 * It handles registration, login, and the messaging menu.
 * 
 * Student: Khupuka Ngobeni
 */

public class Main {
    
    // This list will store all messages created in this session
    private static final ArrayList<Message> myMessages = new ArrayList<>();
    
    // Scanner for reading user input
    private static final Scanner keyboard = new Scanner(System.in);
    
    // Login object for account management
    private static final Login app = new Login();
    
    public static void main(String[] args) {
        
        // First, let the user register and login
        boolean success = doRegistrationAndLogin();
        
        if (!success) {
            System.out.println("Login failed. Exiting program.");
            keyboard.close();
            return;
        }
        
        // Welcome message after successful login
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     WELCOME TO QUICKCHAT");
        System.out.println("=".repeat(50));
        
        // Main menu loop
        int option;
        do {
            showMenu();
            option = keyboard.nextInt();
            keyboard.nextLine(); // Clear the enter key
            
            switch (option) {
                case 1 -> sendNewMessages();
                case 2 -> System.out.println("\nThis feature is coming in Part 3!");
                case 3 -> {
                    System.out.println("\nThank you for using QuickChat!");
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("\nInvalid choice. Please select 1, 2, or 3.");
            }
        } while (option != 3);
        
        keyboard.close();
    }
    
    // This method handles registration and login
    private static boolean doRegistrationAndLogin() {
        
        // === REGISTRATION ===
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        CREATE ACCOUNT");
        System.out.println("=".repeat(40));
        
        System.out.print("Choose a username (must have _ and max 5 letters): ");
        String newUser = keyboard.nextLine();
        
        System.out.print("Choose a password (8+ chars, A-Z, 0-9, special): ");
        String newPass = keyboard.nextLine();
        
        System.out.print("Enter your first name: ");
        String first = keyboard.nextLine();
        
        System.out.print("Enter your last name: ");
        String last = keyboard.nextLine();
        
        System.out.print("Enter your phone number (start with +): ");
        String phone = keyboard.nextLine();
        
        String regResult = app.createAccount(newUser, newPass, first, last, phone);
        System.out.println(regResult);
        
        // If registration failed, stop here
        if (!regResult.startsWith("Registration successful")) {
            return false;
        }
        
        // === LOGIN ===
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           LOGIN");
        System.out.println("=".repeat(40));
        
        System.out.print("Enter your username: ");
        String loginUser = keyboard.nextLine();
        
        System.out.print("Enter your password: ");
        String loginPass = keyboard.nextLine();
        
        String loginResult = app.getLoginMessage(loginUser, loginPass);
        System.out.println(loginResult);
        
        return loginResult.startsWith("Welcome");
    }
    
    // This shows the main menu options
    private static void showMenu() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(30));
        System.out.println("1. Send Messages");
        System.out.println("2. View Messages (Coming Soon)");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }
    
    // This lets the user send multiple messages
    private static void sendNewMessages() {
        
        System.out.print("\nHow many messages would you like to send? ");
        int howMany = keyboard.nextInt();
        keyboard.nextLine();
        
        // Loop for each message
        for (int i = 1; i <= howMany; i++) {
            
            System.out.println("\n" + "=".repeat(30));
            System.out.println("MESSAGE #" + i);
            System.out.println("=".repeat(30));
            
            // Get recipient phone number
            System.out.print("Enter recipient's phone number (start with +): ");
            String phone = keyboard.nextLine();
            System.out.println(Message.checkPhoneNumber(phone));
            
            // Get message text
            System.out.print("Type your message (max 250 characters): ");
            String text = keyboard.nextLine();
            System.out.println(Message.checkLength(text));
            
            // Skip if message is too long
            if (Message.checkLength(text).startsWith("Message exceeds")) {
                System.out.println("Message not saved - it was too long.");
                continue;
            }
            
            // Create the message
            Message msg = new Message(phone, text);
            
            // Ask what to do with it
            System.out.println("\nWhat would you like to do?");
            System.out.println("1 - Send it now");
            System.out.println("2 - Discard / Delete it");
            System.out.println("3 - Save it for later");
            System.out.print("Your choice: ");
            int action = keyboard.nextInt();
            keyboard.nextLine();
            
            // Process the choice
            String actionResult = msg.doAction(action);
            System.out.println(actionResult);
            
            // Show details if sent or stored
            if (action == 1 || action == 3) {
                System.out.println("\n--- Message Confirmation ---");
                System.out.println(msg.showAllDetails());
                myMessages.add(msg);
            }
        }
        
        // Show summary at the end
        System.out.println("\n" + "=".repeat(40));
        System.out.println("SESSION SUMMARY");
        System.out.println("=".repeat(40));
        System.out.println("Messages sent this session: " + Message.getTotalSent());
        System.out.println("Total messages in history: " + myMessages.size());
    }
}