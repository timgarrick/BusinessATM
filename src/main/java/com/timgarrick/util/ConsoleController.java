package com.timgarrick.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleController implements InputOutputOperations {

    Scanner scanner = new Scanner(System.in);


    public void outputString(String s) {
        System.out.println(s);
    }

    public String inputString(String s) {
        outputString(s);
        return scanner.nextLine();
    }

    public String inputString() {
        return scanner.nextLine();
    }

    public double inputNumber(String s) {
        outputString(s);
        return inputNumber();
    }

    public double inputNumber() {
        while(!scanner.hasNextInt()){
            outputString("Number not recognised");
            scanner.next();
        }
        int inputValue = scanner.nextInt();
        scanner.nextLine();
        return inputValue;
    }

    @Override
    public void outputNumber(String s) {

    }

    public void clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println("\r\n");

        }
    }

    public int userOptionSelection(String inputString) {
        List<String> stringList = new ArrayList<>(Arrays.asList(inputString.split("#")));
        return userOptionSelection(stringList);
    }

    public int userOptionSelection(List<String> inputOptions) {
        if (inputOptions == null) {
            return -1;
        }

        int objectCounter = 1;

        outputString("Please enter a selection");

        for (String selection: inputOptions) {
            outputString(objectCounter + " - " + selection);
            objectCounter++;
        }

        outputString("0 - Exit menu");

        int inputValue = (int) inputNumber();

        if (inputValue < 0 || inputValue > inputOptions.size()) {
            outputString("Please enter a valid selection");
            return -1;
        } else {
            return inputValue;
        }
    }
}
