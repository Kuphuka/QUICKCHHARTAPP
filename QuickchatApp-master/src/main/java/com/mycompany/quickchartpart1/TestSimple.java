package com.mycompany.quickchartpart1;

public class TestSimple {
    public static void main(String[] args) {
        
        System.out.println("Testing Message.java...");
        System.out.println("=========================");
        
        // Test 1: Create a message
        System.out.println("\n1. Creating a new message...");
        Message msg = new Message("+27731234567", "Hello World");
        
        // Test 2: Show message details
        System.out.println("\n2. Message details:");
        System.out.println("   Message ID: " + msg.getIdNumber());
        System.out.println("   Hash Code: " + msg.getSecurityCode());
        System.out.println("   Status: " + msg.getCurrentStatus());
        
        // Test 3: Send the message
        System.out.println("\n3. Sending message...");
        String result = msg.doAction(1);
        System.out.println("   Result: " + result);
        System.out.println("   New Status: " + msg.getCurrentStatus());
        
        // Test 4: Check total sent
        System.out.println("\n4. Total messages sent: " + Message.getTotalSent());
        
        // Test 5: Check message length
        System.out.println("\n5. Testing message length check...");
        String lengthResult = Message.checkLength("This is a short message");
        System.out.println("   Result: " + lengthResult);
        
        // Test 6: Check phone number
        System.out.println("\n6. Testing phone number check...");
        String phoneResult = Message.checkPhoneNumber("+27831234567");
        System.out.println("   Result: " + phoneResult);
        
        System.out.println("\n=========================");
        System.out.println("All tests completed!");
    }
}