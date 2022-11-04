package com.timgarrick.application;

import com.timgarrick.account.AccountLogic;
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

            switch (UserLogic.initialUserSelection()) {
                case 1 -> UserLogic.loginUser();
                case 2 -> UserLogic.createNewAccount();
                case 3 -> applicationIsRunning = false;
                default -> userInterface.outputString("Unknown menu item, please retry");
            }

            while(currentlyLoggedInUser != null)
            {
                switch (UserLogic.loggedInUserSelection()) {
                    case 1 -> UserLogic.manageUserAccount();
                    case 2 -> AccountLogic.createNewAccount();
                    case 3 -> UserLogic.manageExistingAccount();
                    case 4 -> currentlyLoggedInUser = null;
                    default -> UserInterface.outputString("Unknown menu item, please retry");
                }



            }
        }
    }
}
