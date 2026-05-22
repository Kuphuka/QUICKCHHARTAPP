package com.mycompany.quickchartpart1;

import java.util.Random;

public class Message {
    
    private final String idNumber;
    private final int countNumber;
    private final String phoneNumber;
    private final String messageText;
    private final String securityCode;
    private String currentStatus;
    
    private static int totalSent = 0;
    private static int nextCount = 1;
    
    public Message(String phone, String text) {
        this.idNumber = createRandomId();
        this.countNumber = nextCount++;
        this.phoneNumber = phone;
        this.messageText = text;
        this.securityCode = createSecurityCode();
        this.currentStatus = "waiting";
    }
    
    private String createRandomId() {
        Random randomGenerator = new Random();
        long randomTenDigits = 1000000000L + (long)(randomGenerator.nextDouble() * 9000000000L);
        return String.valueOf(randomTenDigits);
    }
    
    private String createSecurityCode() {
        String firstTwo = idNumber.substring(0, 2);
        String firstWord = grabFirstWord(messageText);
        String lastWord = grabLastWord(messageText);
        
        String code = firstTwo + ":" + countNumber + ":" + firstWord + lastWord;
        return code.toUpperCase();
    }
    
    private String grabFirstWord(String sentence) {
        String[] parts = sentence.split(" ");
        if (parts.length > 0) {
            return parts[0];
        } else {
            return "";
        }
    }
    
    private String grabLastWord(String sentence) {
        String[] parts = sentence.split(" ");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        } else {
            return "";
        }
    }
    
    public static String checkLength(String text) {
        int howLong = text.length();
        
        if (howLong <= 250) {
            return "Message ready to send.";
        } else {
            int tooMuch = howLong - 250;
            return "Message exceeds 250 characters by " + tooMuch + "; please reduce the size.";
        }
    }
    
    public static String checkPhoneNumber(String phone) {
        if (phone == null || phone.length() == 0) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        if (phone.charAt(0) != '+') {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        for (int i = 1; i < phone.length(); i++) {
            char letter = phone.charAt(i);
            if (letter < '0' || letter > '9') {
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
        }
        
        return "Cell phone number successfully captured.";
    }
    
    public String doAction(int choice) {
        switch (choice) {
            case 1 -> {
                this.currentStatus = "sent";
                totalSent++;
                return "Message successfully sent.";
            }
            case 2 -> {
                this.currentStatus = "discarded";
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                this.currentStatus = "stored";
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option. Please choose 1, 2, or 3.";
            }
        }
    }
    
    public String showAllDetails() {
        return "Message ID: " + idNumber + 
               ", Message Hash: " + securityCode + 
               ", Recipient: " + phoneNumber + 
               ", Message: " + messageText;
    }
    
    public String getIdNumber() { return idNumber; }
    public int getCountNumber() { return countNumber; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getMessageText() { return messageText; }
    public String getSecurityCode() { return securityCode; }
    public String getCurrentStatus() { return currentStatus; }
    public static int getTotalSent() { return totalSent; }
}