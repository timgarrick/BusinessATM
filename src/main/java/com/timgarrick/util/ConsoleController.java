package com.timgarrick.util;

import java.util.Scanner;

public class ConsoleController implements InputOutputOperations {

    Scanner scanner = new Scanner(System.in);


    public void outputString(String s) {
        System.out.println(s);
    }

    public String inputString(String s) {
        outputString(s);
/*        while(!scanner.hasNext())
        {
            outputString(s);
            scanner.next();
        }*/


        return scanner.nextLine();
    }

    public String inputString() {
        return scanner.nextLine();
    }

    @Override
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
}
