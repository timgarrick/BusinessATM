package com.timgarrick.account;

import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.account.transaction.TransactionService;
import com.timgarrick.application.ApplicationService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;

import java.util.ArrayList;
import java.util.List;

public class AccountLogic {


    public static void createNewAccount() {
        if (ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts().size() < AccountType.values().length) {

            String name = UserInterface.inputString("Enter a name for this account: ");
            UserInterface.outputString("Enter the type of account: ");
            int accountTypeCounter = 1;

            for (AccountType accountType:AccountType.values()) {
                UserInterface.outputString(accountTypeCounter + " - " + accountType.getAccountName());
                accountTypeCounter++;
            }

/*            while(!ApplicationLogic.validInputBoundry((int) UserInterface.inputNumber(),1, AccountType.values().length)) {

            }*/
            
            AccountType accountType = null;
            
            switch ((int) UserInterface.inputNumber()) {
                case 1 -> accountType = AccountType.BUSINESS;
                case 2 -> accountType = AccountType.CLIENT;
                case 3 -> accountType = AccountType.COMMUNITY;
                default -> UserInterface.outputString("Account type not recognised");
            }

            AccountService.createAccount(new Account(name, accountType, ApplicationService.currentlyLoggedInUser));
            UserService.refreshUserAccountList();

        } else {
            UserInterface.outputString("You have the maximum number of primary accounts.");
        }
        

    }

    public static void manageExistingAccount() {
        boolean manageExistingAccountsWindowIsOpen = true;
        Account activeAccount = null;

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
            accountListNames.add(account.getAccountID() + " - "
                    + account.getAccountName() + " ( "
                    + account.getAccountType().getAccountName() + ", primary), total balance including overdraft: £"
                    + (account.getBalance() + account.getAccountType().getAccountOverdraft()));

            accountList.add(account);

        }


        for (Account account : ApplicationService.currentlyLoggedInUser.getListOfSecondaryAccounts()) {
            accountListNames.add(account.getAccountID() + " - "
                        + account.getAccountName() + " ( "
                        + account.getAccountType().getAccountName() + ", secondary), total balance including overdraft: £"
                        + (account.getBalance() + account.getAccountType().getAccountOverdraft()));

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
                case 4 -> manageSpecificAccountAddUserAsJoint(account);
                case 5 -> manageSpecificAccountDeleteAccount(account);
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
            UserInterface.outputString("A request to "
                                        + account.getSecondaryOwner().getUsername() + " ("
                                        + account.getSecondaryOwner().getEmail() + ") has been sent");
        }

        String accountToBeDeleted = UserInterface.inputString("Please type the name of the account to confirm deletion");

        if (accountToBeDeleted.equals(account.getAccountName())) {
            AccountService.deleteAccount(account);
            return true;
        } else {
            UserInterface.outputString("Name does not match. Account has not been deleted");
            return false;
        }
    }

    private static void manageSpecificAccountAddUserAsJoint(Account account) {
        String inputString = UserInterface.inputString("Please enter a user to be added as a joint account holder");
        User userToBeAdded = UserService.findUser(inputString);
        if (userToBeAdded != null) {

            UserInterface.outputString("User " + userToBeAdded.getUsername() + " (" + userToBeAdded.getEmail()
            + ") has been sent a request to become the joint owner of this account");
            userToBeAdded.setJointAccountCreationRequest(account);

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
                                                                                    activeAccount,targetAccount,transferAmount);
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
        TransactionService.createNewDepositTransaction(ApplicationService.currentlyLoggedInUser,account,deposit);
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
            if (withdrawAmount > totalBalanceIncludingOverdraft / 0.2) {
                UserInterface.outputString("This is a joint account. To withdraw more than 20% of the total funds, " +
                                                "you need approval from the secondary owner.");

                if(UserInterface.userOptionSelection("Send request to joint owner and continue?") == 1) {
                    //flag joint owner on login
                    return true;
                }
            }
        }

        return false;

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
}
