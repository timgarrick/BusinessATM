package com.timgarrick.application;

import com.timgarrick.account.AccountLogic;
import com.timgarrick.account.AccountService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserLogic;
import com.timgarrick.user.UserService;

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

        //Need to create an ATM entity to maintain transaction preservance

        User tim = new User("tim", "tim", "tim");
        User roya = new User("roya", "roya", "roya");
/*        Account testJoint1 = new Account("testJoint1",AccountType.CLIENT,tim);
        Account testAccount2 = new Account("testAccount2",AccountType.CLIENT,tim);
        Account testAccount3 = new Account("testAccount3",AccountType.CLIENT,tim);*/

        UserService.createUser(tim);
        UserService.createUser(roya);

/*        AccountService.createAccount(testJoint1);
        AccountService.createAccount(testAccount2);
        AccountService.createAccount(testAccount3);*/


        while(applicationIsRunning) {

            UserLogic.welcomeUser();

            switch (UserLogic.initialUserSelection()) {
                case 0 -> applicationIsRunning = false;
                case 1 -> UserLogic.loginUser();
                case 2 -> UserLogic.createNewUserAccount();
            }

            while(currentlyLoggedInUser != null)
            {
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
