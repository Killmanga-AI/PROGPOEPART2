package com.myproject;

import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {
    // ArrayLists to track different message states
    private ArrayList<String> sentMessages = new ArrayList<>();      // Stores successfully sent messages
    private ArrayList<String> disregardedMessages = new ArrayList<>(); // Stores messages that were discarded
    private ArrayList<String> storedMessages = new ArrayList<>();    // Stores messages saved for later
    private ArrayList<String> messageHashes = new ArrayList<>();     // Stores unique hashes for messages
    private ArrayList<String> messageIDs = new ArrayList<>();        // Stores unique IDs for messages
    private int totalMessagesSent = 0;                              // Counter for total sent messages

    // Validates that a message ID meets length requirements (<= 10 characters)
    public boolean checkMessageID(String messageID) {
        return messageID.length() <= 10;
    }

    // Validates recipient cell number format (starts with + followed by 10 digits)
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber.matches("^\\+\\d{10}$");
    }

    // Creates a unique hash for a message using ID, sequence number, and message content
    public String createMessageHash(String messageID, int messageNumber, String message) {
        // Split message into words
        String[] words = message.split(" ");
        // Get first word (or empty string if no words)
        String firstWord = words.length > 0 ? words[0] : "";
        // Get last word (or first word if only one word exists)
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        // Construct hash: first 2 chars of ID + message number + first/last words in uppercase
        String hash = messageID.substring(0, 2) + ":" + messageNumber + ":" +
                firstWord.toUpperCase() + lastWord.toUpperCase();
        messageHashes.add(hash);  // Store the generated hash
        return hash;
    }

    // Handles message disposition (send, disregard, or store)
    public String sentMessage(String message, String recipient, String messageID, String messageHash) {
        // Create options for message handling
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        
        // Show dialog with message handling options
        int choice = JOptionPane.showOptionDialog(null,
                "What would you like to do with this message?",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        // Process user's choice
        switch(choice) {
            case 0: // Send message
                sentMessages.add(message);
                messageIDs.add(messageID);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 1: // Disregard message
                disregardedMessages.add(message);
                return "Press 0 to delete message.";
            case 2: // Store message
                storedMessages.add(message);
                storeMessageToJSON(message, recipient, messageID, messageHash);
                return "Message successfully stored.";
            default: // No selection made
                return "No action taken.";
        }
    }

    // Generates a formatted string with all sent messages and their details
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total messages sent: ").append(totalMessagesSent).append("\n");
        
        // Append details for each sent message
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("Message ID: ").append(messageIDs.get(i)).append("\n");
            sb.append("Message Hash: ").append(messageHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(sentMessages.get(i)).append("\n");
            sb.append("Message: ").append(sentMessages.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    // Returns the total count of sent messages
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Stores message details to a JSON file for persistence
    private void storeMessageToJSON(String message, String recipient, String messageID, String messageHash) {
        try {
            // Create JSON object with message details
            JSONObject messageObj = new JSONObject();
            messageObj.put("messageID", messageID);
            messageObj.put("messageHash", messageHash);
            messageObj.put("recipient", recipient);
            messageObj.put("message", message);
            messageObj.put("status", "stored");

            // Create JSON array and add message object
            JSONArray messagesArray = new JSONArray();
            messagesArray.put(messageObj);

            // Write JSON array to file
            try (FileWriter file = new FileWriter("stored_messages.json")) {
                file.write(messagesArray.toString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generates a random 10-digit message ID
    public String generateMessageID() {
        Random rand = new Random();
        long id = rand.nextLong() % 10000000000L;  // Ensure 10-digit number
        return String.format("%010d", Math.abs(id));  // Format with leading zeros
    }
}