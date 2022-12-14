package com.timgarrick.user;

import com.timgarrick.account.Account;
import com.timgarrick.user.usermessage.UserMessage;
import com.timgarrick.util.ModelOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class User implements ModelOutput {
    private int userID;
    private String username;
    private String password;
    private String email;
    private List<Account> listOfPrimaryAccounts;
    private List<Account> listOfSecondaryAccounts;
    private List<UserMessage> listOfUserMessages;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.listOfPrimaryAccounts = new ArrayList<>();
        this.listOfSecondaryAccounts = new ArrayList<>();
        this.listOfUserMessages = new Stack<>();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getListOfPrimaryAccounts() {
        return listOfPrimaryAccounts;
    }

    public List<Account> getListOfSecondaryAccounts() {
        return listOfSecondaryAccounts;
    }

    public List<UserMessage> getListOfUserMessages() {
        return listOfUserMessages;
    }

    public void setListOfPrimaryAccounts(List<Account> listOfPrimaryAccounts) {
        this.listOfPrimaryAccounts = listOfPrimaryAccounts;
    }

    public void setListOfSecondaryAccounts(List<Account> listOfSecondaryAccounts) {
        this.listOfSecondaryAccounts = listOfSecondaryAccounts;
    }

    @Override
    public String userSelectionOutput() {
        return "ID: " + this.getUserID() + ", Username " + getUserID();
    }

    public String friendlyName() {
        return this.getUsername() + "(" + this.getEmail() + ")";
    }
}

//**user** <long userid, string username, string password, string email address, account ListOfAccounts>
//
//Manage current accounts
//
//Create a new account (only 1 of each type)
//
//For new users, they should be able to create an account
//
//Restrictions placed on all accounts that require two signatories
//
//Check balance, make withdrawals, deposit and transfers to other accounts they own
//
//Can only own 1 of each type of account (business, community, client)
