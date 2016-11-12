package com.iamsubhranil.personal.ui.controllers;

import com.iamsubhranil.personal.communication.ServerThread;
import com.iamsubhranil.personal.communication.fullduplex.EndSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {
    public TextField serverIPField;
    public TextField connectionPortField;
    public Button createClientButton;
    public Label clientCreationStatusLabel;
    public TextField serverPortField;
    public CheckBox threadedBox;
    public Button createServerButton;
    public Label serverCreationStatusLabel;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadClientUI(EndSocket endSocket) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com/iamsubhranil/personal/ui/fxmls/ClientUI.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        ClientController clientController = fxmlLoader.getController();
        //clientController.setupAndStartThread(clientThread, stage);
        clientController.setupAndStartDuplex(endSocket);
        stage.setScene(scene);
        stage.show();
    }

    /*Method invoked when user presses Create button in client section */
    public void createClient(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket(serverIPField.getText(), Integer.parseInt(connectionPortField.getText()));
            loadClientUI(new EndSocket(socket, false));
            clientCreationStatusLabel.setText("Successfully created client.");
        } catch (UnknownHostException e) {
            clientCreationStatusLabel.setText("Unknown host!");
        } catch (ConnectException e) {
            clientCreationStatusLabel.setText("Connection refused by remote!");
        } catch (NumberFormatException e) {
            clientCreationStatusLabel.setText("Port must be an integer!");
        } catch (IOException e) {
            clientCreationStatusLabel.setText("IO error occurred while connecting!");
            System.err.println("Fatal error while implementing socket!");
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            clientCreationStatusLabel.setText("Port index is out of range!");
        }
    }

    /*Method invoked when user presses Create button in Server section */
    public void createServer(ActionEvent actionEvent) {
        try {
            ServerThread server = new ServerThread(Integer.parseInt(serverPortField.getText()), threadedBox.isSelected());
            server.start();
            serverCreationStatusLabel.setText("Server created.");
        } catch (IOException e) {
            serverCreationStatusLabel.setText("Error : " + e.getMessage());
        } catch (NumberFormatException e) {
            serverCreationStatusLabel.setText("Port must be an integer!");
        }
    }
}
