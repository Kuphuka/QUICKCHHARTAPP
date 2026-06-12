package com.mycompany.quickchartapp1;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;
import java.time.LocalDateTime;

public class Message {
    
    private String idNumber;
    private int sequenceNumber;
    private String recipientPhone;
    private String messageText;
    private String securityHash;
    private String status;
    
    private static int totalSent = 0;
    private static int nextSequence = 1;
    
    public Message(String phone, String text) {
        this.idNumber = generateId();
        this.sequenceNumber = nextSequence++;
        this.recipientPhone = phone;
        this.messageText = text;
        this.securityHash = generateHash();
        this.status = "pending";
    }
    
    private String generateId() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }
    
    private String generateHash() {
        String firstTwo = idNumber.substring(0, 2);
        String firstWord = getFirstWord(messageText);
        String lastWord = getLastWord(messageText);
        
        String hash = firstTwo + ":" + sequenceNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
    private String getFirstWord(String sentence) {
        String[] words = sentence.split(" ");
        return words.length > 0 ? words[0] : "";
    }
    
    private String getLastWord(String sentence) {
        String[] words = sentence.split(" ");
        return words.length > 0 ? words[words.length - 1] : "";
    }
    
    public static String checkLength(String text) {
        int length = text.length();
        if (length <= 250) {
            return "Message ready to send.";
        } else {
            int extra = length - 250;
            return "Message exceeds 250 characters by " + extra + "; please reduce the size.";
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
            char c = phone.charAt(i);
            if (c < '0' || c > '9') {
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
        }
        return "Cell phone number successfully captured.";
    }
    
    public String doAction(int choice) {
        if (choice == 1) {
            this.status = "sent";
            totalSent++;
            saveToJsonFile();
            return "Message successfully sent.";
        } else if (choice == 2) {
            this.status = "discarded";
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            this.status = "stored";
            saveToJsonFile();
            return "Message successfully stored.";
        } else {
            return "Invalid option. Please choose 1, 2, or 3.";
        }
    }
    
    private void saveToJsonFile() {
        try {
            JSONObject messageData = new JSONObject();
            messageData.put("messageId", idNumber);
            messageData.put("sequenceNumber", sequenceNumber);
            messageData.put("recipient", recipientPhone);
            messageData.put("message", messageText);
            messageData.put("hashCode", securityHash);
            messageData.put("status", status);
            messageData.put("timeSaved", LocalDateTime.now().toString());
            
            FileWriter writer = new FileWriter("messages.json", true);
            writer.write(messageData.toString() + "\n");
            writer.close();
            
        } catch (IOException error) {
            System.out.println("Could not save message: " + error.getMessage());
        }
    }
    
    public String showDetails() {
        return "Message ID: " + idNumber + 
               ", Message Hash: " + securityHash + 
               ", Recipient: " + recipientPhone + 
               ", Message: " + messageText;
    }
    
    public String getIdNumber() { return idNumber; }
    public int getSequenceNumber() { return sequenceNumber; }
    public String getRecipientPhone() { return recipientPhone; }
    public String getMessageText() { return messageText; }
    public String getSecurityHash() { return securityHash; }
    public String getStatus() { return status; }
    public static int getTotalSent() { return totalSent; }
}