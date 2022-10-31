package com.timgarrick.application;

import com.timgarrick.account.AccountService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;

public class ApplicationService {
    private String bankName;
    private User currentlyLoggedInUser = null;
    private boolean applicationIsRunning = true;
    private final UserService userService;
    private final AccountService accountService;
    private final UserInterface userInterface;

    public ApplicationService(String bankName) {
        this.bankName = bankName;
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.userInterface = new UserInterface();
    }

    public void run() {

        userService.createUser(new User("Test", "test", "test"));
        userService.createUser(new User("TeSt2", "test", "test"));

        while(applicationIsRunning) {
            welcomeUser();

            initialUserSelection();

            userInterface.clear();




        }
    }

    private void initialUserSelection() {
        userInterface.outputString("Please choose a selection");
        userInterface.outputString("1 - Login to account");
        userInterface.outputString("2 - Create new account");
        userInterface.outputString("3 - Quit app");

        userInterface.inputNumber("");
    }

    private void welcomeUser() {
        userInterface.outputString("Banking with " + bankName + ". Welcome!");
        if(currentlyLoggedInUser == null) {
            userInterface.outputString("You are not currently logged in.");
        } else {
            userInterface.outputString("You are currently logged in as " + currentlyLoggedInUser.getUsername());
        }

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
