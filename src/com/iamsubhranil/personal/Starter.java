package com.iamsubhranil.personal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/fxmls/MainUI.fxml"));
        primaryStage.setTitle("ClientsAndServers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
