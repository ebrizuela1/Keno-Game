import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;

public class KenoView {
    KenoController controller;

    void setController(KenoController controller){
        this.controller = controller;
    }

    // Builds the welcome scene Keno, Menu
    Scene buildWelcomeScene(){
        // Initialize the root of scene
        BorderPane root = new BorderPane();

        Text kenoText = new Text("KENO");
        kenoText.setStyle("-fx-text-fill: white; -fx-font-size: 100; -fx-font-weight: bold;");
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

        ComboBox<Integer> numSpotsDropdown = new ComboBox<>();
        Integer[] nums = {1,2,4,8,10};
        numSpotsDropdown.getItems().addAll(nums);
        numSpotsDropdown.setOnAction(event -> {
            int num = numSpotsDropdown.getValue();
            this.controller.handleNumSpots(num);
        });
        root.setRight(numSpotsDropdown);

        // Setting the BorderPane
        root.setLeft(exitButton);
        root.setCenter(grid);
        root.setTop(continueButton);

        // TESTING BUTTON ONLY
        Button arrayTest = new Button("arrayTest");
        arrayTest.setOnAction(event -> {
            this.controller.showList();
        });

        //root.setRight(arrayTest);

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
