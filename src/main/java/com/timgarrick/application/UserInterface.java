package com.timgarrick.application;

import com.timgarrick.util.ConsoleController;

public class UserInterface {
    ConsoleController userInterface = new ConsoleController();


    public void outputString(String s) {
        userInterface.outputString(s);
    }

    public String inputString(String s) {
        return userInterface.inputString(s);
    }

    public double inputNumber(String s) {
        return userInterface.inputNumber(s);
    }


    public void clear() {
        userInterface.clear();
    }
}
