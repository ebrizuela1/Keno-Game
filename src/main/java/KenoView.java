

import javafx.application.Application;
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Tie the view to the controller
        this.controller = new KenoController(this);
        //Set Scene
        primaryStage.setScene(this.buildWelcomeScene());

        //Set Title
        primaryStage.setTitle("Keno App");
        // Display output
        primaryStage.show();
    }

    // A method that Controller will Call
    public void switchToMenu(){
        primaryStage.setScene(this.buildMenuScene());
    }

    public void switchToWelcome(){
        primaryStage.setScene(this.buildWelcomeScene());
    }
}
