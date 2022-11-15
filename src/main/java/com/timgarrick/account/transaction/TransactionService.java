package com.timgarrick.account.transaction;

import com.timgarrick.account.Account;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private static final List<Transaction> allTransactionList = new ArrayList<>();

    public static Transaction createNewTransferTransaction(User transactionOwner,
                                                           Account sourceAccount,
                                                           Account targetAccount,
                                                           double transaction,
                                                           boolean pending) {

        Transaction transferTransaction = new Transaction(allTransactionList,
                sourceAccount, targetAccount, transactionOwner, transaction, TransactionType.TRANSFER, pending);


        return confirmTransaction(transferTransaction);

    }


    public static Transaction createNewDepositTransaction(User transactionOwner,
                                                          Account targetAccount,
                                                          double transaction,
                                                          boolean pending) {

        Transaction depositTransaction = new Transaction(allTransactionList,null, targetAccount,
                                                        transactionOwner, transaction, TransactionType.DEPOSIT,false);


        return confirmTransaction(depositTransaction);
    }

    public static Transaction createNewWithdrawTransaction(User transactionOwner,
                                                           Account sourceAccount,
                                                           double transaction,
                                                           boolean pending) {


        Transaction withdrawTransaction = new Transaction(allTransactionList,
                                                        sourceAccount,
                                                        null,
                                                        transactionOwner,
                                                        transaction,
                                                        TransactionType.WITHDRAWAL,
                                                        pending);

        return confirmTransaction(withdrawTransaction);
    }

    public static Transaction confirmTransaction(Transaction transaction) {

        if (!transaction.isPending()){

            if (transaction.getSourceAccount() != null ) {
                transaction.getSourceAccount().getAccountTransactions().add(transaction);
                transaction.getSourceAccount().buildBalanceFromTransactions();
            }

            if (transaction.getTargetAccount() != null ) {
                transaction.getTargetAccount().getAccountTransactions().add(transaction);
                transaction.getTargetAccount().buildBalanceFromTransactions();
            }

            allTransactionList.add(transaction);

        } else {

            UserInterface.outputString("Unable to complete transaction "
                    + transaction.getTransactionID() + ", transaction is pending");
        }

        return transaction;
    }

    public static void outputListOfTransactions(Account account, int numberOfTransactions) {

        if (numberOfTransactions > account.getAccountTransactions().size()) {
            numberOfTransactions = account.getAccountTransactions().size();
        }

        for (int i = numberOfTransactions; i > 0; i--) {
            UserInterface.outputString(account.getAccountTransactions().get(i-1).toStringByDate());
        }
    }

    public static void outputListOfTransactions(Account account) {
        outputListOfTransactions(account, account.getAccountTransactions().size());
    }
}
