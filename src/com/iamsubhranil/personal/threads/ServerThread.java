package com.iamsubhranil.personal.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author : Nil
 * Date : 11/7/2016 at 1:18 AM.
 * Project : ClientServerBasics
 */
public class ServerThread extends CustomIOThread {

    private final ServerSocket serverSocket;
    private final boolean acceptMultipleClients;
    private final String signature;

    public ServerThread(int port, boolean acceptMultiple) throws IOException {

        serverSocket = new ServerSocket(port);
        acceptMultipleClients = acceptMultiple;
        //   signature = "#server@" + serverSocket.getLocalPort();
        signature = "Me";
        setDaemon(true);

    }

    public void run() {
        checkStreams();
        while (!serverSocket.isClosed()) {
            Socket incomingConnection = null;
            try {
                outputViewer.println(signature + ": Waiting for inbound connections..");
                incomingConnection = serverSocket.accept();
                outputViewer.println(signature + ": Connection accepted from port " + incomingConnection.getPort() + "..");
                outputViewer.println(signature + ": Setting up client..");
                ClientServerThread clientServerThread = new ClientServerThread(incomingConnection, signature);
                outputViewer.println(signature + ": Starting client..");
                outputViewer.println(signature + ": SupportMultiple : " + acceptMultipleClients);
                if (acceptMultipleClients) {
                    clientServerThread.start();
                } else {
                    clientServerThread.run();
                }
            } catch (Exception e) {
                outputViewer.println(signature + ": Error occurred while accepting connection!");
            }
        }
    }

}
