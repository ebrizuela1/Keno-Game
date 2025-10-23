import java.util.ArrayList;

public class KenoController {
    private KenoView view;
    private GameEngine model;
    private MainApp main;
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
    public void handleNumberSelection(Integer buttonNum) {
        // Check if the number exits in selectedNums
        // -- If it does, we remove the number from selectedNumbers
        // -- Else add the number to the selectedNumbers
        if(this.model.getUserList().contains(buttonNum)){
            this.model.removeNumFromUser(buttonNum);
        }else{
            this.model.addNumFromUser(buttonNum);
        }

    }

    public void showList() {
        // Call the Game engine method to show user numList
        this.model.getUserList();
    }


    public void handleSubmit() {
        this.model.submitKenoTicket();


    }

    public void handleNumSpots(Integer value) {
        this.model.setUserNumSpots(value);
    }
}
