
public class KenoController {
    private KenoView view;
    KenoController(KenoView view){
        this.view = view;
    }
    void handleMenuScene(){
        this.view.switchToMenu();
    }
    void handleWelcomeScene(){
        this.view.switchToWelcome();
    }
}
