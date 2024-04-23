package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class WinScreen implements Initializable{
    @FXML
    private Label bestScoreLabel;
    @FXML
    private Label yourWord;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        int bestScore = HelloController.getBestScore();
        bestScoreLabel.setText("Best Score: " + bestScore);
    }


}