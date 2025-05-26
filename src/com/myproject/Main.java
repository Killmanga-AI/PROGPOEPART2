package com.myproject;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Login loginHandler = new Login();
        Registration registration = new Registration();
        Message messageHandler = new Message();

        // com.myproject.Registration
        registration.registerUser(loginHandler);

        // com.myproject.Login
        if (performLogin(loginHandler)) {
            showMainMenu(loginHandler, messageHandler);
        }
    }

    private static boolean performLogin(Login loginHandler) {
        String username = JOptionPane.showInputDialog("Enter your username to login:");
        if (username == null) return false;

        String password = JOptionPane.showInputDialog("Enter your password:");
        if (password == null) return false;

        boolean isLoggedIn = loginHandler.loginUser(username, password);
        String firstName = "User"; // Would normally come from user profile
        String lastName = "Name";
        JOptionPane.showMessageDialog(null,
                loginHandler.returnLoginStatus(isLoggedIn, firstName, lastName));
        return isLoggedIn;
    }

    private static void showMainMenu(Login loginHandler, Message messageHandler) {
        boolean quit = false;
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat");

        while (!quit) {
            String[] options = {"Send Messages", "Show recently sent messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Please select an option:",
                    "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch(choice) {
                case 0: sendMessages(messageHandler); break;
                case 1: JOptionPane.showMessageDialog(null, "Coming Soon"); break;
                case 2: quit = true; break;
            }
        }
    }

    private static void sendMessages(Message messageHandler) {
        String numMessagesStr = JOptionPane.showInputDialog("How many messages would you like to send?");
        try {
            int numMessages = Integer.parseInt(numMessagesStr);
            for (int i = 0; i < numMessages; i++) {
                sendSingleMessage(messageHandler, i + 1);
            }
            JOptionPane.showMessageDialog(null, messageHandler.printMessages());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number");
        }
    }

    private static void sendSingleMessage(Message messageHandler, int messageNumber) {
        String recipient = JOptionPane.showInputDialog("Enter recipient's cell phone number:");
        if (!messageHandler.checkRecipientCell(recipient)) {
            JOptionPane.showMessageDialog(null, "Invalid cellphone format");
            return;
        }

        String message = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
        if (message.length() > 250) {
            JOptionPane.showMessageDialog(null, "com.myproject.Message too long");
            return;
        }

        String messageID = messageHandler.generateMessageID();
        String messageHash = messageHandler.createMessageHash(messageID, messageNumber, message);

        String result = messageHandler.sentMessage(message, recipient, messageID, messageHash);
        JOptionPane.showMessageDialog(null, result);

        if (result.equals("com.myproject.Message successfully sent.")) {
            String details = String.format(
                    "com.myproject.Message ID: %s\ncom.myproject.Message Hash: %s\nRecipient: %s\ncom.myproject.Message: %s",
                    messageID, messageHash, recipient, message);
            JOptionPane.showMessageDialog(null, details);
        }
    }
}