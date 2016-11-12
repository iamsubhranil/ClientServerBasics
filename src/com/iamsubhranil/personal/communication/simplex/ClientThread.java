package com.iamsubhranil.personal.communication.simplex;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author : Nil
 * Date : 11/7/2016 at 2:15 PM.
 * Project : ClientServerBasics
 */
public class ClientThread extends CustomIOThread {

    private final Socket socket;
    private final String signature;
    private final BufferedReader clientInputStream;
    private final PrintWriter clientOutputStream;
    private final SimpleStringProperty statusProperty = new SimpleStringProperty("Intermediate..");
    private boolean mayBeBound = false;

    public ClientThread(Socket s) throws IOException {
        socket = s;
        clientInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clientOutputStream = new PrintWriter(socket.getOutputStream(), true);
        //    signature = "#socket@" + socket.getLocalSocketAddress().toString();
        signature = "Me";
        updateStatus("Initiating..");
        setDaemon(true);
    }

    private void updateStatus(String status) {
        if (mayBeBound) {
            Platform.runLater(() -> statusProperty.setValue("Status : " + status));
        }
    }

    public StringProperty statusProperty() {
        mayBeBound = true;
        return statusProperty;
    }

    public void close() throws IOException {
        clientInputStream.close();
        clientOutputStream.close();
        socket.close();
    }

    public void run() {
        try {
            updateStatus("Initiating IO..");
            checkStreams();
            String serverResponse;
            updateStatus("Waiting for server..");
            while (!((serverResponse = clientInputStream.readLine()).equals("stop"))) {
                if (!statusProperty.getValue().equals("Connected..")) {
                    updateStatus("Connected..");
                }
                outputViewer.println("Server: " + serverResponse);
                String instruction = instructor.readLine();
                outputViewer.println(signature + ": " + instruction);
                clientOutputStream.println(instruction);
            }
            updateStatus("Closing IO and socket..");
            close();
            updateStatus("Disconnected!");
            outputViewer.println(signature + ": Connection terminated by server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
