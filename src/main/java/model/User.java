package model;

import java.util.List;

public class User {
    private int userID;
    private String username;
    private String password;
    private String email;
    private List<Account> listOfAccounts;



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
