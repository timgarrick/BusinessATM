package com.timgarrick.util;

import com.timgarrick.user.UserService;

import javax.swing.*;
import java.awt.event.*;

public class LoginBox extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField usernameTextField;
    private JFormattedTextField passwordFormattedTextField;

    public LoginBox() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void showWindow() {
        this.contentPane.setVisible(true);
    }

    private void onOK() {
        if(UserService.validateUserAgainstUserList(usernameTextField.getText(), passwordFormattedTextField.getText())) {

            dispose();
        } else {
            usernameTextField.setText("Username");
            passwordFormattedTextField.setText("Password");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoginBox dialog = new LoginBox();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
