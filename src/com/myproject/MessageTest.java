package com.myproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message messageHandler;
    private String testRecipient = "+27831234567";
    private String testMessage = "Hi there, this is a test message";
    private String longMessage;

    @BeforeEach
    public void setUp() {
        messageHandler = new Message();
        // Create a message that's exactly 250 characters
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 25; i++) sb.append("1234567890");
        longMessage = sb.toString();
    }

    @Test
    public void testMessageLength_Success() {
        String result = messageHandler.checkMessageLength(longMessage);
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLength_Failure() {
        String tooLongMessage = longMessage + "X";
        String result = messageHandler.checkMessageLength(tooLongMessage);
        assertTrue(result.contains("Message exceeds 250 characters by 1"));
    }

    @Test
    public void testRecipientNumber_Valid() {
        assertTrue(messageHandler.checkRecipientCell(testRecipient));
    }

    @Test
    public void testRecipientNumber_Invalid() {
        assertFalse(messageHandler.checkRecipientCell("0831234567")); // No international code
        assertFalse(messageHandler.checkRecipientCell("+278312345678")); // Too long
        assertFalse(messageHandler.checkRecipientCell("+27abc123456")); // Contains letters
    }

    @Test
    public void testMessageHash_Creation() {
        String messageID = messageHandler.generateMessageID();
        String hash = messageHandler.createMessageHash(messageID, 1, "Hi Mike, can you join us for dinner tonight");
        assertEquals(messageID.substring(0, 2) + ":1:HITONIGHT", hash);
    }

    @Test
    public void testMessageID_Generation() {
        String messageID = messageHandler.generateMessageID();
        assertNotNull(messageID);
        assertEquals(10, messageID.length());
        assertTrue(messageID.matches("\\d+"));
    }

    @Test
    public void testMessageOptions_Send() {
        String messageID = messageHandler.generateMessageID();
        String hash = messageHandler.createMessageHash(messageID, 1, testMessage);
        String result = messageHandler.sentMessage(testMessage, testRecipient, messageID, hash, 0);

        assertEquals("Message successfully sent.", result);
        assertEquals(1, messageHandler.returnTotalMessages());
    }

    @Test
    public void testMessageOptions_Store() {
        String messageID = messageHandler.generateMessageID();
        String hash = messageHandler.createMessageHash(messageID, 1, testMessage);
        String result = messageHandler.sentMessage(testMessage, testRecipient, messageID, hash, 2);

        assertEquals("Message successfully stored.", result);
        assertEquals(0, messageHandler.returnTotalMessages());
    }

    @Test
    public void testMessageOptions_Discard() {
        String messageID = messageHandler.generateMessageID();
        String hash = messageHandler.createMessageHash(messageID, 1, testMessage);
        String result = messageHandler.sentMessage(testMessage, testRecipient, messageID, hash, 1);

        assertEquals("Press 0 to delete message.", result);
        assertEquals(0, messageHandler.returnTotalMessages());
    }

    @Test
    public void testPrintMessages() {
        String id1 = messageHandler.generateMessageID();
        messageHandler.sentMessage("First message", testRecipient, id1,
                messageHandler.createMessageHash(id1, 1, "First message"), 0);

        String id2 = messageHandler.generateMessageID();
        messageHandler.sentMessage("Second message", testRecipient, id2,
                messageHandler.createMessageHash(id2, 2, "Second message"), 0);

        String output = messageHandler.printMessages();
        assertTrue(output.contains("Total messages sent: 2"));
        assertTrue(output.contains(id1));
        assertTrue(output.contains(id2));
    }
}