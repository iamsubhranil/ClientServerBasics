package com.iamsubhranil.personal.ui.controllers;

import com.iamsubhranil.personal.io.TextAreaWriter;
import com.iamsubhranil.personal.threads.fullduplex.EndSocket;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Author : Nil
 * Date : 11/12/2016 at 7:26 PM.
 * Project : ClientServerBasics
 */
public class BasicServerUIController {

    public TextField inputField;
    public TextArea outputArea;

    private EndSocket endSocket;

    public void send(ActionEvent actionEvent) {
        endSocket.writeAll(inputField.getText());
        inputField.setText("");
    }

    public void setSocket(EndSocket end) {
        endSocket = end;
        endSocket.setWriter(new TextAreaWriter(outputArea));
        endSocket.startReadExecutor();
    }
}
