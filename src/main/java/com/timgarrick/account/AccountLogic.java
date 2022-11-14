package com.timgarrick.account;

import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.account.transaction.TransactionService;
import com.timgarrick.application.ApplicationService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;
import com.timgarrick.user.usermessage.UserMessageService;
import com.timgarrick.user.usermessage.UserMessageType;

import java.util.ArrayList;
import java.util.List;

public class AccountLogic {

    static Account activeAccount = null;

    public static void createNewAccount() {

        if (ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts().size() < AccountType.values().length) {

            List<AccountType> listOfAvailableAccountsTypes = new ArrayList<>();
            List<Account> listOfUsersPrimaryAccounts = new ArrayList<>();


            for (AccountType accountType:AccountType.values()) {

                listOfAvailableAccountsTypes.add(accountType);

                //loop through all accounts
                for (Account account:ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts()) {
                    if (account.getAccountType().equals(accountType)) {
                        listOfAvailableAccountsTypes.remove(accountType);
                    }
                }

            }

            StringBuilder availableAccountTypeString = new StringBuilder();


            for (AccountType availableAccountType: listOfAvailableAccountsTypes) {
                availableAccountTypeString.append(availableAccountType.getAccountName()
                                            + " (Overdraft: £" + availableAccountType.getAccountOverdraft() + ")#");
            }

            UserInterface.outputString("Choose a type of account");
            int accountSelection = UserInterface.userOptionSelection(availableAccountTypeString.toString());

            String accountName = UserInterface.inputString("Enter a name for this account: ");

            AccountService.createAccount(new Account(accountName, listOfAvailableAccountsTypes.get(accountSelection-1),
                                            ApplicationService.currentlyLoggedInUser));
            UserService.refreshUserAccountList();

        } else {
            UserInterface.outputString("You have the maximum number of primary accounts.");
        }
        

    }

    public static void manageExistingAccount() {
        boolean manageExistingAccountsWindowIsOpen = true;

        while (manageExistingAccountsWindowIsOpen) {


            activeAccount = selectAccountFromListOfAllAccounts();

            if (activeAccount == null) {
                manageExistingAccountsWindowIsOpen = false;
            } else {
                manageSpecificAccount(activeAccount);
            }

        }

    }

    public static Account selectAccountFromListOfAllAccounts() {

        List<String> accountListNames = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();


        for (Account account : ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts()) {

            accountListNames.add(account.toString());
            accountList.add(account);

        }


        for (Account account : ApplicationService.currentlyLoggedInUser.getListOfSecondaryAccounts()) {
            accountListNames.add(account.toString());

            accountList.add(account);

        }

        int userSelection = UserInterface.userOptionSelection(accountListNames);

        if(userSelection == 0) {
            return null;
        } else {
            return accountList.get(userSelection-1);
        }

    }

    private static void manageSpecificAccount(Account account) {
        boolean manageSpecificAccountWindowIsOpen = true;
        String manageSpecificAccountString = "Make a withdrawal#Make a deposit#Make a transfer to another account#" +
                "Add another user as a joint account owner#Delete account#";

        while(manageSpecificAccountWindowIsOpen) {
            UserInterface.outputString("The following account is selected: ");
            UserInterface.outputString(account.toString());
            UserInterface.outputString("");

            switch (UserInterface.userOptionSelection(manageSpecificAccountString)) {
                case 0 -> manageSpecificAccountWindowIsOpen = false;
                case 1 -> manageSpecificAccountWithdraw(account);
                case 2 -> manageSpecificAccountDeposit(account);
                case 3 -> manageSpecificAccountTransfer(account);
                case 4 -> manageSpecificAccountAddUserAsJoint(account); //working
                case 5 -> manageSpecificAccountDeleteAccount(account); //working
            }
        }
    }

    private static boolean manageSpecificAccountDeleteAccount(Account account) {

        if (account.getBalance() != 0) {
            UserInterface.outputString("Balance of the account must be 0 before it can be deleted");
            return false;
        }

        if (account.getSecondaryOwner() != null) {
            UserInterface.outputString("Permission from joint account holder is required before account can be deleted");
            UserInterface.outputString("A request to " + getSecondAccountOwner(account).friendlyName() + " has been sent");
            UserMessageService.createUserMessage(getSecondAccountOwner(account),
                                                ApplicationService.currentlyLoggedInUser,
                                                null,
                                                account,
                                                UserInterface.inputString("Enter a message for this request"),
                                                UserMessageType.JOINT_ACCOUNT_DELETION_REQUEST);

            return false;

        } else {

            String accountToBeDeleted = UserInterface.inputString("Please type \"Yes\" to confirm deletion");

            if (accountToBeDeleted.equalsIgnoreCase("yes")) {
                AccountService.deleteAccount(account);
                return true;
            } else {
                UserInterface.outputString("Account was not deleted");
                return false;
            }
        }
    }

    private static void manageSpecificAccountAddUserAsJoint(Account account) {
        String inputString = UserInterface.inputString("Please enter a user to be added as a joint account holder");
        User userToBeAdded = UserService.findUser(inputString);
        if (userToBeAdded != null) {

            String message = UserInterface.inputString("Enter a message for this request");

            UserInterface.outputString("User " + userToBeAdded.getUsername() + " (" + userToBeAdded.getEmail()
            + ") has been sent a request to become the joint owner of this account");
            UserMessageService.createUserMessage(userToBeAdded,
                    ApplicationService.currentlyLoggedInUser,
                    null,
                    account,
                    message,
                    UserMessageType.JOINT_ACCOUNT_CREATION_REQUEST);
        } else {
            UserInterface.outputString("User " + inputString + " not found");
        }
    }

    private static boolean manageSpecificAccountTransfer(Account activeAccount) {
        UserInterface.outputString("Current account is " + activeAccount.getAccountName()
                                    + " (ID: " + activeAccount.getAccountID() + ")");
        UserInterface.outputString("Please enter the account to transfer to: ");

        Account targetAccount = selectAccountFromListOfAllAccounts();

        if (targetAccount.equals(activeAccount)) {
            UserInterface.outputString("Unable to transfer to the same account. Please select another account to transfer into.");
            return false;
        }

        double transferAmount = UserInterface.inputNumber("Enter an amount to transfer: ");

        UserInterface.outputString("Transferring " + transferAmount + " from " + activeAccount.getAccountName() + " to "
                + targetAccount.getAccountName());

        String transferConfirm = UserInterface.inputString("Please type \"Yes\" to confirm, or \"No\" to cancel");

        if (transferConfirm.equalsIgnoreCase("yes")) {
            Transaction transferTransaction = TransactionService.createNewTransferTransaction(ApplicationService.currentlyLoggedInUser,
                                                                                    activeAccount,targetAccount,transferAmount, false);
            UserInterface.outputString("Transaction successful. Transaction information: ");
            UserInterface.outputString(activeAccount.toString());
            UserInterface.outputString(targetAccount.toString());

            return true;

        } else {
            UserInterface.outputString("Cancelling transfer.");
            return false;
        }
    }

    private static boolean manageSpecificAccountDeposit(Account account) {
        UserInterface.outputString("Active account: " + account.toString());

        double deposit = UserInterface.inputNumber("Enter a value to deposit into this account:");
        TransactionService.createNewDepositTransaction(ApplicationService.currentlyLoggedInUser,account,deposit, false);
        UserInterface.outputString("£" + deposit + " deposited into account. Current balance: £" + account.getBalance());

        return true;
    }

    private static boolean manageSpecificAccountWithdraw(Account account) {
        UserInterface.outputString("Active account: " + account.toString());

        double withdrawAmount = UserInterface.inputNumber("Enter a value to withdraw from this account:");
        double totalBalanceIncludingOverdraft = account.getBalanceIncludingOverdraft();

        if (withdrawAmount > totalBalanceIncludingOverdraft) {
            UserInterface.outputString("You do not have enough money to withdraw this amount");
            return false;
        }

        if (account.getSecondaryOwner() != null) {
            if (withdrawAmount > (totalBalanceIncludingOverdraft * 0.2)) {
                Transaction pendingTransaction = TransactionService.createNewWithdrawTransaction(
                        ApplicationService.currentlyLoggedInUser, account,withdrawAmount,true);

                UserInterface.outputString("You will need approval from " + getSecondAccountOwner(account).friendlyName()
                                            + " to withdraw more than 20% of the remaining balance from this account. " +
                                            "Would you like to proceed?");

                if(UserInterface.userOptionSelection("Confirm withdrawal") == 1) {
                    UserInterface.outputString("A pending transaction has been created with ID: " +
                            pendingTransaction.getTransactionID() + " and a request will be sent to " +
                            getSecondAccountOwner(account).friendlyName() + " for approval");

                    String messageString = UserInterface.inputString("Enter a message for this request: ");

                    UserMessageService.createUserMessage(getSecondAccountOwner(account),
                            ApplicationService.currentlyLoggedInUser,pendingTransaction,
                            account,messageString, UserMessageType.JOINT_ACCOUNT_TRANSACTION_REQUEST);
                    return true;
                } else {
                    UserInterface.outputString("Withdrawal cancelled");
                    return false;
                }
            }
        }

        TransactionService.createNewWithdrawTransaction(ApplicationService.currentlyLoggedInUser,account,
                                                withdrawAmount,false);

        UserInterface.outputString("£" + withdrawAmount + " withdrawn from account. Current balance: £"
                                    + account.getBalance());
        return true;
    }


    private static boolean validateAccountAgainstUserSelection(int userInputChoice) {
        for (Account account:ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts())
        {
            if (userInputChoice == account.getAccountID()) {
                return true;
            }
        }

        for (Account account:ApplicationService.currentlyLoggedInUser.getListOfSecondaryAccounts())
        {
            if (userInputChoice == account.getAccountID()) {
                return true;
            }
        }

        return false;
    }

    public static void listExistingAccount() {
        UserInterface.outputString("Primary Accounts");
        for (Account account:ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts()) {
            UserInterface.outputString(account.toString());
        }

        UserInterface.outputString("Secondary Accounts");
        for (Account account:ApplicationService.currentlyLoggedInUser.getListOfSecondaryAccounts()) {
            UserInterface.outputString(account.toString());
        }
    }

    private static User getSecondAccountOwner(Account account) {

        if (ApplicationService.currentlyLoggedInUser.equals(account.getPrimaryOwner())) {
            return account.getSecondaryOwner();
        } else if (ApplicationService.currentlyLoggedInUser.equals(account.getSecondaryOwner())) {
            return account.getPrimaryOwner();
        }

        return null;
    }
}
