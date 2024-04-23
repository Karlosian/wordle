package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import java.net.URL;
import javafx.fxml.Initializable;

import java.util.Arrays;
import java.util.ResourceBundle;
public class LoseScreen implements Initializable{

    @FXML
    private Label bestScoreLabel;
    @FXML
    private Label yourWord;
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int bestScore = HelloController.getBestScore();
        bestScoreLabel.setText("Best Score: " + bestScore);
        yourWord.setText("Your word: " + HelloController.getWord());
    }


}
