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
    private final boolean acceptMultipleClients;
    private final String signature;

    public ServerThread(int port, boolean acceptMultiple) throws IOException {

        serverSocket = new ServerSocket(port);
        acceptMultipleClients = acceptMultiple;
        signature = "#server@" + serverSocket.getLocalPort();
        status = new SimpleStringProperty("Waiting for inbound connections");
        setDaemon(true);

    }

    public void run() {
        while (!serverSocket.isClosed()) {
            Socket incomingConnection = null;
            try {
                System.out.println(signature + ": Waiting for inbound connections..");
                incomingConnection = serverSocket.accept();
                status.set("Connection accepted from port " + incomingConnection.getPort());
                System.out.println(signature + ": Connection accepted from port " + incomingConnection.getPort() + "..");
                System.out.println(signature + ": Setting up client..");
                ClientServerThread clientServerThread = new ClientServerThread(incomingConnection, null, signature);
                System.out.println(signature + ": Starting client..");
                System.out.println(signature + ": SupportMultiple : " + acceptMultipleClients);
                if (acceptMultipleClients) {
                    clientServerThread.start();
                } else {
                    clientServerThread.run();
                }
            } catch (Exception e) {
                status.set("Error occurred while accepting connection!");
                System.out.println(signature + ": Error occurred while accepting connection!");
            }
        }
    }

}
