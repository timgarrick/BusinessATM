package com.timgarrick.application;


public class UserLogic {

    static void welcomeUser() {

        ApplicationService.userInterface.outputString("Banking with "
                + ApplicationService.bankName+ ". Welcome!");
        if (ApplicationService.currentlyLoggedInUser == null) {
            ApplicationService.userInterface.outputString("You are not currently logged in.");
        } else {
            ApplicationService.userInterface.outputString("You are currently logged in as "
                            + ApplicationService.currentlyLoggedInUser.getUsername());
        }

    }


}