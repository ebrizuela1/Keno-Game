
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
    //
    public void handleNumberSelection(Integer buttonNum) {
        this.user.appendNum(buttonNum);
    }

    public void showList() {
        System.out.println(this.user.selectedNumbers);
    }
}
