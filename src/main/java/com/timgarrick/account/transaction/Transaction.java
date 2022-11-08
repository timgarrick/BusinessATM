package com.timgarrick.account.transaction;

import com.timgarrick.account.Account;
import com.timgarrick.user.User;

import java.util.Date;
import java.util.List;

public class Transaction {
    private final long transactionID;
    private final Account sourceAccount;
    private final Account targetAccount;
    private final User transactionOwner;
    private final double accountTransaction;
    private final Date accountTransactionDate;
    private final TransactionType transactionType;
    private final boolean completedTransaction;

    public Transaction(List<Transaction> accountTransactionList,
                       Account sourceAccount,
                       Account targetAccount,
                       User primaryUser,
                       double accountTransaction,
                       TransactionType transactionType,
                       boolean completedTransaction) {

        this.transactionOwner = primaryUser;
        this.completedTransaction = completedTransaction;

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

    public long getTransactionID() {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", sourceAccount=" + sourceAccount +
                ", targetAccount=" + targetAccount +
                ", transactionOwner=" + transactionOwner +
                ", accountTransaction=" + accountTransaction +
                ", accountTransactionDate=" + accountTransactionDate +
                '}';
    }
}
