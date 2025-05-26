package com.myproject;

import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {
    private ArrayList<String> sentMessages = new ArrayList<>();
    private ArrayList<String> disregardedMessages = new ArrayList<>();
    private ArrayList<String> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();
    private int totalMessagesSent = 0;

    public boolean checkMessageID(String messageID) {
        return messageID.length() <= 10;
    }

    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber.matches("^\\+\\d{10}$");
    }

    public String createMessageHash(String messageID, int messageNumber, String message) {
        String[] words = message.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        String hash = messageID.substring(0, 2) + ":" + messageNumber + ":" +
                firstWord.toUpperCase() + lastWord.toUpperCase();
        messageHashes.add(hash);
        return hash;
    }

    public String sentMessage(String message, String recipient, String messageID, String messageHash) {
        String[] options = {"Send com.myproject.Message", "Disregard com.myproject.Message", "Store com.myproject.Message to send later"};
        int choice = JOptionPane.showOptionDialog(null,
                "What would you like to do with this message?",
                "com.myproject.Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch(choice) {
            case 0: // Send
                sentMessages.add(message);
                messageIDs.add(messageID);
                totalMessagesSent++;
                return "com.myproject.Message successfully sent.";
            case 1: // Disregard
                disregardedMessages.add(message);
                return "Press 0 to delete message.";
            case 2: // Store
                storedMessages.add(message);
                storeMessageToJSON(message, recipient, messageID, messageHash);
                return "com.myproject.Message successfully stored.";
            default:
                return "No action taken.";
        }
    }

    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total messages sent: ").append(totalMessagesSent).append("\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("com.myproject.Message ID: ").append(messageIDs.get(i)).append("\n");
            sb.append("com.myproject.Message Hash: ").append(messageHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(sentMessages.get(i)).append("\n");
            sb.append("com.myproject.Message: ").append(sentMessages.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    private void storeMessageToJSON(String message, String recipient, String messageID, String messageHash) {
        try {
            JSONObject messageObj = new JSONObject();
            messageObj.put("messageID", messageID);
            messageObj.put("messageHash", messageHash);
            messageObj.put("recipient", recipient);
            messageObj.put("message", message);
            messageObj.put("status", "stored");

            JSONArray messagesArray = new JSONArray();
            messagesArray.put(messageObj);

            try (FileWriter file = new FileWriter("stored_messages.json")) {
                file.write(messagesArray.toString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateMessageID() {
        Random rand = new Random();
        long id = rand.nextLong() % 10000000000L;
        return String.format("%010d", Math.abs(id));
    }
}
