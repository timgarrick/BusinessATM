package com.timgarrick.user.usermessage;

import com.timgarrick.account.Account;
import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.user.User;

import java.util.Date;

public class UserMessage {
    private int messageID;
    private User user;
    private boolean active;
    private Transaction transaction;
    private Account account;
    private String contextMessage;
    private Date messageCreated;
    private UserMessageType userMessageType;

    public UserMessage(int messageID,
                       boolean read,
                       User user,
                       Transaction transaction,
                       Account account,
                       String contextMessage,
                       UserMessageType userMessageType) {
        this.user = user;
        this.messageID = messageID;
        this.active = read;
        this.transaction = transaction;
        this.account = account;
        this.contextMessage = contextMessage;
        this.userMessageType= userMessageType;
        this.messageCreated = new Date();
    }

    public int getMessageID() {
        return messageID;
    }

    public User getUser() {
        return user;
    }

    public boolean isActive() {
        return active;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Account getAccount() {
        return account;
    }

    public String getContextMessage() {
        return contextMessage;
    }

    public Date getMessageCreated() {
        return messageCreated;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserMessageType getUserMessageType() {
        return userMessageType;
    }
}
