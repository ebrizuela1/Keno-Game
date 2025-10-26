import java.awt.*;
import java.util.ArrayList;
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
        this.model.submitKenoTicket();

    }

    public void handleNumSpots(int num) {
        this.model.setUserNumSpots(num);
    }

    public void handleQuickPick() {
        this.model.setUserNums();
    }
}
