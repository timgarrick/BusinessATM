package com.timgarrick.application;

public class ApplicationLogic {
    public static boolean validInputBoundry(int value, int startingValue, int endValue) {
        if (value >= startingValue && value <= endValue) {
            return true;
        }
        return false;
    }
}
