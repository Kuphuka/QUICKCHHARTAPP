package com.mycompany.quickchartpart1;

public class TestMessage {
    public static void main(String[] args) {
        
        System.out.println("Testing Message Class...");
        System.out.println("=========================");
        
        // Test 1: Check message length
        System.out.println("\nTest 1: Message length check");
        String result1;
        result1 = Message.checkLength("Hello, this is a short message");
        System.out.println("Result: " + result1);
        
        // Test 2: Check phone number
        System.out.println("\nTest 2: Phone number check");
        String result2 = Message.checkPhoneNumber("+27831234567");
        System.out.println("Result: " + result2);
        
        // Test 3: Create a message
        System.out.println("\nTest 3: Create a new message");
        Message myMsg = new Message("+27731234567", "Hello my friend");
        System.out.println("Message created!");
        System.out.println("Message ID: " + myMsg.getIdNumber());
        System.out.println("Sequence Number: " + myMsg.getCountNumber());
        System.out.println("Security Code: " + myMsg.getSecurityCode());
        
        // Test 4: Send the message
        System.out.println("\nTest 4: Send the message");
        String result3 = myMsg.doAction(1);
        System.out.println("Result: " + result3);
        System.out.println("Status: " + myMsg.getCurrentStatus());
        
        // Test 5: Show all details
        System.out.println("\nTest 5: Show message details");
        System.out.println(myMsg.showAllDetails());
        
        System.out.println("\n=========================");
        System.out.println("All tests completed!");
    }
}