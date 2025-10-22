

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
public class KenoView extends Application {

    private Stage primaryStage;
    private KenoController controller;
    private Scene welcomeScene;
    private Scene menuScene;
    private Scene gameScene;

    // Builds the welcome scene Keno, Menu
    Scene buildWelcomeScene(){

        // Initialize the root of scene
        BorderPane root = new BorderPane();

        Text kenoText = new Text("KENO");
        Button menuButton = new Button("Menu");
        Button startButton = new Button("START");

        // ==== Button Event Handlers ====
        menuButton.setOnAction(event -> {
            this.controller.handleMenuScene();
        });
        startButton.setOnAction(event -> {
            this.controller.handleGameScene();
        });

        // text : KENO
        root.setCenter(kenoText);

        // Start Button
        root.setBottom(startButton);

        // Menu Button
        root.setLeft(menuButton);

        return new Scene(root, 500, 500);
    }

    // Builds the Menu
    Scene buildMenuScene(){
        BorderPane root = new BorderPane();
        VBox menuOptions = new VBox();

        Button odds = new Button("Odds");
        Button rules = new Button("Rules");
        Button exit = new Button("Exit");
        menuOptions.getChildren().addAll(rules, odds, exit);

        // === Button Event Handlers
        exit.setOnAction(event -> {
            this.controller.handleWelcomeScene();
        });

        root.setCenter(menuOptions);
        menuOptions.setAlignment(Pos.CENTER);
        return (new Scene(root, 500, 500));
    }

    // Builds the Actual Game
    Scene buildGameScene(){
        BorderPane root = new BorderPane();

        GridPane grid = new GridPane();
        // Add spacing/padding for all elements in grid
        grid.setVgap(3);
        grid.setHgap(3);

        // Exit : returns to welcome scene
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            this.controller.handleWelcomeScene();
        });
        root.setLeft(exitButton);
        root.setCenter(grid);
        int rows = 8;
        int cols = 10;
        for (int row = 0 ; row < rows ; row++){
            for (int col = 0 ; col < cols ; col++){
                String num = Integer.toString( row * cols + col + 1);
                Button button = new Button(num);
                grid.add(button, col , row);
            }
        }
        return (new Scene(root, 500, 500));
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.welcomeScene = this.buildWelcomeScene();
        this.menuScene = this.buildMenuScene();
        this.gameScene = this.buildGameScene();

        // Tie the view to the controller
        this.controller = new KenoController(this);
        //Set Scene
        primaryStage.setScene(welcomeScene);

        //Set Title
        primaryStage.setTitle("Keno App");
        // Display output
        primaryStage.show();
    }

    // A method that Controller will Call
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
