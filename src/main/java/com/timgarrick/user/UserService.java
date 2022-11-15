package com.timgarrick.user;

import com.timgarrick.account.Account;
import com.timgarrick.account.AccountService;
import com.timgarrick.application.ApplicationService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.usermessage.UserMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UserService {

    static List<User> userList = new ArrayList<>();


    public static User createUser(User user) {
        user.setUserID(userList.size()+1);
        userList.add(user);
        return userList.get((userList.size()-1));

    }

    public static List<User> getUserList() {
        if (userList.size()>0) {
            return userList;
        } else {
            return null;
        }
    }

    public static User findUser(String name) {
        for (User user: userList) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                return user;
            }
        }

        return null;
    }

    public static void refreshUserAccountList() {

        ApplicationService.currentlyLoggedInUser.setListOfPrimaryAccounts(null);
        ApplicationService.currentlyLoggedInUser.setListOfSecondaryAccounts(null);

        List<Account> newPrimaryAccountList = new ArrayList<>();
        List<Account> newSecondaryAccountList = new ArrayList<>();

        for (Account account: AccountService.getAllAccounts()) {
            if (account.getPrimaryOwner() != null && account.getPrimaryOwner().equals(ApplicationService.currentlyLoggedInUser)) {
                newPrimaryAccountList.add(account);
            }

            if (account.getSecondaryOwner() != null && account.getSecondaryOwner().equals(ApplicationService.currentlyLoggedInUser)) {
                newSecondaryAccountList.add(account);
            }
        }

        ApplicationService.currentlyLoggedInUser.setListOfPrimaryAccounts(newPrimaryAccountList);
        ApplicationService.currentlyLoggedInUser.setListOfSecondaryAccounts(newSecondaryAccountList);

    }

    public static List<UserMessage> getFlaggedMessages() {
        List<UserMessage> flaggedMessages = new Stack<>();

        for (UserMessage userMessage:ApplicationService.currentlyLoggedInUser.getListOfUserMessages())
        {
            if (userMessage.isActive())
            {
                flaggedMessages.add(userMessage);
            }
        }

        return flaggedMessages;

    }

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

        String username = UserInterface.inputString("Please enter your username or ID");
        String password = UserInterface.inputString("Please enter the password for this account");
        if (validateUserAgainstUserList(username, password)) {
            UserInterface.outputString("Logged into account " + ApplicationService.currentlyLoggedInUser.getUsername() + " successfully");
            refreshUserAccountList();
            return true;
        }

        UserInterface.outputString("Unable to validate your user account.");
        return false;

    }

    public static boolean validateUserAgainstUserList(String username, String password) {

        if (getUserList() == null) {
            return false;
        }

        for (User user : getUserList())
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

        if(findUser(newUsername) != null) {
            UserInterface.outputString("This username already exists");
            return false;
        }

        String newUseremail = UserInterface.inputString("Enter the email address");
        String newPassword1 = UserInterface.inputString("Enter the password for this account");
        String newPassword2 = UserInterface.inputString("Enter the password again");

        if (newPassword1.equals(newPassword2)) {
            createUser(new User(newUsername, newPassword1, newUseremail));
            UserInterface.outputString("User " + newUsername + " successfully created");
            return true;
        } else {
            UserInterface.outputString("Passwords do not match. Please try again.");
            return false;
        }
    }
}
