package com.timgarrick.account;

import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.account.transaction.TransactionService;
import com.timgarrick.application.ApplicationService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;

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
        account.setPrimaryOwner(null);
        account.setSecondaryOwner(null);
        account.setArchived(true);
        UserService.refreshUserAccountList();
    }

    public static void updateSecondaryUser(Account account, User user) {
        account.setSecondaryOwner(user);
        UserService.refreshUserAccountList();
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

    public static void showAccountTransactionHistory(Account account) {
        if (account.getAccountTransactions() != null) {
            for (Transaction transaction:account.getAccountTransactions()) {
                //

            }

        } else {
            UserInterface.outputString("No account transactions for this account");
        }
    }

    public boolean transferFromSourceToTarget(Account source, Account target, double balance) {
        if (balance < 0) {
            UserInterface.outputString("Balance must be greater than 0 to transfer");
            return false;
        } else if (balance <= source.getBalance()) {
            transactionService.createNewTransferTransaction(ApplicationService.currentlyLoggedInUser,source, target, balance, false);
            return true;
        } else {
            UserInterface.outputString("Unable to transfer £" + balance + " from " + source.getAccountName()
                                        + " to " + target.getAccountName());
            return false;
        }
    }

}
