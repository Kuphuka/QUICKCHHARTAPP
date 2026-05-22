package com.mycompany.quickchartpart1;

/**
 * This is my Login class for the chat app assignment.
 * I wrote this to handle user registration and login.
 * 
 * Student: Khupuka Ngobeni
 */

public class Login {
    
    // These variables will store the user's information
    String username;
    String password;
    String firstName;
    String lastName;
    
    // This method checks if the username is written correctly
    // The username needs an underscore _ and must be 5 letters or less
    public static boolean checkUsername(String name) {
        // Check if it has underscore
        boolean hasUnderscore = name.contains("_");
        // Check if it's short enough
        boolean isShort = name.length() <= 5;
        
        return hasUnderscore && isShort;
    }
    
    // This method checks if the password is strong enough
    // Rules: at least 8 characters, 1 capital letter, 1 number, 1 special character
    public static boolean checkPassword(String pass) {
        
        // First rule: length must be 8 or more
        if (pass.length() < 8) {
            return false;
        }
        
        // Second rule: look for a capital letter (A to Z)
        boolean foundCapital = false;
        for (int i = 0; i < pass.length(); i++) {
            char letter = pass.charAt(i);
            if (letter >= 'A' && letter <= 'Z') {
                foundCapital = true;
                break;
            }
        }
        if (!foundCapital) {
            return false;
        }
        
        // Third rule: look for a number (0 to 9)
        boolean foundNumber = false;
        for (int i = 0; i < pass.length(); i++) {
            char letter = pass.charAt(i);
            if (letter >= '0' && letter <= '9') {
                foundNumber = true;
                break;
            }
        }
        if (!foundNumber) {
            return false;
        }
        
        // Fourth rule: look for a special character (not letter and not number)
        boolean foundSpecial = false;
        for (int i = 0; i < pass.length(); i++) {
            char letter = pass.charAt(i);
            boolean isLetter = (letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z');
            boolean isNumber = (letter >= '0' && letter <= '9');
            
            if (!isLetter && !isNumber) {
                foundSpecial = true;
                break;
            }
        }
        // If we passed all the checks, password is good
        return foundSpecial;
    }
    
    // This method checks if the phone number is valid
    // Phone must start with + and then have only numbers
    public static boolean checkPhoneNumber(String phone) {
        
        // Empty phone numbers are not allowed
        if (phone == null || phone.length() == 0) {
            return false;
        }
        
        // First character must be a plus sign
        if (phone.charAt(0) != '+') {
            return false;
        }
        
        // All other characters must be numbers
        for (int i = 1; i < phone.length(); i++) {
            char digit = phone.charAt(i);
            if (digit < '0' || digit > '9') {
                return false;
            }
        }
        
        return true;
    }
    
    // This method creates a new account
    // It checks everything and saves the information if valid
    public String createAccount(String user, String pass, String first, String last, String phone) {
        
        // First check the username
        if (!checkUsername(user)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        // Then check the password
        if (!checkPassword(pass)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        // Finally check the phone number
        if (!checkPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        // If all checks passed, save the information
        username = user;
        password = pass;
        firstName = first;
        lastName = last;
        
        return "Registration successful! You can now log in.";
    }
    
    // This method checks if the login details match what was saved
    public boolean checkLogin(String user, String pass) {
        return username != null && username.equals(user) && password != null && password.equals(pass);
    }
    
    // This method returns a message based on login success or failure
    public String getLoginMessage(String user, String pass) {
        if (checkLogin(user, pass)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}