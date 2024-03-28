    package com.example.demo1;

    import javafx.fxml.FXML;
    import javafx.event.ActionEvent;
    import javafx.fxml.Initializable;
    import javafx.scene.Node;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.control.Button;
    import javafx.scene.layout.GridPane;

    import java.net.URL;
    import java.util.ResourceBundle;

    public class HelloController implements Initializable{

        @FXML
        private GridPane textFieldGrid;

        private static int placeTracker = 0;

        public void initialize(URL url, ResourceBundle resourceBundle) {
            // Loop through each cell and add TextField
            int counter = 0;
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 5; col++) {
                    counter++;
                    TextField textField = new TextField();
                    textField.setAlignment(javafx.geometry.Pos.CENTER);
                    textField.setPrefHeight(70.0);
                    textField.setPrefWidth(70.0);
                    textField.setMaxWidth(70.0);
                    textField.setMinHeight(70.0);
                    textField.setEditable(false);
                    textField.setText(" ");
                    textField.setId(String.valueOf(counter));
                    textField.getStyleClass().add("GridClass"); // Apply CSS class
                    textFieldGrid.add(textField, col, row);

                    //GridPane.setMargin(textField, new javafx.geometry.Insets(35));
                }
            }
        }
        public void addLetter(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            String buttonId = clickedButton.getId();
            placeTracker++;
            TextField tf = (TextField) textFieldGrid.lookup(("#" + placeTracker));
            tf.setText(buttonId);
        }
    }
