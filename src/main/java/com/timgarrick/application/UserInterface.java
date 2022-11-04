package com.timgarrick.application;

import com.timgarrick.util.ConsoleController;

public class UserInterface {
    static ConsoleController userInterface = new ConsoleController();


    public static void outputString(String s) {
        userInterface.outputString(s);
    }

    public static String inputString(String s) {
        return userInterface.inputString(s);
    }

    public String inputString() {
        return userInterface.inputString();
    }


    public double inputNumber(String s) {
        return userInterface.inputNumber(s);
    }

    public static double inputNumber() {
        return userInterface.inputNumber();
    }


    public void clear() {
        userInterface.clear();
    }
}
