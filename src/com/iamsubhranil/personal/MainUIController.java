package com.iamsubhranil.personal;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    /*Method invoked when user presses Create button in client section */
    public void createClient(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket(serverIPField.getText(), Integer.parseInt(connectionPortField.getText()));
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
            clientCreationStatusLabel.setText("Successfully created client!");
        } catch (UnknownHostException e) {
            clientCreationStatusLabel.setText("Unknown host!");
            System.err.println("Fatal error while implementing socket!");
            e.printStackTrace();
        } catch (ConnectException e) {
            clientCreationStatusLabel.setText("Connection refused by remote!");
            System.err.println("Fatal error while implementing socket!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            clientCreationStatusLabel.setText("Port must be an integer");
        } catch (IOException e) {
            clientCreationStatusLabel.setText("IO error occured!");
            System.err.println("Fatal error while implementing socket!");
            e.printStackTrace();
        }
    }

    /*Method invoked when user presses Create button in Server section */
    public void createServer(ActionEvent actionEvent) {
        try {
            ServerThread server = new ServerThread(Integer.parseInt(serverPortField.getText()), threadedBox.isSelected());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
