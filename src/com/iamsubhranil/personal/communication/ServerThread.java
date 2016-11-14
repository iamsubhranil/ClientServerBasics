package com.iamsubhranil.personal.communication;

import com.iamsubhranil.personal.communication.fullduplex.EndSocket;
import com.iamsubhranil.personal.communication.simplex.CustomIOThread;
import com.iamsubhranil.personal.ui.controllers.BasicServerUIController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    private final Object locker = new Object();

    public ServerThread(int port, boolean acceptMultiple) throws IOException {

        serverSocket = new ServerSocket(port);
        acceptMultipleClients = acceptMultiple;
        //   signature = "#server@" + serverSocket.getLocalPort();
        signature = "~#";
        setDaemon(true);

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private void loadServerResponseUI(EndSocket endSocket) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com/iamsubhranil/personal/ui/fxmls/BasicServerUI.fxml"));
        Parent root = fxmlLoader.load();
        BasicServerUIController basicServerUIController = fxmlLoader.getController();
        basicServerUIController.setSocket(endSocket);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            endSocket.setStage(stage);
            stage.show();
        });
    }

    public void run() {
        checkStreams();
        while (!serverSocket.isClosed()) {
            Socket incomingConnection = null;
            try {
                outputViewer.println(signature + ": Waiting for inbound connections..");
                incomingConnection = serverSocket.accept();
                outputViewer.println(signature + ": Connection accepted from port " + incomingConnection.getPort() + "..");
                outputViewer.println(signature + ": Setting up connection..");
                //   ClientServerThread clientServerThread = new ClientServerThread(incomingConnection, signature);
                EndSocket endSocket = new EndSocket(incomingConnection, true, acceptMultipleClients ? null : locker);
                outputViewer.println(signature + ": Setting up response UI..");
                loadServerResponseUI(endSocket);
                outputViewer.println(signature + ": Starting connection..");
                outputViewer.println(signature + ": SupportMultiple : " + acceptMultipleClients);
                if (!acceptMultipleClients) {
                    outputViewer.println(signature + ": Waiting for the client to disconnect..");
                    synchronized (locker) {
                        locker.wait();
                    }
                    outputViewer.println(signature + ": Client disconnected from port " + incomingConnection.getPort() + "..");
                }
            } catch (Exception e) {
                outputViewer.println(signature + ": Error occurred while accepting connection!");
                e.printStackTrace();
            }
        }
    }

}
