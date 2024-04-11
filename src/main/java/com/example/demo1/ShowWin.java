package com.example.demo1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowWin {
    public static void showWin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("win-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300,200 );
        stage.setTitle("You Win!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
