import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* MainApp contains : Controller, View, Player, Game Engine
   KenoView contains : Controller
   KenoController contains : GameEngine, View, MainApp
   GameEngine contains : Player
*/
public class MainApp extends Application {
    private Stage primaryStage;
    private Scene welcomeScene;
    private Scene menuScene;
    private Scene gameScene;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // Create our view
        KenoView view = new KenoView();

        // Create player instance
        Player user = new Player();
        //Create our Game engine
        GameEngine model = new GameEngine(user);
        // Create our controller
        KenoController controller = new KenoController(view, model, this);
        // Inject the controller to the view
        view.setController(controller);

        // Build All scenes
        this.welcomeScene = view.buildWelcomeScene();
        this.menuScene = view.buildMenuScene();
        this.gameScene = view.buildGameScene();
        // Initial Scene : Welcome Scene
        primaryStage.setScene(this.welcomeScene);
        //Set Title
        primaryStage.setTitle("Keno App");
        // Display output
        primaryStage.show();
    }

    // methods that Controller will call
    public void switchToMenu(){
        primaryStage.setScene(menuScene);
    }
    public void switchToWelcome(){
        primaryStage.setScene(welcomeScene);
    }
    public void switchToGame(){
        primaryStage.setScene(gameScene);
    }
}
