package com.timgarrick.application;

import com.timgarrick.user.User;
import com.timgarrick.user.UserService;

public class UserLogic {

    static void welcomeUser() {

        UserInterface.outputString("Banking with "
                + ApplicationService.bankName+ ". Welcome!");
        if (ApplicationService.currentlyLoggedInUser == null) {
            UserInterface.outputString("You are not currently logged in.");
        } else {
            UserInterface.outputString("You are currently logged in as "
                            + ApplicationService.currentlyLoggedInUser.getUsername());
        }

    }

    static void manageUserAccount() {
        boolean manageUserAccountMenuOpen = true;

        while (manageUserAccountMenuOpen) {
            UserInterface.outputString("Please choose a selection");
            UserInterface.outputString("1 - Change password");
            UserInterface.outputString("2 - Change email address");
            UserInterface.outputString("3 - Exit to main menu");

            switch ((int) UserInterface.inputNumber()) {
                case 1 -> setUserPassword();
                case 2 -> setUserEmail();
                case 3 -> manageUserAccountMenuOpen = false;
            }

        }
    }

    static int loggedInUserSelection() {
        UserInterface.outputString("Please choose a selection");
        UserInterface.outputString("1 - Manage user account");

        UserInterface.outputString("2 - Create new account");
        UserInterface.outputString("3 - Manage existing accounts");

        UserInterface.outputString("4 - Log out and exit to main menu");
        UserInterface.outputString("5 - Quit Application");

        return (int) UserInterface.inputNumber();

    }

    static void manageExistingAccount() {
    }

    static int initialUserSelection() {
        UserInterface.outputString("Please choose a selection");
        UserInterface.outputString("1 - Login to user account");
        UserInterface.outputString("2 - Create new account");
        UserInterface.outputString("3 - Quit application");

        //userInterface.inputNumber("");

        return (int) UserInterface.inputNumber();
    }

    private static void setUserEmail() {
        UserInterface.outputString("Your current email address is " + ApplicationService.currentlyLoggedInUser.getEmail());
        ApplicationService.currentlyLoggedInUser.setEmail(UserInterface.inputString("Enter new email address"));
        UserInterface.outputString("Email address has been updated.");
    }

    private static void setUserPassword() {
        UserInterface.outputString("Your current email address is " + ApplicationService.currentlyLoggedInUser.getPassword());
        String password1 = UserInterface.inputString("Enter new password");
        String password2 = UserInterface.inputString("Enter password again");

        if(password1.equals(password2)) {
            ApplicationService.currentlyLoggedInUser.setPassword(password1);
            UserInterface.outputString("Password has been updated");
        } else {
            UserInterface.outputString("Passwords do not match");
        }
    }

    static boolean loginUser() {
        String username = UserInterface.inputString("Please enter your username or ID");
        String password = UserInterface.inputString("Please enter the password for this account");
        if (validateUserAgainstUserList(username, password)) {
            UserInterface.outputString("Logged into account " + ApplicationService.currentlyLoggedInUser.getUsername() + " successfully");

            return true;
        }

        UserInterface.outputString("Unable to validate your user account.");
        return false;

    }

    private static boolean validateUserAgainstUserList(String username, String password) {



        for (User user : UserService.getUserList())
        {
            if(username.equalsIgnoreCase(user.getUsername()))
            {
                if(password.equals(user.getPassword())){
                    ApplicationService.currentlyLoggedInUser = user;
                    return true;
                }
            }
        }


        return false;

    }

    static boolean createNewAccount() {
        String newUsername = UserInterface.inputString("Enter the username");

        if(UserService.findUser(newUsername) != null) {
            UserInterface.outputString("This username already exists");
            return false;
        }


        String newUseremail = UserInterface.inputString("Enter the email address");
        String newPassword1 = UserInterface.inputString("Enter the password for this account");
        String newPassword2 = UserInterface.inputString("Enter the password again");

        if (newPassword1.equals(newPassword2)) {

            UserService.createUser(new User(newUsername, newPassword1, newUseremail));
            UserInterface.outputString("User " + newUsername + " successfully created");
            return true;

        } else {
            UserInterface.outputString("Passwords do not match. Please try again.");
            return false;

        }


    }


}