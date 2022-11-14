package com.timgarrick.user.usermessage;

import com.timgarrick.account.Account;
import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.user.User;

import java.util.Date;

public class UserMessage {
    private int messageID;
    private User targetUser;
    private User sentBy;
    private boolean active;
    private Transaction transaction;
    private Account account;
    private String contextMessage;
    private Date messageCreated;
    private UserMessageType userMessageType;

    public UserMessage(int messageID,
                       User sentBy,
                       User targetUser,
                       Transaction transaction,
                       Account account,
                       String contextMessage,
                       UserMessageType userMessageType) {
        this.sentBy = sentBy;
        this.targetUser = targetUser;
        this.messageID = messageID;
        this.active = true;
        this.transaction = transaction;
        this.account = account;
        this.contextMessage = contextMessage;
        this.userMessageType= userMessageType;
        this.messageCreated = new Date();
    }

    public int getMessageID() {
        return messageID;
    }

    public User getTargetUser() {
        return targetUser;
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

    public User getSentBy() {
        return sentBy;
    }
}
