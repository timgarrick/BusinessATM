package com.timgarrick.account.transaction;

import com.timgarrick.account.Account;
import com.timgarrick.user.User;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private static final List<Transaction> allTransactionList = new ArrayList<>();

    public static Transaction createNewTransferTransaction(User transactionOwner,
                                                           Account sourceAccount, Account targetAccount, double transaction) {

        Transaction newTransaction = new Transaction(allTransactionList,
                sourceAccount, targetAccount, transactionOwner, transaction, TransactionType.TRANSFER, true);


        allTransactionList.add(newTransaction);
        sourceAccount.getAccountTransactions().add(newTransaction);
        sourceAccount.updateBalanceAfterTransaction();
        targetAccount.getAccountTransactions().add(newTransaction);
        targetAccount.updateBalanceAfterTransaction();

        return newTransaction;

    }


    public static void createNewDepositTransaction(User transactionOwner,
                                                   Account targetAccount,
                                                   double transaction) {
        Transaction depositTransaction = new Transaction(allTransactionList, null, targetAccount,
                                                        transactionOwner, transaction, TransactionType.DEPOSIT, true);

        allTransactionList.add(depositTransaction);
        targetAccount.getAccountTransactions().add(depositTransaction);
        targetAccount.updateBalanceAfterTransaction();

    }

    public static void createNewWithdrawTransaction(User transactionOwner,
                                                   Account sourceAccount,
                                                   double transaction) {
        Transaction withdrawTransaction = new Transaction(allTransactionList, sourceAccount, null,
                transactionOwner, transaction, TransactionType.WITHDRAWAL, false);

        allTransactionList.add(withdrawTransaction);
        sourceAccount.getAccountTransactions().add(withdrawTransaction);
        sourceAccount.updateBalanceAfterTransaction();

    }
}
