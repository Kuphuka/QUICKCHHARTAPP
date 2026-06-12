package com.mycompany.quickchartapp1;

public class TestMessage {
    public static void main(String[] args) {
        
        System.out.println("Testing Message Class...");
        System.out.println("========================");
        
        Message msg = new Message("+27731234567", "Hello World");
        
        System.out.println("Message ID: " + msg.getIdNumber());
        System.out.println("Hash: " + msg.getSecurityHash());
        System.out.println("Status: " + msg.getStatus());
        
        System.out.println("\nSending message...");
        String result = msg.doAction(1);
        System.out.println("Result: " + result);
        System.out.println("New Status: " + msg.getStatus());
        
        System.out.println("\nTotal messages sent: " + Message.getTotalSent());
        
        System.out.println("\nPhone validation test:");
        System.out.println("Valid phone (+27831234567): " + Message.checkPhoneNumber("+27831234567"));
        System.out.println("Invalid phone (0831234567): " + Message.checkPhoneNumber("0831234567"));
        
        System.out.println("\nMessage length test:");
        System.out.println("Short message: " + Message.checkLength("Short message"));
        
        System.out.println("\n========================");
        System.out.println("Test completed!");
    }
}