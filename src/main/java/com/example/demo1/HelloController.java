    package com.example.demo1;

    import java.io.File;
    import javafx.fxml.FXML;
    import javafx.event.ActionEvent;
    import javafx.fxml.Initializable;
    import javafx.scene.control.TextField;
    import javafx.scene.control.Button;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.GridPane;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;

    import java.io.FileNotFoundException;

    import java.lang.StringBuilder;
    import java.net.URL;
    import java.util.Arrays;
    import java.util.ResourceBundle;
    import java.util.Scanner;

    public class HelloController implements Initializable{

        @FXML
        private GridPane textFieldGrid;

        @FXML
        private AnchorPane Anchor;

        private static int placeTracker = 0;

        private static int rowIndex = 0;

        private String[] words = new String[2315];

        private static boolean rowFull = false;

        private String chosenWord= new String();

        private String finalString = " ";


        //Initialize text fields in the grid
        public void initialize(URL url, ResourceBundle resourceBundle) {
            // Loop through each cell and add TextField
            int counter = 0;
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 5; col++) {
                    counter++;
                    TextField textField = new TextField();
                    //set appearance
                    textField.setAlignment(javafx.geometry.Pos.CENTER);
                    textField.setPrefHeight(70.0);
                    textField.setPrefWidth(70.0);
                    textField.setMaxWidth(70.0);
                    textField.setMinHeight(70.0);
                    textField.setEditable(false);
                    textField.setText(" ");
                    textField.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                    textField.setId(String.valueOf(counter));
                    textField.getStyleClass().add("GridClass"); // Apply CSS class
                    textFieldGrid.add(textField, col, row);

                    //GridPane.setMargin(textField, new javafx.geometry.Insets(35));
                }
            }
            getWords();
            chosenWord = pickWord();
        }
        public void addLetter(ActionEvent event) {

            Button clickedButton = (Button) event.getSource();
            String buttonId = clickedButton.getId();

            TextField lastOfRow = (TextField) textFieldGrid.lookup("#" + ((1+rowIndex)*5));
            char c = lastOfRow.getCharacters().charAt(0);
            if(c != ' ') {
                rowFull = true;
            }
            else rowFull = false;

            if(!rowFull){
                placeTracker++;
                TextField tf = (TextField) textFieldGrid.lookup(("#" + placeTracker));
                tf.setText(buttonId);}
        }
        //logic for when backspace key is pressed
        public void backSpace(ActionEvent event){
            //check if backspace can even be pressed
            if(placeTracker != rowIndex*5) {
                if (placeTracker != 0) {
                    TextField tf = (TextField) textFieldGrid.lookup(("#" + placeTracker));
                    tf.setText(" ");
                }
                if (placeTracker > 0) {
                    placeTracker--;
                }
            }
        }

        //logic for when enter key is pressed
        public void enterKey(ActionEvent event){
            TextField lastOfRow = (TextField) textFieldGrid.lookup("#" + ((1+rowIndex)*5));
            char c = lastOfRow.getCharacters().charAt(0);
            //make sure row is full
            if(c != ' '){
                rowIndex++;
                // -- add code to look at word vs wordle in the future --
                rowFull = false;
                //declare all 5 text fields of the row
                TextField fir = (TextField) textFieldGrid.lookup("#" + (placeTracker - 4));
                TextField sec = (TextField) textFieldGrid.lookup("#" + (placeTracker - 3));
                TextField thr = (TextField) textFieldGrid.lookup("#" + (placeTracker - 2));
                TextField frt = (TextField) textFieldGrid.lookup("#" + (placeTracker - 1));

                StringBuilder sb = new StringBuilder().append(fir.getCharacters().charAt(0)).append(sec.getCharacters().charAt(0)).append(thr.getCharacters().charAt(0)).append(frt.getCharacters().charAt(0)).append(c);
                finalString = sb.toString();

                System.out.println("FinalString: " +  finalString);
                compareWords(finalString);
            }
        }

        private void getWords(){
            int counter = 0;
            try {
                //I do not know why i have to add "target/classes" but it works and just "text.txt" does not
                File inputStream = new File("target/classes/text.txt");
                Scanner s = new Scanner(inputStream);

                while (s.hasNextLine()) {
                    String data = s.nextLine();
                    words[counter] = data;
                    counter++;

                }
                System.out.println("Successfully imported  " + counter + " words.");
                s.close();
            }
            catch (FileNotFoundException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        private String pickWord(){
            int random =  (int) (Math.random()*2316);
            chosenWord = words[random];
            System.out.println("Cheat: " + chosenWord);
            return chosenWord.toUpperCase();
        }

        private String[] compareWords(String s){
            //create string array
            String colors[] = new String[5];
            //make default blank
            Arrays.fill(colors, "Blank");

            //compare each word and fill in colors
            for(int i = 0; i<5; i++){
                for(int j = 0; j<5; j++){
                    if(s.charAt(i) == chosenWord.charAt(j)){
                        if(i == j){
                            colors[i] = "Green";
                        }
                        else if(!(colors[i].equals("Green"))){
                            colors[i] = "Yellow";
                        }
                    }
                }
            }
            //Now that colors have been determined, change the background color of text box and keyboard letters.
            for (int i = 0; i<5; i++) {
                if (colors[i].equals("Green")) {
                //set letter on keyboard to green
                    Button btt = (Button) Anchor.lookup("#" + (s.charAt(i)));
                    btt.getStyleClass().add("GreenButton");
                //set boxes to green
                    TextField box = (TextField) textFieldGrid.lookup("#" + ((rowIndex-1)*5 + i + 1));
                    box.getStyleClass().add("GreenButton");
                }
                else if (colors[i].equals("Yellow")) {
                    //set letter on keyboard to green
                    Button btt = (Button) Anchor.lookup("#" + (s.charAt(i)));
                    btt.getStyleClass().add("YellowButton");
                    //set boxes to green
                    TextField box = (TextField) textFieldGrid.lookup("#" + ((rowIndex-1)*5 + i + 1));
                    box.getStyleClass().add("YellowButton");
                }
                else {
                    //set letter on keyboard to green
                    Button btt = (Button) Anchor.lookup("#" + (s.charAt(i)));
                    btt.getStyleClass().add("DarkButton");
                    //set boxes to green
                    TextField box = (TextField) textFieldGrid.lookup("#" + ((rowIndex-1)*5 + i + 1));
                    box.getStyleClass().add("DarkButton");
                }
            }
            return colors;
        }

    }
