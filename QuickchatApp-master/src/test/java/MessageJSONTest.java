package com.mycompany.quickchartpart1;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageJSONTest {
    
    @Test
    public void testMessageCanBeCreated() {
        Message msg = new Message("+27731234567", "Hello World");
        assertNotNull("Message should be created", msg);
    }
    
    @Test
    public void testMessageIdIsTenDigits() {
        Message msg = new Message("+27731234567", "Hello World");
        String id = msg.getIdNumber();
        assertEquals("Message ID should be 10 digits", 10, id.length());
    }
    
    @Test
    public void testMessageIdContainsOnlyNumbers() {
        Message msg = new Message("+27731234567", "Hello World");
        String id = msg.getIdNumber();
        boolean onlyNumbers = true;
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) < '0' || id.charAt(i) > '9') {
                onlyNumbers = false;
                break;
            }
        }
        assertTrue("Message ID should contain only numbers", onlyNumbers);
    }
    
    @Test
    public void testSecurityHashHasColon() {
        Message msg = new Message("+27731234567", "Hello World");
        String hash = msg.getSecurityCode();
        assertTrue("Hash should contain a colon", hash.contains(":"));
    }
    
    @Test
    public void testSecurityHashIsUppercase() {
        Message msg = new Message("+27731234567", "Hello World");
        String hash = msg.getSecurityCode();
        assertEquals("Hash should be uppercase", hash, hash.toUpperCase());
    }
    
    @Test
    public void testSentStatusAfterDoAction1() {
        Message msg = new Message("+27731234567", "Test");
        msg.doAction(1);
        assertEquals("Status should be sent", "sent", msg.getCurrentStatus());
    }
    
    @Test
    public void testStoredStatusAfterDoAction3() {
        Message msg = new Message("+27731234567", "Test");
        msg.doAction(3);
        assertEquals("Status should be stored", "stored", msg.getCurrentStatus());
    }
    
    @Test
    public void testDiscardedStatusAfterDoAction2() {
        Message msg = new Message("+27731234567", "Test");
        msg.doAction(2);
        assertEquals("Status should be discarded", "discarded", msg.getCurrentStatus());
    }
    
    @Test
    public void testTotalSentCountIncreases() {
        int before = Message.getTotalSent();
        
        Message msg1 = new Message("+27731234567", "First");
        msg1.doAction(1);
        
        Message msg2 = new Message("+27731234567", "Second");
        msg2.doAction(1);
        
        int after = Message.getTotalSent();
        assertEquals("Total sent should increase by 2", before + 2, after);
    }
    
    @Test
    public void testCheckLengthShortMessage() {
        String result = Message.checkLength("Short message");
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    public void testCheckLengthLongMessage() {
        String longMsg = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        String result = Message.checkLength(longMsg);
        assertTrue("Should say message exceeds limit", result.contains("exceeds 250"));
    }
    
    @Test
    public void testCheckPhoneNumberValid() {
        String result = Message.checkPhoneNumber("+27831234567");
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    public void testCheckPhoneNumberInvalidNoPlus() {
        String result = Message.checkPhoneNumber("0831234567");
        assertTrue("Should say incorrectly formatted", result.contains("incorrectly formatted"));
    }
    
    @Test
    public void testSequenceNumberIncreases() {
        Message first = new Message("+27731234567", "First");
        Message second = new Message("+27731234567", "Second");
        
        int firstSeq = first.getCountNumber();
        int secondSeq = second.getCountNumber();
        
        assertEquals("Sequence should increase by 1", firstSeq + 1, secondSeq);
    }
    
    @Test
    public void testShowDetailsContainsAllInfo() {
        Message msg = new Message("+27731234567", "Test message");
        String details = msg.showAllDetails();
        assertTrue("Should contain Message ID", details.contains("Message ID:"));
        assertTrue("Should contain Message Hash", details.contains("Message Hash:"));
        assertTrue("Should contain Recipient", details.contains("Recipient:"));
        assertTrue("Should contain Message", details.contains("Message:"));
    }
    
    @Test
    public void testHashFormatCorrect() {
        Message msg = new Message("+27731234567", "Hello my friend");
        String hash = msg.getSecurityCode();
        // Format should be like: 12:1:HELLOFRIEND
        boolean hasCorrectFormat = hash.matches("\\d{2}:\\d+:[A-Z]+");
        assertTrue("Hash format should be XX:X:WORDS", hasCorrectFormat);
    }
}
