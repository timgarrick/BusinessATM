package com.timgarrick.application;

import com.timgarrick.account.AccountService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;
import com.timgarrick.user.usermessage.UserMessageService;


public class ApplicationService {
    public static String bankName;
    public static User currentlyLoggedInUser = null;
    private boolean applicationIsRunning = true;
    final static UserService userService = new UserService();
    final static AccountService accountService = new AccountService();
    final static UserInterface userInterface = new UserInterface();

    public ApplicationService(String bankName) {
        ApplicationService.bankName = bankName;
    }

    public void run() {

        //MainWindow.showWindow();


        User tim = new User("tim", "tim", "tim");
        User roya = new User("roya", "roya", "roya");

        UserService.createUser(tim);
        UserService.createUser(roya);


        while(applicationIsRunning) {

            UserService.welcomeUser();

            switch (UserService.initialUserSelection()) {
                case 0 -> applicationIsRunning = false;
                case 1 -> UserService.loginUser();
                case 2 -> UserService.createNewUserAccount();
            }


            while(currentlyLoggedInUser != null)
            {

                while (UserService.getFlaggedMessages().size() > 0) {
                    UserInterface.outputString("You have a action to process on your account");
                    UserMessageService.processMessage(UserService.getFlaggedMessages().remove(0));
                }

                switch (UserService.loggedInUserSelection()) {
                    case 0 -> currentlyLoggedInUser = null;
                    case 1 -> UserService.manageUserAccount();
                    case 2 -> AccountService.createNewAccount();
                    case 3 -> AccountService.manageExistingAccount();
                }
            }
        }
    }
}
