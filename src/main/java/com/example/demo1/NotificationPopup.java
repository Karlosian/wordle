package com.example.demo1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class NotificationPopup{

    public static void showNotification(VBox root, String message) {
        // Create label
        Label notificationLabel = new Label(message);
        notificationLabel.setStyle("-fx-background-color: #FF5733; -fx-padding: 10px;");

        // Create VBox
        VBox notificationBox = new VBox(notificationLabel);
        notificationBox.setAlignment(Pos.TOP_CENTER);

        // Add the VBox to the root
        root.getChildren().add(notificationBox);

        //Remove notification after 3 seconds
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    root.getChildren().remove(notificationBox);
                })
        );
        timeline.play();
    }
}
