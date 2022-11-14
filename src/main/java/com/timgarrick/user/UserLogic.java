package com.timgarrick.user;

import com.timgarrick.account.AccountService;
import com.timgarrick.account.transaction.TransactionService;
import com.timgarrick.application.ApplicationService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.usermessage.UserMessage;

public class UserLogic {

    public static void welcomeUser() {

        UserInterface.outputString("Banking with " + ApplicationService.bankName + ". Welcome!");

        if (ApplicationService.currentlyLoggedInUser == null) {
            UserInterface.outputString("You are not currently logged in.");
        } else {
            UserInterface.outputString("You are currently logged in as "
                            + ApplicationService.currentlyLoggedInUser.getUsername());

        }

        UserInterface.outputString("");
    }

    public static void checkUserMessageFlags()
    {

        for (UserMessage activeMessage:ApplicationService.currentlyLoggedInUser.getListOfUserMessages()) {

            switch(activeMessage.getUserMessageType()) {

                case JOINT_ACCOUNT_CREATION_REQUEST -> {

                    UserInterface.outputString("Joint account request for "
                            + activeMessage.getAccount().getAccountName() + " from "
                            + activeMessage.getAccount().getPrimaryOwner() + " ("
                            + activeMessage.getAccount().getPrimaryOwner().getUsername() + ") " + " at "
                            + activeMessage.getMessageCreated());

                    if(!activeMessage.getContextMessage().isEmpty()) {
                        UserInterface.outputString(activeMessage.getContextMessage());
                    }

                    switch (UserInterface.userOptionSelection("Accept request#Decline request#Ignore request")) {
                        case 1 -> {
                            AccountService.updateSecondaryUser(activeMessage.getAccount(),
                                                                ApplicationService.currentlyLoggedInUser);
                            activeMessage.setActive(false);
                        }
                        case 2 -> {
                            activeMessage.setActive(false);
                        }
                        default -> {
                            break;
                        }
                    }


                }
                case JOINT_ACCOUNT_DELETION_REQUEST -> {

                    if (ApplicationService.currentlyLoggedInUser.equals(activeMessage.getAccount().getSecondaryOwner())) {

                        UserInterface.outputString("Joint account deletion requested for "
                                + activeMessage.getAccount().getAccountName() + " by "
                                + activeMessage.getAccount().getPrimaryOwner() + " ("
                                + activeMessage.getAccount().getPrimaryOwner().getUsername() + ") " + " at "
                                + activeMessage.getMessageCreated());

                        if (!activeMessage.getContextMessage().isEmpty()) {
                            UserInterface.outputString(activeMessage.getContextMessage());
                        }

                        switch (UserInterface.userOptionSelection("Accept request#Decline request#Ignore request")) {
                            case 1 -> {
                                AccountService.deleteAccount(activeMessage.getAccount());
                                UserService.refreshUserAccountList();
                                activeMessage.setActive(false);
                            }
                            case 2 -> {
                                activeMessage.setActive(false);
                            }
                            default -> {
                                break;
                            }
                        }
                    }


                }
                case JOINT_ACCOUNT_TRANSACTION_REQUEST -> {

                    UserInterface.outputString("Joint account transaction request "
                            + activeMessage.getAccount().getAccountName() + " by "
                            + activeMessage.getAccount().getPrimaryOwner() + " ("
                            + activeMessage.getAccount().getPrimaryOwner().getUsername() + ") " + " at "
                            + activeMessage.getMessageCreated());

                    if (!activeMessage.getContextMessage().isEmpty()) {
                        UserInterface.outputString(activeMessage.getContextMessage());
                    }

                    UserInterface.outputString(activeMessage.getTransaction().toString());

                    switch (UserInterface.userOptionSelection("Accept request#Decline request#Ignore request")) {
                        case 1 -> {
                            TransactionService.confirmTransaction(activeMessage.getTransaction());
                            activeMessage.setActive(false);
                        }
                        case 2 -> {
                            activeMessage.setActive(false);
                        }
                        default -> {
                            break;
                        }
                    }
                }
            }


        }
    }

    private static void jointAccountRequestMessageText(String joint_account_creation) {


    }

    public static void manageUserAccount() {
        boolean manageUserAccountMenuOpen = true;
        String manageUserAccountString = "Change password#Change email address";

        while (manageUserAccountMenuOpen) {

            switch (UserInterface.userOptionSelection(manageUserAccountString)) {
                case 0 -> manageUserAccountMenuOpen = false;
                case 1 -> setUserPassword();
                case 2 -> setUserEmail();
            }

        }
    }

    public static int loggedInUserSelection() {
        String loggedInUserSelectionString = "Manage user account#Create new account#Manage Existing Accounts";

        return UserInterface.userOptionSelection(loggedInUserSelectionString);
/*
                UserInterface.outputString("Please choose a selection");
        UserInterface.outputString("1 - Manage user account");

        UserInterface.outputString("2 - Create new account");
        UserInterface.outputString("3 - Manage existing accounts");

        UserInterface.outputString("4 - Log out and exit to main menu");
        UserInterface.outputString("5 - Quit Application");

        return (int) UserInterface.inputNumber();*/

    }

    static void manageExistingAccount() {
    }

    public static int initialUserSelection() {
        String initialUserSelectionString = "Login to user account#Create new user account";

        return UserInterface.userOptionSelection(initialUserSelectionString);

    }

    private static void setUserEmail() {
        UserInterface.outputString("Your current email address is " + ApplicationService.currentlyLoggedInUser.getEmail());
        ApplicationService.currentlyLoggedInUser.setEmail(UserInterface.inputString("Enter new email address"));
        UserInterface.outputString("Email address has been updated.");
    }

    private static void setUserPassword() {
        //this needs to be hashed and input password compared to stored hash password
        UserInterface.outputString("Your current password is: " + ApplicationService.currentlyLoggedInUser.getPassword());
        String password1 = UserInterface.inputString("Enter new password");
        String password2 = UserInterface.inputString("Enter password again");

        if(password1.equals(password2)) {
            ApplicationService.currentlyLoggedInUser.setPassword(password1);
            UserInterface.outputString("Password has been updated");
        } else {
            UserInterface.outputString("Passwords do not match");
        }
    }

    public static boolean loginUser() {
        //LoginBox loginBox = new LoginBox();
        //loginBox.showWindow();


        String username = UserInterface.inputString("Please enter your username or ID");
        String password = UserInterface.inputString("Please enter the password for this account");
        if (validateUserAgainstUserList(username, password)) {
            UserInterface.outputString("Logged into account " + ApplicationService.currentlyLoggedInUser.getUsername() + " successfully");
            //UserLogic.checkUserMessageFlags();
            UserService.refreshUserAccountList();
            return true;
        }

        UserInterface.outputString("Unable to validate your user account.");
        return false;

    }

    public static boolean validateUserAgainstUserList(String username, String password) {

        if (UserService.getUserList() == null) {
            return false;
        }

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

    public static boolean createNewUserAccount() {
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