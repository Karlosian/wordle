    package com.example.demo1;

    import java.io.File;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.event.ActionEvent;
    import javafx.fxml.Initializable;
    import javafx.scene.control.TextField;
    import javafx.scene.control.Button;
    import javafx.scene.input.KeyCode;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.VBox;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    import java.io.FileNotFoundException;

    import java.io.IOException;
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

        @FXML
        private VBox RootBox;
        private static int placeTracker = 0;

        private static int rowIndex = 0;

        private String[] words = new String[5757];

        private static boolean rowFull = false;

        private String chosenWord= new String();

        private String finalString = " ";
        private static boolean letWarn = true;
        private static boolean victory = false;

        private static int bestScore = 999;

        private static String oldWord = new String();


        //keypress logic
        public void addKeyLogic(){
            Anchor.setOnMouseClicked(e -> Anchor.requestFocus());
            Anchor.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if(!victory) {
                    if (keyCode == KeyCode.BACK_SPACE) {
                        backSpace();
                    } else if (keyCode == KeyCode.ENTER) {
                        //have to do try and catch in case of error
                        try {
                            enterKey();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if(!event.getText().isEmpty() && Character.isLetter(event.getText().charAt(0))) {
                        String keyText = event.getText();
                        addLetterKey(keyText.charAt(0));
                    }
                }
                else{
                    playAgain();
                }
            });
        }


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
                    textField.setOnMouseClicked(e -> Anchor.requestFocus());
                    //GridPane.setMargin(textField, new javafx.geometry.Insets(35));
                }
            }
            getWords();
            chosenWord = pickWord();
            addKeyLogic();

        }
        //logic for clicking button to add letter
        public void addLetter(ActionEvent event) {
            if (rowIndex < 6) {
                Button clickedButton = (Button) event.getSource();
                String buttonId = clickedButton.getId();

                TextField lastOfRow = (TextField) textFieldGrid.lookup("#" + ((1 + rowIndex) * 5));
                char c = lastOfRow.getCharacters().charAt(0);
                if (c != ' ') {
                    rowFull = true;
                } else rowFull = false;

                if (!rowFull) {
                    placeTracker++;
                    TextField tf = (TextField) textFieldGrid.lookup(("#" + placeTracker));
                    tf.setText(buttonId);
                }
            }
        }
        //add letter after key press
        public void addLetterKey(char s) {
            if(Character.isLetter(s) && rowIndex<6) {
                TextField lastOfRow = (TextField) textFieldGrid.lookup("#" + ((1 + rowIndex) * 5));
                char c = lastOfRow.getCharacters().charAt(0);
                if (c != ' ') {
                    rowFull = true;
                } else rowFull = false;

                if (!rowFull) {
                    placeTracker++;
                    TextField tf = (TextField) textFieldGrid.lookup(("#" + placeTracker));
                    tf.setText((Character.toString(s)).toUpperCase());
                }
            }
        }

        //logic for when backspace key is pressed
        public void backSpace(){
            //check if backspace can even be pressed
            if(placeTracker != rowIndex*5 && rowIndex<6) {
                if (placeTracker != 0) {
                    TextField tf = (TextField) textFieldGrid.lookup(("#" + placeTracker));
                    tf.setText(" ");
                }
                if (placeTracker > 0) {
                    placeTracker--;
                }
            }
            //else{playAgain();}

        }

        //code for when enter is pressed on an unrecognized word
        public void notifyUser() {
            if (letWarn) {
                Button btt = (Button) Anchor.lookup("#ENTER");
                NotificationPopup.showNotification(RootBox, "Inputted word is not recognized");
                btt.setDisable(true);
                btt.getStyleClass().add("DarkButton");
                letWarn=false;
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(3), event -> {
                            btt.getStyleClass().remove("DarkButton");
                            btt.setDisable(false);
                            letWarn = true;
                        })
                );
                timeline.play();
            }
        }

        //logic for when enter key is pressed
        public void enterKey() throws IOException {
            if(rowIndex<6) {
                TextField lastOfRow = (TextField) textFieldGrid.lookup("#" + ((1 + rowIndex) * 5));
                char c = lastOfRow.getCharacters().charAt(0);
                //make sure row is full
                if (c != ' ') {
                    //declare all 5 text fields of the row
                    TextField fir = (TextField) textFieldGrid.lookup("#" + (placeTracker - 4));
                    TextField sec = (TextField) textFieldGrid.lookup("#" + (placeTracker - 3));
                    TextField thr = (TextField) textFieldGrid.lookup("#" + (placeTracker - 2));
                    TextField frt = (TextField) textFieldGrid.lookup("#" + (placeTracker - 1));

                    //make string
                    StringBuilder sb = new StringBuilder().append(fir.getCharacters().charAt(0)).append(sec.getCharacters().charAt(0)).append(thr.getCharacters().charAt(0)).append(frt.getCharacters().charAt(0)).append(c);
                    finalString = sb.toString();
                    if (isWord(finalString.toLowerCase())) {
                        rowFull = false;
                        rowIndex++;
                        compareWords(finalString);
                    } else {
                        notifyUser();
                    }
                }
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
            int random =  (int) (Math.random()*5758);
            chosenWord = words[random];
            System.out.println("Cheat: " + chosenWord);
            return chosenWord.toUpperCase();
        }

        private String[] compareWords(String s) throws IOException {
            //create string array
            String colors[] = new String[5];
            //make default blank
            Arrays.fill(colors, "Blank");
            int greenCount = 0;
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
            //verify yellows
            String tempWord = new String();
            for(int i = 0; i<5; i++){
                if(colors[i].equals("Green")){
                    tempWord +='*';
                }
                else{
                    tempWord += chosenWord.charAt(i);
                }
            }
            //replace incorrect yellows with blank
            String specificChar = new String();
            System.out.println(tempWord);
            for(int i = 0; i<5; i++){
                specificChar = String.valueOf(s.charAt(i));
                if(colors[i].equals("Yellow")){
                    if(!(tempWord.contains(specificChar))){
                        System.out.println("Blank");
                        colors[i] = "Blank";
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
                    box.getStyleClass().add("GreenText");
                    box.getStyleClass().remove("GridClass");
                    greenCount++;
                }
                else if (colors[i].equals("Yellow")) {
                    //set letter on keyboard to yellow
                    Button btt = (Button) Anchor.lookup("#" + (s.charAt(i)));
                    btt.getStyleClass().add("YellowButton");
                    //set boxes to yellow
                    TextField box = (TextField) textFieldGrid.lookup("#" + ((rowIndex-1)*5 + i + 1));
                    box.getStyleClass().add("YellowText");
                    System.out.println();

                }
                else {
                    //set letter on keyboard to dark
                    Button btt = (Button) Anchor.lookup("#" + (s.charAt(i)));
                    btt.getStyleClass().add("DarkButton");
                    //set boxes to dark
                    TextField box = (TextField) textFieldGrid.lookup("#" + ((rowIndex-1)*5 + i + 1));
                    box.getStyleClass().add("DarkText");
                }
            }
            if(greenCount == 5){
                System.out.println("You Won!");
                oldWord = chosenWord;
                bestScore = Math.min(bestScore, rowIndex);
                ShowWin.showWin(new Stage());
                victory = true;
            }
            if(rowIndex == 6 && greenCount != 5){
                System.out.println("You failed!");
                oldWord = chosenWord;
                ShowWin.showLose(new Stage());
                victory = true;
            }
            return colors;
        }

        //check if word is recognized
        public boolean isWord(String word){
            String tempWord = new String();
            for(int i = 0; i<5757; i++ ){
                 if (word.equals(words[i])){
                  return true;
                 }
            }
            return false;
        }

        public void playAgain(){
            placeTracker = 0;
            rowIndex = 0;
            for (int i =1; i<=30; i++){
                //remove letters
                TextField tf = (TextField) textFieldGrid.lookup(("#" + i));
                tf.setText(" ");
                //remove colors
                tf.getStyleClass().clear();
                tf.getStyleClass().add("GridClass");
                tf.getStyleClass().add("text-input");
                tf.getStyleClass().add("text-field");
            }
            for(char i = 'A'; i<='Z'; i++  ){
                Button btt = (Button) Anchor.lookup("#" + (i));
                btt.getStyleClass().clear();
                btt.getStyleClass().add("KeyButton");
                btt.getStyleClass().add("button");
            }
            chosenWord = pickWord();
            victory = false;

        }
        public static int getBestScore(){
            if(bestScore != 999){
            int i = bestScore;
            return i;}
            else{
                return 0;
            }
        }
        public static String getWord(){
            return oldWord;
        }

    }
