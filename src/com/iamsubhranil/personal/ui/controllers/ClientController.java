package com.iamsubhranil.personal.ui.controllers;

import com.iamsubhranil.personal.communication.fullduplex.EndSocket;
import com.iamsubhranil.personal.io.TextAreaWriter;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.Socket;

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

    public void setupAndStartDuplex(EndSocket endSocket) {
        endSocket.setWriter(new TextAreaWriter(terminalField));
        endSocket.startReadExecutor();
        commandField.setOnAction(e -> {
            endSocket.writeAll(commandField.getText());
            commandField.setText("");
        });
        decorate(endSocket.getSocket());
    }

    private void decorate(Socket socket) {
        remoteAddressLabel.setText("Remote address : " + socket.getInetAddress().getHostAddress());
        localAddressLabel.setText("Local address : " + socket.getLocalAddress().getHostAddress());
        remotePortLabel.setText("Remote port : " + socket.getPort());
        localPortLabel.setText("Local port : " + socket.getLocalPort());
    }
}
