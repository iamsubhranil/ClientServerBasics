package com.iamsubhranil.personal;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author : Nil
 * Date : 11/7/2016 at 1:18 AM.
 * Project : ClientServerBasics
 */
public class ServerThread extends Thread {

    private final ServerSocket serverSocket;
    private final SimpleStringProperty status;

    public ServerThread(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        status = new SimpleStringProperty("Waiting for inbound connections");
        System.out.println("Waiting for inbound connections");

    }

    public void run() {
        Socket incomingConnection = null;
        try {
            incomingConnection = serverSocket.accept();
            status.set("Connection accepted from port " + incomingConnection.getLocalPort());
            System.out.println("Connection accepted from port " + incomingConnection.getLocalPort());
        } catch (Exception e) {
            status.set("Error occurred while accepting connection!");
            System.out.println(("Error occurred while accepting connection!"));
        }
    }

}
