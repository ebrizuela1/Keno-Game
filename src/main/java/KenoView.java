import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;


public class KenoView {
    KenoController controller;

    private Map<Integer, Button> numberButtons;
    private GridPane grid;

    KenoView(){
        this.numberButtons = new HashMap<>();
        this.grid = new GridPane();
    }


    void setController(KenoController controller){
        this.controller = controller;
    }

    // Builds the welcome scene Keno, Menu
    Scene buildWelcomeScene(){
        String buttonStyling = "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 5; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1); " +
                "-fx-font-size: 24px; ";
        // Initialize the root of scene
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #2a5298, #1e3c72);");

        Text kenoText = new Text("KENO");
        kenoText.setStyle("-fx-fill: white; -fx-font-size: 100; -fx-font-weight: bold;");
        Button menuButton = new Button("Menu");
        Button startButton = new Button("START");

        // ==== Button Event Handlers ====
        menuButton.setOnAction(event -> {
            this.controller.handleMenuScene();
        });
        startButton.setOnAction(event -> {
            this.controller.handleGameScene();
        });

        startButton.setStyle(buttonStyling);
        menuButton.setStyle(buttonStyling);

        VBox CenterView = new VBox();
        CenterView.getChildren().addAll(kenoText,startButton);
        CenterView.setAlignment(Pos.CENTER);
        CenterView.setSpacing(20);
        root.setCenter(CenterView);

        // Menu Button
        root.setTop(menuButton);

        BorderPane.setMargin(menuButton, new Insets(10));

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
        // Add spacing/padding for all elements in grid
        grid.setVgap(3);
        grid.setHgap(3);

        // Navbar : Continue, numSpots, numDrawings, Auto continue, auto select nums
        HBox navBar = new HBox();

        // Continue
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(event -> {
            this.controller.handleSubmit();
        } );
;        // Exit : returns to welcome scene
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            this.controller.handleWelcomeScene();
        } );

        // Number of drawings
        ComboBox<Integer> numDrawingDropdown = new ComboBox<>();
        numDrawingDropdown.getItems().addAll(1,2,3);

        ComboBox<Integer> numSpotsDropdown = new ComboBox<>();
        Integer[] nums = {1,2,4,8,10};
        numSpotsDropdown.getItems().addAll(nums);
        numSpotsDropdown.setOnAction(event -> {
            int num = numSpotsDropdown.getValue();
            this.controller.handleNumSpots(num);
        });

        // Auto Select Button
        Button autoSelectNums = new Button("Auto Select");
        autoSelectNums.setOnAction(event->{
            controller.handleQuickPick(); });

        // Add all nav bar components to Hbox : navBar
        navBar.getChildren().addAll(exitButton, continueButton, numDrawingDropdown, numSpotsDropdown, autoSelectNums);

        // Setting the BorderPane
        root.setCenter(grid);
        root.setTop(navBar);


        // building the grid with 80 buttons
        int rows = 8;
        int cols = 10;
        for (int row = 0 ; row < rows ; row++){
            for (int col = 0 ; col < cols ; col++){
                String num = Integer.toString( row * cols + col + 1);
                Button button = new Button(num);
                button.setOnAction(event -> {
                    // Get the button number
                    Integer buttonNum = Integer.parseInt(button.getText());
                    numberButtons.put(buttonNum, button);
                    // Call handler when button is pressed
                    this.controller.handleNumberSelection(buttonNum, button);
                });
                button.setStyle("-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                        "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
                grid.add(button, col , row);
            }
        }
        return (new Scene(root, 700, 500));
    }
}
