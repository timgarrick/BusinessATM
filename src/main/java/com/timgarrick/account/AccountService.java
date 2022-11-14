package com.timgarrick.account;

import com.timgarrick.account.transaction.TransactionService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;
import com.timgarrick.user.usermessage.UserMessage;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static List<Account> allAccounts = new ArrayList<>();
    TransactionService transactionService;


    public static Account createAccount(Account account) {
        account.setAccountID(allAccounts.size()+1);
        allAccounts.add(account);
        return allAccounts.get(allAccounts.size()-1);
    }

    public static List<Account> getAllAccounts() {
        return allAccounts;
    }

    public static void deleteAccount(int accountID) {
        deleteAccount(findAccountByID(accountID));
    }

    public static void deleteAccount(Account account) {
        AccountLogic.activeAccount = null;
        account.setPrimaryOwner(null);
        account.setSecondaryOwner(null);
        account.setArchived(true);
        UserService.refreshUserAccountList();
    }

    public static void updateSecondaryUser(Account account, User user) {
        account.setSecondaryOwner(user);
        UserService.refreshUserAccountList();
    }

    public static boolean processJointAccountCreationRequest(UserMessage message) {
        UserInterface.outputString(message.getSentBy().friendlyName()
                + " has requested you to be joint owner of the following account:");
        UserInterface.outputString(message.getAccount().toString());
        if(!message.getContextMessage().isEmpty()) {
            UserInterface.outputString("With the message: ");
            UserInterface.outputString(message.getContextMessage());
        }

        return UserInterface.userOptionSelection("Accept request?") == 1;
    }

    public static boolean processJointAccountDeletionRequest(UserMessage message) {
        UserInterface.outputString(message.getSentBy().friendlyName()
                + " has requested the following account to be deleted");
        UserInterface.outputString(message.getAccount().toString());
        if(!message.getContextMessage().isEmpty()) {
            UserInterface.outputString("With the message: ");
            UserInterface.outputString(message.getContextMessage());
        }

        return UserInterface.userOptionSelection("Accept request?") == 1;
    }

    public static boolean processJointAccountTransactionRequest(UserMessage message) {
        UserInterface.outputString(message.getSentBy().friendlyName()
                + " has created the following transaction which needs your approval");
        UserInterface.outputString(message.getTransaction().toString());
        if(!message.getContextMessage().isEmpty()) {
            UserInterface.outputString("With the message: ");
            UserInterface.outputString(message.getContextMessage());
        }

        return UserInterface.userOptionSelection("Accept request?") == 1;

    }


    public void setAllAccounts(List<Account> allAccounts) {
        AccountService.allAccounts = allAccounts;
    }

    public static Account findAccountByID(int id) {
        for (Account account:allAccounts) {
            if (account.getAccountID() == id) {
                return account;
            }
        }

        return null;
    }



}
