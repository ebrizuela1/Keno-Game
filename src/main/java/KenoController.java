import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.scene.control.Button;


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
        if(!this.model.isGameActive()){
            this.model.setGameActive(true);
        }
        int matches = this.model.submitKenoTicket();
        ArrayList<Integer> winners = this.model.getWinningNumbers();
        int remaining = this.model.getDrawingsRemaining();

//        this.view.animateDraw(winners, matches, remaining)
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


        // DEBUG 2: Does the map from the view actually have buttons in it?

        for(Integer number : autoNums){
            Button numberToHighlight = map.get(number);
            if (numberToHighlight != null){
                numberToHighlight.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF00; " +
                        "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
            }
        }

    }
}
