package com.iamsubhranil.personal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/styles/OpenSans-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/styles/OpenSans-Light.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/styles/OpenSans-Semibold.ttf"), 14);
        Parent root = FXMLLoader.load(getClass().getResource("ui/fxmls/MainUI.fxml"));
        primaryStage.setTitle("ClientsAndServers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
