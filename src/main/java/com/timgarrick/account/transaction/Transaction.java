package com.timgarrick.account.transaction;

import com.timgarrick.account.Account;
import com.timgarrick.user.User;

import java.util.Date;
import java.util.List;

public class Transaction {
    private final int transactionID;
    private final Account sourceAccount;
    private final Account targetAccount;
    private final User transactionOwner;
    private final double accountTransaction;
    private final Date accountTransactionDate;
    private final TransactionType transactionType;
    private boolean pending;

    public Transaction(List<Transaction> accountTransactionList,
                       Account sourceAccount,
                       Account targetAccount,
                       User primaryUser,
                       double accountTransaction,
                       TransactionType transactionType,
                       boolean pending) {

        this.transactionOwner = primaryUser;
        this.pending = pending;

        if (accountTransactionList == null) {
            this.transactionID = 1;
        } else {
            this.transactionID = accountTransactionList.size() + 1;
        }

        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.accountTransaction = accountTransaction;
        this.transactionType = transactionType;
        this.accountTransactionDate = new Date();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public double getAccountTransaction() {
        return accountTransaction;
    }

    public User geTransactionOwner() {
        return transactionOwner;
    }

    public Date getAccountTransactionDate() {
        return accountTransactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return transactionID + ": " +
                transactionType.getFriendlyName() + " £" +
                accountTransaction + " from "+
                sourceAccount + " to "+
                targetAccount + ". Transaction requested by "+
                transactionOwner + " (" +
                accountTransactionDate + ")";
    }


}
