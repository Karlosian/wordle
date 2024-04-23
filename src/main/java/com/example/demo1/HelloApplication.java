package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader welcomeMessage = new FXMLLoader(HelloApplication.class.getResource("welcomeMessage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 720,900 );
        stage.setTitle("Wordle");
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        Parent welcomeRoot = welcomeMessage.load();
        Scene welcomeScene = new Scene(welcomeRoot);
        Stage welcomeStage = new Stage();
        welcomeStage.setTitle("Welcome Message");
        welcomeStage.setScene(welcomeScene);


        stage.setScene(scene);
        stage.setResizable(false);
        welcomeStage.setResizable(false);
        // Add event handler for key pressed

        stage.show();
        welcomeStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}