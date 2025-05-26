package com.myproject;

import javax.swing.JOptionPane;

public class Registration {
    
    /**
     * Main registration method that guides user through the registration process
     * @param loginHandler The Login instance that will store the user credentials
     */
    public void registerUser(Login loginHandler) {
        // Get and validate username using helper method
        String username = getValidInput("username",
                "Enter your username (max 5 chars, one '_'):",
                Login::isValidUsernameEnhanced,
                "Username is not correctly formatted, please ensure that your username contains exactly one underscore and is no more than five characters in length.");

        // Get and validate password using helper method
        String password = getValidInput("password",
                "Enter your password (>= 8 chars, upper, lower, digit, special):",
                Login::isStrongPasswordEnhanced,
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");

        // Get and validate cellphone number using helper method
        String cellNumber = getValidInput("cellphone number",
                "Enter your cellphone number (starting with country code, >= 8 digits):",
                Login::isValidCellphoneNumberInternational,
                "Cell phone number incorrectly formatted or does not contain international code.");

        // Store validated credentials in the login handler
        loginHandler.setCredentials(username, password, cellNumber);
        
        // Show success message
        JOptionPane.showMessageDialog(null, "Registration successful!");
    }

    /**
     * Helper method to get and validate user input with retry logic
     * @param inputType The type of input being collected (for messages)
     * @param prompt The dialog prompt to show the user
     * @param validator Function that validates the input
     * @param errorMessage Error message to show when validation fails
     * @return Validated user input
     */
    private String getValidInput(String inputType, 
                               String prompt,
                               java.util.function.Function<String, Boolean> validator,
                               String errorMessage) {
        // Keep trying until valid input is received
        while (true) {
            // Show input dialog to user
            String input = JOptionPane.showInputDialog(null, prompt);
            
            // Handle case where user cancels the input
            if (input == null) {
                JOptionPane.showMessageDialog(null, inputType + " input cancelled.");
                System.exit(0);  // Exit application if input is cancelled
            }
            
            // Validate input using the provided validator function
            if (validator.apply(input)) {
                // Show success message and return valid input
                JOptionPane.showMessageDialog(null, inputType + " successfully captured!");
                return input;
            }
            
            // Show error message if validation fails (loop continues)
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}