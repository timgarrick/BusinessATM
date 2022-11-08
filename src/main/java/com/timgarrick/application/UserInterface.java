package com.timgarrick.application;

import com.timgarrick.util.ConsoleController;

import java.util.List;

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


    public static double inputNumber(String s) {
        return userInterface.inputNumber(s);
    }

    public static double inputNumber() {
        return userInterface.inputNumber();
    }

    public static int userOptionSelection(String s) {

        return userInterface.userOptionSelection(s);

    }

    public static int userOptionSelection(List<String> list) {

        return userInterface.userOptionSelection(list);

    }


    public void clear() {
        userInterface.clear();
    }
}
