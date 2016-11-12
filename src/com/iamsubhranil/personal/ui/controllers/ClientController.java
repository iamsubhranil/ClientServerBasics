package com.iamsubhranil.personal.ui.controllers;

import com.iamsubhranil.personal.io.TextAreaWriter;
import com.iamsubhranil.personal.io.TextFieldReader;
import com.iamsubhranil.personal.threads.fullduplex.EndSocket;
import com.iamsubhranil.personal.threads.simplex.ClientThread;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void setupAndStartThread(ClientThread clientThread, Stage s) {
        statusLabel.textProperty().bind(clientThread.statusProperty());
        clientThread.setReader(new TextFieldReader(commandField));
        clientThread.setWriter(new TextAreaWriter(terminalField));
        clientThread.start();
        s.setOnCloseRequest(value -> {
            try {
                clientThread.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientThread.interrupt();
        });
        Socket socket = clientThread.getSocket();
        decorate(socket);
    }
}
