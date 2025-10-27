import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class KenoView {
    KenoController controller;
    private Label balanceLabel;
    private Button continueButton;
    private Button autoSelect;

    private Map<Integer, Button> numberButtons;
    private ComboBox<Integer> numSpotsDropdown;
    private ComboBox<Integer> numDrawingDropdown;
    private GridPane grid;

    KenoView(){
        this.numberButtons = new HashMap<>();
        this.numSpotsDropdown = new ComboBox<>();
        this.numDrawingDropdown = new ComboBox<>();
    }
    public Map<Integer, Button> getNumberButtons(){
        return this.numberButtons;
    }

    void setController(KenoController controller){
        this.controller = controller;
    }

    Label getBalanceLabel(){
        return this.balanceLabel;
    }

    // Builds the welcome scene Keno, Menu
    Scene buildWelcomeScene(){
        // Initialize the root of scene
        BorderPane root = new BorderPane();
        root.setStyle(AppStyles.WELCOME_BACKGROUND);

        Text kenoText = new Text("KENO");
        kenoText.setStyle(AppStyles.KENO_TITLE);

        Button menuButton = new Button("Menu");
        Button startButton = new Button("START");

        // ==== Button Event Handlers ====
        menuButton.setOnAction(event -> {
            this.controller.handleMenuScene();
        });
        startButton.setOnAction(event -> {
            this.controller.handleGameScene();
        });

        startButton.setStyle(AppStyles.WELCOME_BUTTON);
        menuButton.setStyle(AppStyles.CONTROL_BUTTON);

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
        root.setStyle(AppStyles.WELCOME_BACKGROUND);
        VBox menuOptions = new VBox(20);

        Button odds = new Button("Odds");
        odds.setStyle(AppStyles.CONTROL_BUTTON);
        Button rules = new Button("Rules");
        rules.setStyle(AppStyles.CONTROL_BUTTON);
        Button exit = new Button("Exit");
        exit.setStyle(AppStyles.CONTROL_BUTTON);

        menuOptions.getChildren().addAll(rules, odds, exit);

        rules.setOnAction(event -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Rules of Keno");
            alert.setHeaderText("How to Play Keno");
            
            String rulesText = "1. Choose how many spots (numbers) you want to play: 1, 4, 8, or 10.\n" +
                               "2. Choose how many drawings you want to play: 1 to 4.\n" +
                               "3. Select your numbers from the grid (1-80). You must select the exact amount of spots you chose.\n" +
                               "4. You can also use 'Auto Select' to have the game pick numbers for you.\n" +
                               "5. Press 'Continue' to start the draw. The game will randomly draw 20 numbers.\n" +
                               "6. You win by matching your numbers to the 20 numbers drawn by the game.";
            
            alert.setContentText(rulesText);
            alert.getDialogPane().setPrefSize(480, 320);
            alert.setResizable(true);
            alert.showAndWait();
        });

        odds.setOnAction(event -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Keno Odds & Payouts");
            alert.setHeaderText("Payouts (Based on North Carolina State Lottery)");

            String oddsText = "Payouts are based on a $1 wager.\n\n" +
                              "SPOT 1 (Match 1): $2\n\n" +
                              "SPOT 4 (Match 4): $75\n" +
                              "SPOT 4 (Match 3): $5\n" +
                              "SPOT 4 (Match 2): $1\n\n" +
                              "SPOT 8 (Match 8): $10,000\n" +
                              "SPOT 8 (Match 7): $450\n" +
                              "SPOT 8 (Match 6): $50\n" +
                              "SPOT 8 (Match 5): $12\n" +
                              "SPOT 8 (Match 4): $2\n\n" +
                              "SPOT 10 (Match 10): $100,000\n" +
                              "SPOT 10 (Match 9): $2,500\n" +
                              "SPOT 10 (Match 8): $250\n" +
                              "SPOT 10 (Match 7): $40\n" +
                              "SPOT 10 (Match 6): $10\n" +
                              "SPOT 10 (Match 5): $2\n" +
                              "SPOT 10 (Match 0): $5";

            alert.setContentText(oddsText);
            // Make the alert resizable
            alert.getDialogPane().setPrefSize(480, 400); 
            alert.setResizable(true);
            
            alert.showAndWait();
        });

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
        HBox navBar = new HBox(10);
        grid = new GridPane();
        this.continueButton = new Button("Continue");
        Button exitButton = new Button("Exit");
        this.autoSelect = new Button("Auto Select");
        Button styleButton = new Button("Change Style");
        AtomicBoolean isDefaultStyle = new AtomicBoolean(true);

        HBox gameControls = new HBox(
                10,
                this.numDrawingDropdown,
                this.numSpotsDropdown,
                this.autoSelect,
                this.continueButton,
                styleButton
        );
        Region space = new Region();

        //disable grid until number of spots are chosen and a grid button is pressed
        grid.setDisable(true);

        // Continue
        this.continueButton.setOnAction(event -> {
            // Clear all the old winning numbers
            this.continueButton.setDisable(true);
            this.controller.handleClearWinning();

            // Check if the game is already active
            if (this.controller.model.isGameActive()){
                this.controller.handleSubmit();
            }else{
                int selectedCount = this.controller.getSelectedNumberCount();
                int spotsToPlay = this.controller.getNumSpots();
                int numDrawings = this.controller.model.user.getNumDrawings();

                if(selectedCount == spotsToPlay && numDrawings > 0 && spotsToPlay > 0){
                    // Set the game to active so we cannot change user selected numbers
                    this.controller.setGameActive(true);

                    numSpotsDropdown.setDisable(true);
                    numDrawingDropdown.setDisable(true);
                    this.autoSelect.setDisable(true);

                    this.controller.handleSubmit();
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Selection Incomplete");
                    alert.setHeaderText("Not enough numbers selected.");
                    alert.setContentText("You chose to play " + spotsToPlay + " spots, " +
                            "but you have only selected " + selectedCount + " numbers.");
                    alert.showAndWait();
                    this.continueButton.setDisable(false);
                }
            }


        });

        styleButton.setOnAction(event -> {
            if (isDefaultStyle.get()) {
                root.setStyle(AppStyles.GAME_BACKGROUND_DIFFERENT);
                isDefaultStyle.set(false);
            } else {
                root.setStyle(AppStyles.GAME_BACKGROUND_DEFAULT);
                isDefaultStyle.set(true);
            }
        });

       // Exit : returns to welcome scene
        exitButton.setOnAction(event -> {
            this.controller.handleWelcomeScene();
        } );

        // implementing the number of spots dropdown
        Integer[] numSpots = {1,2,4,8,10};
        this.numSpotsDropdown.getItems().addAll(numSpots);
        this.numSpotsDropdown.setPromptText("Select Num Spots");
        this.numSpotsDropdown.setOnAction(event -> {
            int num = numSpotsDropdown.getValue();
            this.controller.handleNumSpots(num);
            grid.setDisable(false);
        });

        this.autoSelect.setOnAction(event->{
            this.controller.handleQuickPick();
        });

        // implementing the number of drawings drop down
        Integer[] numDrawing = {1,2,3,4};
        this.numDrawingDropdown.getItems().addAll(numDrawing);
        this.numDrawingDropdown.setPromptText("Select Num Drawing");
        this.numDrawingDropdown.setOnAction(event -> {
            int num = this.numDrawingDropdown.getValue();
            this.controller.handleNumDrawings(num);
        });

        // Add all nav bar components to Hbox : navBar
        navBar.getChildren().addAll(exitButton, space, gameControls);

        VBox playerStats = new VBox();
        playerStats.setMaxSize(50,50);
        Text Player = new Text("Player");
        this.balanceLabel = new Label("$" + this.controller.getBalance());
        playerStats.getChildren().addAll(Player, this.balanceLabel);
        // building the grid with 80 buttons
        int rows = 8;
        int cols = 10;
        for (int row = 0 ; row < rows ; row++){
            for (int col = 0 ; col < cols ; col++){
                String num = Integer.toString( row * cols + col + 1);
                Button button = new Button(num);
                // Get the button number
                Integer buttonNum = Integer.parseInt(button.getText());
                numberButtons.put(buttonNum, button);
                button.setOnAction(event -> {
                    // Call handler when button is pressed ONLY when game is not active
                    this.controller.handleNumberSelection(buttonNum, button);
                    numSpotsDropdown.setDisable(true);
                });
                button.setStyle(AppStyles.NUMBER_BUTTON);
                grid.add(button, col , row);
            }
        }

        //  ---------- STATIC STYLING BELOW ----------
        root.setStyle(AppStyles.GAME_BACKGROUND_DEFAULT);
        this.continueButton.setStyle(AppStyles.CONTROL_BUTTON);
        exitButton.setStyle(AppStyles.CONTROL_BUTTON);
        playerStats.setStyle(AppStyles.PLAYER_STATS_BOX); // playerStatsStyle
        balanceLabel.setStyle(AppStyles.PLAYER_STATS_BALANCE); // balanceStyle
        Player.setStyle(AppStyles.PLAYER_STATS_HEADER); // playerStyle
        numSpotsDropdown.setStyle(AppStyles.CONTROL_BUTTON);
        numDrawingDropdown.setStyle(AppStyles.CONTROL_BUTTON);
        this.autoSelect.setStyle(AppStyles.CONTROL_BUTTON);
        styleButton.setStyle(AppStyles.CONTROL_BUTTON);

        gameControls.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(space, Priority.ALWAYS);
        // Setting the BorderPane
        root.setCenter(grid);
        root.setTop(navBar);
        root.setLeft(playerStats);
        BorderPane.setMargin(playerStats, new Insets(10, 5, 10, 10));
        // Add spacing/padding for all elements in grid
        grid.setVgap(3);
        grid.setHgap(3);
        grid.setAlignment(Pos.CENTER);

        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(grid, Pos.CENTER);

        return (new Scene(root, 700, 500));
    }

    public Button getContinueButton(){
        return this.continueButton;
    }

    public Button getAutoSelectButton(){ // <-- ADD THIS
        return this.autoSelect;
    }

    public Node getNumSpotsDropdown() {
        return this.numSpotsDropdown;
    }

    public Node getNumDrawingDropdown() {
        return this.numDrawingDropdown;
    }

    public void resetGameUI(){
        for (Button btn : numberButtons.values()) {
            btn.setStyle(AppStyles.NUMBER_BUTTON);
        }
        grid.setDisable(true);
        numSpotsDropdown.setDisable(false);
        numDrawingDropdown.setDisable(false);
        autoSelect.setDisable(false);
        continueButton.setDisable(false);

        numSpotsDropdown.setValue(null);
        numDrawingDropdown.setValue(null);

        balanceLabel.setText("$" + controller.getBalance());
    }
}
