package com.timgarrick.user.usermessage;

import com.timgarrick.account.Account;
import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMessageService {
    private static List<UserMessage> allMessages = new ArrayList<>();

    public static UserMessage createUserMessage(User user, Transaction transaction, Account account, String message,
                                                UserMessageType userMessageType) {
        int id = allMessages.size()+1;
        UserMessage newUserMessage = new UserMessage(id, true, user, transaction, account, message, userMessageType);

        allMessages.add(newUserMessage);

        return newUserMessage;
    }
}
