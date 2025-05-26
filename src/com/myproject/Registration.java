package com.myproject;

import javax.swing.JOptionPane;

public class Registration {
    public void registerUser(Login loginHandler) {
        String username = getValidInput("username",
                "Enter your username (max 5 chars, one '_'):",
                Login::isValidUsernameEnhanced,
                "Username is not correctly formatted, please ensure that your username contains exactly one underscore and is no more than five characters in length.");

        String password = getValidInput("password",
                "Enter your password (>= 8 chars, upper, lower, digit, special):",
                Login::isStrongPasswordEnhanced,
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");

        String cellNumber = getValidInput("cellphone number",
                "Enter your cellphone number (starting with country code, >= 8 digits):",
                Login::isValidCellphoneNumberInternational,
                "Cell phone number incorrectly formatted or does not contain international code.");

        loginHandler.setCredentials(username, password, cellNumber);
        JOptionPane.showMessageDialog(null, "Registration successful!");
    }

    private String getValidInput(String inputType, String prompt,
                                 java.util.function.Function<String, Boolean> validator,
                                 String errorMessage) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, prompt);
            if (input == null) {
                JOptionPane.showMessageDialog(null, inputType + " input cancelled.");
                System.exit(0);
            }
            if (validator.apply(input)) {
                JOptionPane.showMessageDialog(null, inputType + " successfully captured!");
                return input;
            }
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}