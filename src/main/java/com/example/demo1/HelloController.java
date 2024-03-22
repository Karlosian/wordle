package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private GridPane textFieldGrid;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Loop through each cell and add TextField
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                TextField textField = new TextField();
                textField.setAlignment(javafx.geometry.Pos.CENTER);
                textField.setPrefHeight(50.0);
                textField.setPrefWidth(50.0);
                textField.setMaxWidth(50.0);
                textField.setMinHeight(50.0);
                textField.getStyleClass().add("GridClass"); // Apply CSS class
                textFieldGrid.add(textField, col, row);

                GridPane.setMargin(textField, new javafx.geometry.Insets(35));
            }
        }
    }
}