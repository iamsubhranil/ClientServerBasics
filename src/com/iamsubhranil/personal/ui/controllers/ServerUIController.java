package com.iamsubhranil.personal.ui.controllers;

import com.iamsubhranil.personal.communication.ServerThread;
import com.iamsubhranil.personal.io.TextAreaWriter;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.ServerSocket;

/**
 * Author : Nil
 * Date : 11/14/2016 at 9:02 AM.
 * Project : ClientServerBasics
 */
public class ServerUIController {
    public Label localAddressLabel;
    public Label statusLabel;
    public Label localPortLabel;
    public Label connectedClientsLabel;
    public Label activeClientsLabel;
    public Label connectionModeLabel;
    public ListView activeListView;
    public ListView requestedListView;
    public ListView disconnectedListView;
    public TextArea outputArea;

    public void setup(ServerThread server) {
        ServerSocket toSet = server.getServerSocket();
        localAddressLabel.setText("Local address : " + toSet.getLocalSocketAddress().toString());
        localPortLabel.setText("Local port : " + toSet.getLocalPort());
        server.setWriter(new TextAreaWriter(outputArea));
        server.start();
    }
}
