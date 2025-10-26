import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class KenoView {
    KenoController controller;

    private Map<Integer, Button> numberButtons;

    KenoView(){
        this.numberButtons = new HashMap<>();
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
        // initializing all elements in view
        BorderPane root = new BorderPane();
        HBox navBar = new HBox();
        GridPane grid = new GridPane();
        Button continueButton = new Button("Continue");
        Button exitButton = new Button("Exit");
        Button autoSelect = new Button("Auto Select");
        ComboBox<Integer> numSpotsDropdown = new ComboBox<>();
        ComboBox<Integer> numDrawingDropdown = new ComboBox<>();
        HBox gameControls = new HBox(10, numDrawingDropdown, numSpotsDropdown, autoSelect, continueButton);
        Region space = new Region();

        //disable grid until number of spots are chosen and a grid button is pressed
        grid.setDisable(true);

        // Continue
        continueButton.setOnAction(event -> {
            int selectedCount = this.controller.getSelectedNumberCount();
            int spotsToPlay = this.controller.getNumSpots();

            if(selectedCount == spotsToPlay){
                this.controller.handleSubmit();
                numSpotsDropdown.setDisable(true);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Selection Incomplete");
                alert.setHeaderText("Not enough numbers selected.");
                alert.setContentText("You chose to play " + spotsToPlay + " spots, " +
                        "but you have only selected " + selectedCount + " numbers.");
                alert.showAndWait();
            }
        });
       // Exit : returns to welcome scene
        exitButton.setOnAction(event -> {
            this.controller.handleWelcomeScene();
        } );

        // implementing the number of spots dropdown
        Integer[] numSpots = {1,2,4,8,10};
        numSpotsDropdown.getItems().addAll(numSpots);
        numSpotsDropdown.setPromptText("Select Num Spots");
        numSpotsDropdown.setOnAction(event -> {
            int num = numSpotsDropdown.getValue();
            this.controller.handleNumSpots(num);
            grid.setDisable(false);
        });

        autoSelect.setOnAction(event->{
            this.controller.handleQuickPick();
        });

        // implementing the number of drawings drop down
        Integer[] numDrawing = {1,2,3,4};
        numDrawingDropdown.getItems().addAll(numDrawing);
        numDrawingDropdown.setPromptText("Select Num Drawing");
        numDrawingDropdown.setOnAction(event -> {
            int num = numDrawingDropdown.getValue();
            this.controller.handleNumDrawings(num);
        });

        // Add all nav bar components to Hbox : navBar
        navBar.getChildren().addAll(exitButton, space, gameControls);

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
                    numSpotsDropdown.setDisable(true);
                });
                button.setStyle("-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                        "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;");
                grid.add(button, col , row);
            }
        }

        //  ---------- STATIC STYLING BELOW ----------
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #ffcc00, #ff8c00);");
        String controlButtonStyle = "-fx-background-color: #B0B0B0; " +
                "-fx-text-fill: black; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 5;";
        continueButton.setStyle(controlButtonStyle);
        exitButton.setStyle(controlButtonStyle);
        numSpotsDropdown.setStyle(controlButtonStyle);
        numDrawingDropdown.setStyle(controlButtonStyle);
        autoSelect.setStyle(controlButtonStyle);
        gameControls.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(space, Priority.ALWAYS);
        // Setting the BorderPane
        root.setCenter(grid);
        root.setTop(navBar);
        // Add spacing/padding for all elements in grid
        grid.setVgap(3);
        grid.setHgap(3);
        grid.setAlignment(Pos.CENTER);
        // Navbar : Continue, numSpots, numDrawings, Auto continue, auto select nums
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(grid, Pos.CENTER);

        return (new Scene(root, 700, 500));
    }
}
