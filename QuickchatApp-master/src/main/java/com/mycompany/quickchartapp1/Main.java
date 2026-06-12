package com.mycompany.quickchartapp1;

import com.mycompany.quickchartpart1.Login;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static final ArrayList<Message> discardedMessages = new ArrayList<>();
    private static final ArrayList<Message> allMessages = new ArrayList<>();
    
    private static final Scanner input = new Scanner(System.in);
    private static final Login app = new Login();
    
    public static void main(String[] args) {
        
        try (input) {
            if (!doRegistrationAndLogin()) {
                try (input) {
                    System.out.println("Login failed. Exiting...");
                }
                return;
            }
            
            System.out.println("\n==================================================");
            System.out.println("     WELCOME TO QUICKCHAT");
            System.out.println("==================================================");
            
            int choice;
            do {
                showMainMenu();
                choice = input.nextInt();
                input.nextLine();
                
                switch (choice) {
                    case 1:
                        sendMessages();
                        break;
                    case 2:
                        viewStoredMessages();
                        break;
                    case 3:
                        searchMessages();
                        break;
                    case 4:
                        deleteMessageByHash();
                        break;
                    case 5:
                        generateFullReport();
                        break;
                    case 6:
                        System.out.println("\nThank you for using QuickChat!");
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please select 1-6.");
                }
            } while (choice != 6);
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Send Messages");
        System.out.println("2. View Stored Messages");
        System.out.println("3. Search Messages");
        System.out.println("4. Delete Message by Hash");
        System.out.println("5. Generate Full Report");
        System.out.println("6. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    private static boolean doRegistrationAndLogin() {
        
        System.out.println("\n========================================");
        System.out.println("        CREATE NEW ACCOUNT");
        System.out.println("========================================");
        
        System.out.print("Username (must have _ and max 5 letters): ");
        String newUser = input.nextLine();
        
        System.out.print("Password (8+ chars, A-Z, 0-9, special): ");
        String newPass = input.nextLine();
        
        System.out.print("First name: ");
        String first = input.nextLine();
        
        System.out.print("Last name: ");
        String last = input.nextLine();
        
        System.out.print("Phone number (start with +): ");
        String phone = input.nextLine();
        
        String regResult = app.createAccount(newUser, newPass, first, last, phone);
        System.out.println(regResult);
        
        if (!regResult.startsWith("Registration successful")) {
            return false;
        }
        
        System.out.println("\n========================================");
        System.out.println("              LOGIN");
        System.out.println("========================================");
        
        System.out.print("Username: ");
        String loginUser = input.nextLine();
        
        System.out.print("Password: ");
        String loginPass = input.nextLine();
        
        String loginResult = app.getLoginMessage(loginUser, loginPass);
        System.out.println(loginResult);
        
        return loginResult.startsWith("Welcome");
    }
    
    private static void sendMessages() {
        System.out.print("\nHow many messages would you like to send? ");
        int count = input.nextInt();
        input.nextLine();
        
        for (int i = 1; i <= count; i++) {
            System.out.println("\n" + "=".repeat(30));
            System.out.println("MESSAGE #" + i);
            System.out.println("=".repeat(30));
            
            System.out.print("Recipient phone number (start with +): ");
            String phone = input.nextLine();
            String phoneResult = Message.checkPhoneNumber(phone);
            System.out.println(phoneResult);
            
            System.out.print("Your message (max 250 characters): ");
            String text = input.nextLine();
            String lengthResult = Message.checkLength(text);
            System.out.println(lengthResult);
            
            if (lengthResult.startsWith("Message exceeds")) {
                System.out.println("Message not saved - too long.");
                continue;
            }
            
            Message msg = new Message(phone, text);
            
            System.out.println("\nWhat would you like to do?");
            System.out.println("1 - Send now");
            System.out.println("2 - Discard");
            System.out.println("3 - Store for later");
            System.out.print("Your choice: ");
            int action = input.nextInt();
            input.nextLine();
            
            String result = msg.doAction(action);
            System.out.println(result);
            
            switch (action) {
                case 1:
                    sentMessages.add(msg);
                    allMessages.add(msg);
                    System.out.println("\n--- Message Details ---");
                    System.out.println(msg.showDetails());
                    break;
                case 2:
                    discardedMessages.add(msg);
                    System.out.println("\n--- Message Discarded ---");
                    break;
                case 3:
                    storedMessages.add(msg);
                    allMessages.add(msg);
                    System.out.println("\n--- Message Details ---");
                    System.out.println(msg.showDetails());
                    break;
                default:
                    break;
            }
        }
        
        System.out.println("\n" + "=".repeat(40));
        System.out.println("SESSION SUMMARY");
        System.out.println("=".repeat(40));
        System.out.println("Messages sent: " + sentMessages.size());
        System.out.println("Messages stored: " + storedMessages.size());
        System.out.println("Messages discarded: " + discardedMessages.size());
        System.out.println("Total messages: " + allMessages.size());
    }
    
    private static void viewStoredMessages() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        STORED MESSAGES");
        System.out.println("=".repeat(40));
        
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            System.out.println("\nMessage " + (i + 1) + ":");
            System.out.println("  Recipient: " + msg.getRecipientPhone());
            System.out.println("  Message: " + msg.getMessageText());
            System.out.println("  Hash: " + msg.getSecurityHash());
        }
    }
    
    private static void findLongestMessage() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        LONGEST MESSAGE");
        System.out.println("=".repeat(40));
        
        if (allMessages.isEmpty()) {
            System.out.println("No messages available.");
            return;
        }
        
        Message longest = null;
        int maxLength = 0;
        
        for (Message msg : allMessages) {
            if (msg.getMessageText().length() > maxLength) {
                maxLength = msg.getMessageText().length();
                longest = msg;
            }
        }
        
        if (longest != null) {
            System.out.println("Longest message (" + maxLength + " characters):");
            System.out.println("  Recipient: " + longest.getRecipientPhone());
            System.out.println("  Message: " + longest.getMessageText());
            System.out.println("  Hash: " + longest.getSecurityHash());
        }
    }
    
    private static void searchById() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        SEARCH BY MESSAGE ID");
        System.out.println("=".repeat(40));
        
        System.out.print("Enter Message ID: ");
        String searchId = input.nextLine();
        
        boolean found = false;
        
        for (Message msg : allMessages) {
            if (msg.getIdNumber().equals(searchId)) {
                System.out.println("\nMessage Found:");
                System.out.println("  ID: " + msg.getIdNumber());
                System.out.println("  Recipient: " + msg.getRecipientPhone());
                System.out.println("  Message: " + msg.getMessageText());
                System.out.println("  Hash: " + msg.getSecurityHash());
                System.out.println("  Status: " + msg.getStatus());
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("No message found with ID: " + searchId);
        }
    }
    
    private static void searchByRecipient() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        SEARCH BY RECIPIENT");
        System.out.println("=".repeat(40));
        
        System.out.print("Enter recipient phone number: ");
        String searchPhone = input.nextLine();
        
        boolean found = false;
        int count = 0;
        
        System.out.println("\nMessages sent to " + searchPhone + ":");
        
        for (Message msg : allMessages) {
            if (msg.getRecipientPhone().equals(searchPhone)) {
                System.out.println("\n  " + (count + 1) + ":");
                System.out.println("    ID: " + msg.getIdNumber());
                System.out.println("    Message: " + msg.getMessageText());
                System.out.println("    Status: " + msg.getStatus());
                count++;
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No messages found for recipient: " + searchPhone);
        } else {
            System.out.println("\nTotal messages found: " + count);
        }
    }
    
    private static void deleteMessageByHash() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        DELETE BY HASH CODE");
        System.out.println("=".repeat(40));
        
        System.out.print("Enter Message Hash to delete: ");
        String searchHash = input.nextLine();
        
        boolean found = false;
        
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).getSecurityHash().equals(searchHash)) {
                Message deleted = allMessages.remove(i);
                sentMessages.remove(deleted);
                storedMessages.remove(deleted);
                discardedMessages.remove(deleted);
                
                System.out.println("\nMessage successfully deleted!");
                System.out.println("  ID: " + deleted.getIdNumber());
                System.out.println("  Recipient: " + deleted.getRecipientPhone());
                System.out.println("  Message: " + deleted.getMessageText());
                System.out.println("  Hash: " + deleted.getSecurityHash());
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("No message found with hash: " + searchHash);
        }
    }
    
    private static void generateFullReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              FULL MESSAGE REPORT");
        System.out.println("=".repeat(50));
        
        System.out.println("\n--- SENT MESSAGES (" + sentMessages.size() + ") ---");
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            System.out.println("\n  " + (i + 1) + ":");
            System.out.println("    ID: " + msg.getIdNumber());
            System.out.println("    Hash: " + msg.getSecurityHash());
            System.out.println("    Recipient: " + msg.getRecipientPhone());
            System.out.println("    Message: " + msg.getMessageText());
        }
        
        System.out.println("\n--- STORED MESSAGES (" + storedMessages.size() + ") ---");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            System.out.println("\n  " + (i + 1) + ":");
            System.out.println("    ID: " + msg.getIdNumber());
            System.out.println("    Hash: " + msg.getSecurityHash());
            System.out.println("    Recipient: " + msg.getRecipientPhone());
            System.out.println("    Message: " + msg.getMessageText());
        }
        
        System.out.println("\n--- DISCARDED MESSAGES (" + discardedMessages.size() + ") ---");
        for (int i = 0; i < discardedMessages.size(); i++) {
            Message msg = discardedMessages.get(i);
            System.out.println("\n  " + (i + 1) + ":");
            System.out.println("    ID: " + msg.getIdNumber());
            System.out.println("    Hash: " + msg.getSecurityHash());
            System.out.println("    Recipient: " + msg.getRecipientPhone());
            System.out.println("    Message: " + msg.getMessageText());
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("REPORT SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total Sent Messages: " + sentMessages.size());
        System.out.println("Total Stored Messages: " + storedMessages.size());
        System.out.println("Total Discarded Messages: " + discardedMessages.size());
        System.out.println("Grand Total: " + allMessages.size());
        
        findLongestMessage();
    }
    
    private static void searchMessages() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("          SEARCH OPTIONS");
        System.out.println("=".repeat(40));
        System.out.println("1. Search by Message ID");
        System.out.println("2. Search by Recipient Phone");
        System.out.print("\nEnter your choice: ");
        int searchChoice = input.nextInt();
        input.nextLine();
        
        switch (searchChoice) {
            case 1 -> searchById();
            case 2 -> searchByRecipient();
            default -> System.out.println("Invalid choice.");
        }
    }
}