package com.timgarrick.account;

import com.timgarrick.application.ApplicationService;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;

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

        while (manageExistingAccountsWindowIsOpen) {
            UserInterface.outputString("Please choose an account ID to manage");
            UserInterface.outputString("0 - Back to main menu");


            int accountCounter = 1;

            for (Account account : ApplicationService.currentlyLoggedInUser.getListOfPrimaryAccounts()) {
                UserInterface.outputString(account.getAccountID() + " - "
                        + account.getAccountName() + " ( "
                        + account.getAccountType().getAccountName() + ", primary), total balance including overdraft: £"
                        + account.getBalance() + account.getAccountType().getAccountOverdraft());
            }

            for (Account account : ApplicationService.currentlyLoggedInUser.getListOfSecondaryAccounts()) {
                UserInterface.outputString(account.getAccountID() + " - "
                        + account.getAccountName() + " ( "
                        + account.getAccountType().getAccountName() + ", secondary), total balance including overdraft: £"
                        + account.getBalance() + account.getAccountType().getAccountOverdraft());
            }


            int userInputChoice = (int) UserInterface.inputNumber();

            if (userInputChoice == 0) {
                manageExistingAccountsWindowIsOpen = false;
            } else if (validateAccountAgainstUserSelection(userInputChoice)) {
                manageSpecificAccount(userInputChoice);
            } else {
                UserInterface.outputString("Account does not exist or you do not have permission to view this account");
            }

        }

    }

    private static void manageSpecificAccount(int accountID) {
        boolean manageSpecificAccountWindowIsOpen = true;

        while(manageSpecificAccountWindowIsOpen) {
            UserInterface.outputString("The following account is selected: ");
            UserInterface.outputString(AccountService.findAccountByID(accountID).toString());
            UserInterface.outputString("");
            UserInterface.outputString("Account options");
            UserInterface.outputString("1 - Make a withdrawal");
            UserInterface.outputString("2 - Make a deposit");
            UserInterface.outputString("3 - Make a transfer to another account");
            UserInterface.outputString("4 - Add another user as a joint owner");
            UserInterface.outputString("5 - Delete account");
            UserInterface.outputString("6 - Go back to account list");

            switch ((int) UserInterface.inputNumber()) {
                case 1 -> manageSpecificAccountWithdraw(accountID);
                case 2 -> manageSpecificAccountDeposit(accountID);
                case 3 -> manageSpecificAccountTransfer(accountID);
                case 4 -> manageSpecificAccountAddUserAsJoint(accountID);
                case 5 -> manageSpecificAccountDeleteAccount(accountID);
                case 6 -> manageSpecificAccountWindowIsOpen = false;
                default -> UserInterface.outputString("Account type not recognised");
            }


        }
    }

    private static boolean manageSpecificAccountDeleteAccount(int accountID) {
        Account account = AccountService.findAccountByID(accountID);

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
            AccountService.deleteAccount(accountID);
            return true;
        } else {
            UserInterface.outputString("Name does not match. Account has not been deleted");
            return false;
        }
    }

    private static void manageSpecificAccountAddUserAsJoint(int accountID) {
        String inputString = UserInterface.inputString("Please enter a user to be added as a joint account holder");
        User userToBeAdded = UserService.findUser(inputString);
        if (userToBeAdded != null) {

            UserInterface.outputString("User " + userToBeAdded.getUsername() + " (" + userToBeAdded.getEmail()
            + ") has been sent a request to become the joint owner of this account");
            userToBeAdded.setJointAccountCreationRequest(accountID);

        } else {
            UserInterface.outputString("User " + inputString + " not found");
        }
    }

    private static void manageSpecificAccountTransfer(int accountID) {
    }

    private static void manageSpecificAccountDeposit(int accountID) {
    }

    private static void manageSpecificAccountWithdraw(int accountID) {
        
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
