package com.timgarrick.account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static List<Account> allAccounts = new ArrayList<>();

    static boolean createAccount(Account account) {
        account.setAccountID(allAccounts.size()+1);
        allAccounts.add(account);
        return true;
    }

    public static List<Account> getAllAccounts() {
        return allAccounts;
    }

    public static void deleteAccount(int accountID) {
        allAccounts.remove(findAccountByID(accountID));
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
