package com.timgarrick.util;

import javax.swing.*;

public class SwingGUI extends JFrame {
    public static void showWindow(String bankName){
        JFrame frame = new JFrame(bankName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        JButton button = new JButton("Press");
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }
}
