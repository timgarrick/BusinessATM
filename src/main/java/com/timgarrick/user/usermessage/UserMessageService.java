package com.timgarrick.user.usermessage;

import com.timgarrick.account.Account;
import com.timgarrick.account.AccountService;
import com.timgarrick.account.transaction.Transaction;
import com.timgarrick.account.transaction.TransactionService;
import com.timgarrick.application.ApplicationService;
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
                if (AccountService.processJointAccountCreationRequest(message)) {
                    AccountService.updateSecondaryUser(message.getAccount(),ApplicationService.currentlyLoggedInUser);
                }
                message.setActive(false);
            }
            case JOINT_ACCOUNT_DELETION_REQUEST -> {
                if (AccountService.processJointAccountDeletionRequest(message)) {
                    AccountService.deleteAccount(message.getAccount());
                }
                message.setActive(false);

            }
            case JOINT_ACCOUNT_TRANSACTION_REQUEST -> {
                if (AccountService.processJointAccountTransactionRequest(message)) {
                    TransactionService.confirmTransaction(message.getTransaction());
                }
                message.setActive(false);
            }
        }
        return true;

    }
}
