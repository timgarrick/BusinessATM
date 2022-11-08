package com.timgarrick.account;

import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.user.User;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int AccountID;
    private String accountName;
    private AccountType accountType;
    private User primaryOwner;
    private User secondaryOwner;
    private double balance;
    private List<Transaction> accountTransactions;

    public Account(String accountName, AccountType accountType, User primaryUser) {
        this.primaryOwner = primaryUser;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = 0;
        this.accountTransactions = new ArrayList<>();
    }



    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public User getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(User primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public User getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(User secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public double getBalance() {
        return balance;
    }

    public double getBalanceIncludingOverdraft() {
        return accountType.getAccountOverdraft() + getBalance();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(List<Transaction> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }

/*    public void updateBalance() {
        for (Transaction transaction:accountTransactions) {

            if (transaction.getSourceAccount().getAccountID() == this.AccountID)) {
                balance+= transaction.getAccountTransaction();
            }

        }
    }*/

    @Override
    public String toString() {
        String secondaryOwnerName;
        if(secondaryOwner == null) {
            secondaryOwnerName = "None";
        } else {
            secondaryOwnerName = this.getSecondaryOwner().getUsername();
        }

        return "ID: " + AccountID +
                ", Name: " + accountName +
                ", Type: " + accountType.getAccountName() +
                ", Primary Owner: " + primaryOwner.getUsername() +
                ", Secondary Owner: " + secondaryOwnerName +
                ", Balance: " + balance;
    }

    public void updateBalanceAfterTransaction() {
        this.balance += accountTransactions.get(accountTransactions.size()-1).getAccountTransaction();
    }
}
