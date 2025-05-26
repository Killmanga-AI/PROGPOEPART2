package com.myproject;

import javax.swing.JOptionPane;
import java.util.regex.*;

public class Login {
    private String username;
    private String password;
    private String cellNumber;

    // Your exact implementation
    public static void main(String[] args) {
        String username;
        boolean usernameValid = false;

        while (!usernameValid) {
            username = JOptionPane.showInputDialog(null, "Enter your username (max 5 chars, one '_'):");
            if (username == null) {
                JOptionPane.showMessageDialog(null, "Username input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (isValidUsernameEnhanced(username)) {
                JOptionPane.showMessageDialog(null, "Username successfully captured!");
                usernameValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username format. Please try again.", "Username Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        String cellphoneNumber;
        boolean cellphoneValid = false;

        while (!cellphoneValid) {
            cellphoneNumber = JOptionPane.showInputDialog(null, "Enter your cellphone number (starting with country code, >= 8 digits):");
            if (cellphoneNumber == null) {
                JOptionPane.showMessageDialog(null, "Cellphone number input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (isValidCellphoneNumberInternational(cellphoneNumber)) {
                JOptionPane.showMessageDialog(null, "Cellphone number successfully added!");
                cellphoneValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid cellphone number format. Please try again.", "Cellphone Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        String password;
        boolean passwordValid = false;

        while (!passwordValid) {
            password = JOptionPane.showInputDialog(null, "Enter your password (>= 8 chars, upper, lower, digit, special):");
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Password input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (isStrongPasswordEnhanced(password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured!");
                JOptionPane.showMessageDialog(null, "All data validated successfully (password not displayed for security).", "Success", JOptionPane.INFORMATION_MESSAGE);
                passwordValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Password does not meet the complexity requirements. Please try again.", "Password Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static boolean isValidUsernameEnhanced(String username) {
        long underscoreCount = username.chars().filter(ch -> ch == '_').count();
        return underscoreCount == 1 && username.length() <= 5;
    }

    // Regex pattern inspired by ChatGPT (OpenAI,2024)
    public static boolean isValidCellphoneNumberInternational(String cellphoneNumber) {
        return cellphoneNumber != null && cellphoneNumber.matches("^[0-9+]+$") && cellphoneNumber.length() >= 8;
    }

    public static boolean isStrongPasswordEnhanced(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

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

    // Added method from your requirements
    public String returnLoginStatus(boolean isLoggedIn, String firstName, String lastName) {
        return isLoggedIn ?
                "Welcome " + firstName + "," + lastName + " it is great to see you again." :
                "Username or password incorrect, please try again.";
    }

    // Additional methods needed for integration
    public boolean loginUser(String username, String password) {
        return this.username != null && this.username.equals(username) &&
                this.password != null && this.password.equals(password);
    }

    public void setCredentials(String username, String password, String cellNumber) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }
}