package com.timgarrick.application;

import com.timgarrick.account.AccountLogic;
import com.timgarrick.account.AccountService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserLogic;
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

            UserLogic.welcomeUser();

            switch (UserLogic.initialUserSelection()) {
                case 0 -> applicationIsRunning = false;
                case 1 -> UserLogic.loginUser();
                case 2 -> UserLogic.createNewUserAccount();
            }



            while(currentlyLoggedInUser != null)
            {
                if(currentlyLoggedInUser.getListOfPrimaryAccounts().size() == 0) {
                    UserInterface.outputString("You have no primary accounts. Please create a new account.");
                    AccountLogic.createNewAccount();
                }

                while (UserService.getFlaggedMessages().size() > 0) {
                    UserInterface.outputString("You have a action to process on your account");
                    UserMessageService.processMessage(UserService.getFlaggedMessages().remove(0));
                }

                switch (UserLogic.loggedInUserSelection()) {
                    case 0 -> currentlyLoggedInUser = null;
                    case 1 -> UserLogic.manageUserAccount();
                    case 2 -> AccountLogic.createNewAccount();
                    case 3 -> AccountLogic.manageExistingAccount();
                }
            }
        }
    }
}
