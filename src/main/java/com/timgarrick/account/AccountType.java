package com.timgarrick.account;

public enum AccountType {
    BUSINESS("Small Business Account",1, 1000, false),
    COMMUNITY("Community Account",1, 2500, false),
    CLIENT("Client Account", 1,1500, false);

    private String accountName;
    private int maxNumberOfAccountsPerType;
    private double accountOverdraft;
    private boolean accountCreatedAsPrimary;

    private AccountType(String name, int maxNumberOfAccounts, double accountOverdraft, boolean accountCreatedAsPrimary) {
        this.accountName = name;
        this.maxNumberOfAccountsPerType = maxNumberOfAccounts;
        this.accountOverdraft = accountOverdraft;
        this.accountCreatedAsPrimary = true;
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

    public boolean getAccountCreatedAsPrimary() {
        return accountCreatedAsPrimary;
    }





}
