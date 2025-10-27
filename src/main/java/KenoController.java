import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class KenoController{
    KenoView view;
    GameEngine model;
    MainApp main;

    KenoController(KenoView view, GameEngine model, MainApp main){
        this.view = view;
        this.model = model;
        this.main = main;
    }
    void handleMenuScene(){
        this.main.switchToMenu();
    }
    void handleWelcomeScene(){
        this.main.switchToWelcome();
    }
    void handleGameScene(){
        this.main.switchToGame();
    }

    // Need to implement selectedNumber.size() <= numSpots
    public void handleNumberSelection(Integer buttonNum, Button button) {
        // If game is Active we SHOULD NOT update user selected numbers
        if (this.model.isGameActive()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Game is Active");
            alert.setHeaderText("Cannot unselect");
            alert.setContentText("You cannot change your selections once the game has started");
            alert.showAndWait();
            return;
        }
        // Check if the number exits in selectedNums
        // -- If it does, we remove the number from selectedNumbers
        // -- Else add the number to the selectedNumbers
        if(this.model.getUserList().contains(buttonNum)){
            this.model.removeNumFromUser(buttonNum);
            button.setStyle("-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                    "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
        }else if (this.model.getUserList().size() < this.model.getUserNumSpots()){
            this.model.addNumFromUser(buttonNum);
            button.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF00; " +
                    "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
        }
    }

    public void showList() {
        // Call the Game engine method to show user numList
        this.model.getUserList();
    }


    public void handleSubmit() {

        ArrayList<Integer> matches = this.model.submitKenoTicket();
        this.handleDisplayWinning(matches);
    }

    public void handleNumSpots(int num) {
        this.model.setUserNumSpots(num);
    }

    public void handleNumDrawings(int num) {
        this.model.setUserNumDrawings(num);
    }

    public int getSelectedNumberCount() {
        return this.model.getUserList().size();
    }

    public int getNumSpots() {
        return this.model.getUserNumSpots();
    }

    public void handleQuickPick(){
        // should not be able to quickpick when game is active
        if (this.model.isGameActive()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot use Auto Select");
            alert.setHeaderText("Cannot use Auto Select");
            alert.setContentText("You cannot use auto select when the game is session.");
            alert.showAndWait();
            return;
        }
        Map<Integer, Button> map = this.view.getNumberButtons();
        // Unhighlight all numbers
        ArrayList<Integer> userNumbers = this.model.getUserList();
        for (Integer number : userNumbers){
            Button unhighlight = map.get(number);
            unhighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                    "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
        }
        this.model.user.clearSelections();

        // Set the numbers while returning it so we can iterate and highlight in KenoView
        ArrayList<Integer> autoNums = new ArrayList<Integer>(this.model.setUserNums());

        for(Integer number : autoNums){
            Button numberToHighlight = map.get(number);
            if (numberToHighlight != null){
                numberToHighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF00; " +
                        "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
            }
        }

    }

    public void handleDisplayWinning(ArrayList<Integer> matches) {
        Map<Integer, Button> map = this.view.getNumberButtons();
        ArrayList<Integer> winningNumbers = this.model.getWinningNumbers();

        // Use atomic Integer to track our loop (cannot use int or Integer with TimeLine)
        AtomicInteger counter = new AtomicInteger(0);
        // Timeline will trigger every 1000 millis (1 second)
        Timeline highlighter = new Timeline(
                // Logic that will run every second
                new KeyFrame(Duration.millis(300), event ->{
                    Integer number = winningNumbers.get(counter.getAndIncrement());
                    Button winningHighlight = map.get(number);

                    // Actual Highlighting logic
                    if (this.model.getUserList().contains(number)){
                        winningHighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF00; -fx-border-color: #9234eb;" +
                                "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white; -fx-border-width: 3px; -fx-border-radius : 10;");
                    }else{
                        winningHighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #9234eb; " +
                                "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
                    }
                })
        );
        // Tell the highlighter to run 20 times
        highlighter.setCycleCount(winningNumbers.size());
        highlighter.setOnFinished(event -> {
            Platform.runLater(() -> {
                // 1. Calculate winnings and update balance
                int value = this.addWinnings(matches);

                // 2. Count down the drawing
                this.model.user.decNumDrawingsRemaining();
                System.out.println("Number of Drawings left: " + this.model.getDrawingsRemaining());

                if (this.model.getDrawingsRemaining() > 0) {
                    // ----- MORE DRAWINGS LEFT -----
                    Alert drawAlert = new Alert(Alert.AlertType.INFORMATION);
                    drawAlert.setTitle("Draw Results");
                    drawAlert.setHeaderText("Winnings this round: $" + value);
                    drawAlert.setContentText("Your new balance is: $" + this.model.user.getBalance());
                    drawAlert.showAndWait();
                    this.view.getContinueButton().setDisable(false);

                } else {
                    // ----- LOOP IS FINISHED -----
                    Alert overviewAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    overviewAlert.setTitle("Game Over");
                    overviewAlert.setHeaderText("All draws complete!");
                    overviewAlert.setContentText("Your final balance is: $" + this.model.user.getBalance() + "\n\nPlay again?");
                    overviewAlert.showAndWait();
                    // Re-enable *all* controls for a new game.
                    System.out.println("Game is done, loop is done");
                    this.view.getContinueButton().setDisable(false);
                    this.view.getNumSpotsDropdown().setDisable(false);
                    this.view.getAutoSelectButton().setDisable(false);
                    this.view.getNumDrawingDropdown().setDisable(false);
                    this.setGameActive(false); // Game is no longer active
                    this.model.resetGame();
                    this.resetGrid();
                }
            });
        });

        highlighter.play(); // Run the loop
    }

    private void resetGrid() {
        Map<Integer, Button> map = this.view.getNumberButtons();
        for(int i = 1 ; i < 81 ; i++){
            Button resetButton = map.get(i);
            resetButton.setStyle("-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                    "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
        }
    }

    public void handleClearWinning() {
        Map<Integer, Button> map = this.view.getNumberButtons();
        if (this.model.getWinningNumbers() == null){return;}
        ArrayList<Integer> winningNumbers = this.model.getWinningNumbers();

        System.out.println("Winning array" + winningNumbers);
        for (Integer number : winningNumbers){
            Button unhighlight = map.get(number);

            if (this.model.getUserList().contains(number)){
                // Set Back to just green
                unhighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF00; " +
                        "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
            }else{
                System.out.println("Cleared : "+ number);
                unhighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                        "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
            }
        }
    }

    public int addWinnings(ArrayList<Integer> matches) {
        Label playerBalance = this.view.getBalanceLabel();
        Integer value = 0;
        if (matches == null){
            System.out.println("No Winnings");
        }else{
            System.out.println("Matches" + matches);
            for (Integer number : matches){
                System.out.println(number);

            }

            int numMatches = matches.size();
            switch(this.model.getUserNumSpots()){

                case 1: {
                    // numSpots : 1 ; Matches Made 1
                    // Add $2 to balance
                    if (numMatches == 1){
                        value = 2;
                    }
                    break;
                }
                case 2: {
                    if (numMatches == 2){value = 11;}
                    break;
                }
                case 4:{
                    if (numMatches == 4){value = 75;}
                    else if(numMatches == 3){ value = 5;}
                    else if(numMatches == 2){value = 1;}
                    break;
                }
                case 8:{
                    if (numMatches == 8){value = 10000;}
                    else if (numMatches == 7){value = 750;}
                    else if (numMatches == 6){value = 50;}
                    else if (numMatches == 5){value = 12;}
                    else if (numMatches == 4){value = 2;}
                    break;
                }
                case 10:{
                    if (numMatches == 10){value = 100000;}
                    else if(numMatches == 9){value = 4250;}
                    else if(numMatches == 8){value = 450;}
                    else if(numMatches == 7){value = 40;}
                    else if(numMatches == 6){value = 15;}
                    else if(numMatches == 5){value = 2;}
                    else if(numMatches == 0){value = 5;}
                    break;
                }
            }
            this.model.addMoney(value);

            playerBalance.setText("$" + this.model.user.getBalance());

        }
        return value;
    }

    public void setGameActive(boolean b) {
        this.model.setGameActive(b);
    }
}
