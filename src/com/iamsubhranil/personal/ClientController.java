package com.iamsubhranil.personal;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Author : Nil
 * Date : 11/7/2016 at 4:14 PM.
 * Project : ClientServerBasics
 */
public class ClientController {
    public Label localAddressLabel;
    public Label localPortLabel;
    public Label remoteAddressLabel;
    public Label remotePortLabel;
    public Label statusLabel;
    public TextArea terminalField;
    public TextField commandField;

    public void sendCommandAndErase(ActionEvent actionEvent) {
        String instruction = commandField.getText();
        commandField.setText("");
        terminalField.appendText("\nMe : " + instruction);
    }
}
