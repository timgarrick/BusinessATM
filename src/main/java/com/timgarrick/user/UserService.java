package com.timgarrick.user;

import com.timgarrick.account.Account;
import com.timgarrick.account.AccountService;
import com.timgarrick.application.ApplicationService;
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
}
