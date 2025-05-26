package com.myproject;

import javax.swing.JOptionPane;
import java.util.regex.*;

public class Login {
    // Class fields to store user credentials
    private String username;
    private String password;
    private String cellNumber;

    // Main method - entry point of the program
    public static void main(String[] args) {
        // Username validation loop
        String username;
        boolean usernameValid = false;

        while (!usernameValid) {
            // Prompt user for username input
            username = JOptionPane.showInputDialog(null, "Enter your username (max 5 chars, one '_'):");
            
            // Handle cancellation case
            if (username == null) {
                JOptionPane.showMessageDialog(null, "Username input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Validate username format
            if (isValidUsernameEnhanced(username)) {
                JOptionPane.showMessageDialog(null, "Username successfully captured!");
                usernameValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username format. Please try again.", "Username Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Cellphone number validation loop
        String cellphoneNumber;
        boolean cellphoneValid = false;

        while (!cellphoneValid) {
            // Prompt user for cellphone number
            cellphoneNumber = JOptionPane.showInputDialog(null, "Enter your cellphone number (starting with country code, >= 8 digits):");
            
            // Handle cancellation case
            if (cellphoneNumber == null) {
                JOptionPane.showMessageDialog(null, "Cellphone number input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Validate international cellphone format
            if (isValidCellphoneNumberInternational(cellphoneNumber)) {
                JOptionPane.showMessageDialog(null, "Cellphone number successfully added!");
                cellphoneValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid cellphone number format. Please try again.", "Cellphone Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Password validation loop
        String password;
        boolean passwordValid = false;

        while (!passwordValid) {
            // Prompt user for password
            password = JOptionPane.showInputDialog(null, "Enter your password (>= 8 chars, upper, lower, digit, special):");
            
            // Handle cancellation case
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Password input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Validate password complexity
            if (isStrongPasswordEnhanced(password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured!");
                JOptionPane.showMessageDialog(null, "All data validated successfully (password not displayed for security).", "Success", JOptionPane.INFORMATION_MESSAGE);
                passwordValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Password does not meet the complexity requirements. Please try again.", "Password Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Validates username format: exactly one underscore and max 5 characters
    public static boolean isValidUsernameEnhanced(String username) {
        long underscoreCount = username.chars().filter(ch -> ch == '_').count();
        return underscoreCount == 1 && username.length() <= 5;
    }

    // Validates international cellphone number format
    // Allows digits and '+' sign, minimum 8 characters
    public static boolean isValidCellphoneNumberInternational(String cellphoneNumber) {
        return cellphoneNumber != null && cellphoneNumber.matches("^[0-9+]+$") && cellphoneNumber.length() >= 8;
    }

    // Validates password meets complexity requirements:
    // At least 8 characters with uppercase, lowercase, digit, and special character
    public static boolean isStrongPasswordEnhanced(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

        // Check each character in password to determine character types present
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else {
                Pattern specialPattern = Pattern.compile("[^a-zA-Z0-9]");
                Matcher specialMatcher = specialPattern.matcher(String.valueOf(c));
                if (specialMatcher.find()) hasSpecial = true;
            }
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    // Returns appropriate login status message
    public String returnLoginStatus(boolean isLoggedIn, String firstName, String lastName) {
        return isLoggedIn ?
                "Welcome " + firstName + "," + lastName + " it is great to see you again." :
                "Username or password incorrect, please try again.";
    }

    // Authenticates user credentials
    public boolean loginUser(String username, String password) {
        return this.username != null && this.username.equals(username) &&
                this.password != null && this.password.equals(password);
    }

    // Sets user credentials in the class fields
    public void setCredentials(String username, String password, String cellNumber) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }
}