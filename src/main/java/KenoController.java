
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
        if(model.getUser().getSelectedNumbers().contains(buttonNum)){
            this.model.getUser().removeNum(buttonNum);
        }else{
            this.model.getUser().addNum(buttonNum);
        }

    }

    public void showList() {
        // Call the Game engine method to show user numList
        this.model.userList();
    }


}
