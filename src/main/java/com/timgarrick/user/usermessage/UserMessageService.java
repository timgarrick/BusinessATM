package com.timgarrick.user.usermessage;

import com.timgarrick.account.Account;
import com.timgarrick.account.AccountService;
import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.application.UserInterface;
import com.timgarrick.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMessageService {
    private static List<UserMessage> allMessages = new ArrayList<>();

    public static UserMessage createUserMessage(User targetUser, User sentBy, Transaction transaction, Account account, String message,
                                                UserMessageType userMessageType) {
        int id = allMessages.size()+1;
        UserMessage newUserMessage = new UserMessage(id, sentBy, false, targetUser, transaction, account, message, userMessageType);

        allMessages.add(newUserMessage);
        targetUser.getListOfUserMessages().add(newUserMessage);

        return newUserMessage;
    }

    public static boolean processMessage(UserMessage message) {

        switch (message.getUserMessageType()) {
            case JOINT_ACCOUNT_CREATION_REQUEST -> {
                if (AccountService.processJointAccountCreationRequest()) {
                    //read message, delete message from user list
                }
                UserInterface.outputString(message.getTargetUser() );
            }
            case JOINT_ACCOUNT_DELETION_REQUEST -> {
                AccountService.processJointAccountDeletionRequest();

            }
            case JOINT_ACCOUNT_TRANSACTION_REQUEST -> {
                AccountService.processJointAccountTransactionRequest();

            }
        }
    }
}
