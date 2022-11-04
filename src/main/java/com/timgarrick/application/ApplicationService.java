package com.timgarrick.application;

import com.timgarrick.account.AccountService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;

public class ApplicationService {
    public static String bankName;
    public static User currentlyLoggedInUser = null;
    private boolean applicationIsRunning = true;
    final static UserService userService = new UserService();
    final static AccountService accountService = new AccountService();
    public final static UserInterface userInterface = new UserInterface();

    public ApplicationService(String bankName) {
        this.bankName = bankName;
    }

    public void run() {

        userService.createUser(new User("Test", "test", "test"));
        userService.createUser(new User("TeSt2", "test", "test"));

        while(applicationIsRunning) {

            UserLogic.welcomeUser();

            switch (initialUserSelection()) {
                case 1 -> loginUser();
                case 2 -> createNewAccount();
                case 3 -> applicationIsRunning = false;
                default -> userInterface.outputString("Unknown menu item, please retry");
            }

            while(currentlyLoggedInUser != null)
            {
                switch (loggedInUserSelection()) {
                    case 1 -> manageUserAccount();
                    case 2 -> createNewAccount();
                    case 3 -> manageExistingAccount();
                    case 4 -> currentlyLoggedInUser = null;
                    default -> userInterface.outputString("Unknown menu item, please retry");
                }

            }



        }
    }

    private void manageExistingAccount() {
    }

    private void manageUserAccount() {
    }

    private int loggedInUserSelection() {
        userInterface.outputString("Please choose a selection");
        userInterface.outputString("1 - Manage user account");

        userInterface.outputString("2 - Create new account");
        userInterface.outputString("3 - Manage existing accounts");

        userInterface.outputString("4 - Log out and exit to main menu");
        userInterface.outputString("5 - Quit Application");

        return (int) userInterface.inputNumber();

    }

    private boolean createNewAccount() {
        String newUsername = userInterface.inputString("Enter the username");

        if(userService.findUser(newUsername) != null) {
            userInterface.outputString("This username already exists");
            return false;
        }

        
        String newUseremail = userInterface.inputString("Enter the email address");
        String newPassword1 = userInterface.inputString("Enter the password for this account");
        String newPassword2 = userInterface.inputString("Enter the password again");

        if (newPassword1.equals(newPassword2)) {

            userService.createUser(new User(newUsername, newPassword1, newUseremail));
            userInterface.outputString("User " + newUsername + " successfully created");
            return true;

        } else {
            userInterface.outputString("Passwords do not match. Please try again.");
            return false;

        }


    }

    private int initialUserSelection() {
        userInterface.outputString("Please choose a selection");
        userInterface.outputString("1 - Login to user account");
        userInterface.outputString("2 - Create new account");
        userInterface.outputString("3 - Quit application");

        //userInterface.inputNumber("");

        return (int) userInterface.inputNumber();
    }

    private boolean loginUser() {
        String username = userInterface.inputString("Please enter your username or ID");
        String password = userInterface.inputString("Please enter the password for this account");
        if (validateUserAgainstUserList(username, password)) {
            userInterface.outputString("Logged into account " + currentlyLoggedInUser.getUsername() + " successfully");

            return true;
        }

        userInterface.outputString("Unable to validate your user account.");
        return false;

    }

    private boolean validateUserAgainstUserList(String username, String password) {



        for (User user : userService.getUserList())
        {
            if(username.equalsIgnoreCase(user.getUsername()))
            {
                if(password.equals(user.getPassword())){
                    currentlyLoggedInUser = user;
                    return true;
                }
            }
        }


        return false;

    }
}
