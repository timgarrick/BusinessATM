package com.timgarrick.account.transaction;

public enum TransactionType {
    TRANSFER("Transfer"),
    WITHDRAWAL("Withdraw"),
    DEPOSIT("Deposit");

    private String friendlyName;

    TransactionType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
