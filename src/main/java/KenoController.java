
public class KenoController {
    private KenoView view;
    private Player user;
    KenoController(KenoView view, Player user){
        this.view = view;
        this.user = user;
    }
    void handleMenuScene(){
        this.view.switchToMenu();
    }
    void handleWelcomeScene(){
        this.view.switchToWelcome();
    }
    void handleGameScene(){
        this.view.switchToGame();
    }

    // Need to implement selectedNumber.size() <= numSpots
    public void handleNumberSelection(Integer buttonNum) {
        // Check if the number exits in selectedNums
        // -- If it does, we remove the number from selectedNumbers
        // -- Else add the number to the selectedNumbers
        if(user.getSelectedNumbers().contains(buttonNum)){
            this.user.removeNum(buttonNum);
        }else{
            this.user.addNum(buttonNum);
        }

    }

    public void showList() {
        System.out.println(this.user.getSelectedNumbers());
    }
}
