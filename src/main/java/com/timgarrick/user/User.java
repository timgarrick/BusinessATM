package com.timgarrick.user;

import com.timgarrick.account.Account;

import java.util.List;

public class User {
    private int userID;
    private String username;
    private String password;
    private String email;
    private List<Account> listOfAccounts;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    public List<Account> getListOfAccounts() {
        return listOfAccounts;
    }

    public void setListOfAccounts(List<Account> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
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
