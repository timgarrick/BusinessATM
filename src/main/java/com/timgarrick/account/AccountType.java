package com.timgarrick.account;

public enum AccountType {
    BUSINESS("Small Business Account",1, 1000),
    COMMUNITY("Community Account",1, 2500),
    CLIENT("Client Account", 1,1500);

    private String accountName;
    private int maxNumberOfAccountsPerType;
    private double accountOverdraft;

    private AccountType(String name, int maxNumberOfAccounts, double accountOverdraft) {
        this.accountName = name;
        this.maxNumberOfAccountsPerType = maxNumberOfAccounts;
        this.accountOverdraft = accountOverdraft;
    }

    public int getMaxNumberOfAccountsPerType() {
        return maxNumberOfAccountsPerType;
    }

    public double getAccountOverdraft() {
        return accountOverdraft;
    }

    public String getAccountName() {
        return accountName;
    }

}
