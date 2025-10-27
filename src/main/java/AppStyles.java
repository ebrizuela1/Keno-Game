/**
 * A central class to hold all JavaFX style strings for the Keno application.
 * This ensures a consistent look and feel and makes styles easy to update.
 */
public final class AppStyles {

    private AppStyles() {}

    // --- Scene Backgrounds ---

    public static final String WELCOME_BACKGROUND =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #649173, #DBD5A4);";

    public static final String GAME_BACKGROUND_DEFAULT =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #ffcc00, #ff8c00)";

    public static final String GAME_BACKGROUND_DIFFERENT =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #00008B, #98AFC7);";

    // --- Text Styles ---

    public static final String KENO_TITLE =
            "-fx-fill: white; -fx-font-size: 100; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0.5, 0, 2);";

    public static final String PLAYER_STATS_HEADER =
            "-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: white;";

    public static final String PLAYER_STATS_BALANCE =
            "-fx-font-size: 18px; -fx-text-fill: white;";


    // --- UI Controls & Containers ---

    public static final String PLAYER_STATS_BOX =
            "-fx-background-color: rgba(0, 0, 0, 0.2); " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 20px;";

    public static final String CONTROL_BUTTON =
            "-fx-background-color: #E0E0E0; " +
                    "-fx-text-fill: #333333; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);";

    public static final String WELCOME_BUTTON =
            CONTROL_BUTTON + "-fx-font-size: 24px; -fx-padding: 10 20;";


    // --- Number Grid Button States --

    private static final String NUMBER_BUTTON_BASE =
            "-fx-background-radius: 10; " +
                    "-fx-pref-width: 40px; " +
                    "-fx-pref-height: 40px; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-border-radius: 10; " +
                    "-fx-border-width: 3px; ";

    public static final String NUMBER_BUTTON =
            NUMBER_BUTTON_BASE +
                    "-fx-background-color: #555555; -fx-border-color: transparent;";

    /**
     * User has clicked and selected this number.
     */
    public static final String NUMBER_BUTTON_SELECTED =
            NUMBER_BUTTON_BASE +
                    "-fx-background-color: #007bff; -fx-border-color: #0056b3;";

    public static final String NUMBER_BUTTON_WIN =
            NUMBER_BUTTON_BASE +
                    "-fx-background-color: #ffc107; " +
                    "-fx-text-fill: black; " +
                    "-fx-border-color: #e0a800;";

    public static final String NUMBER_BUTTON_MATCH =
            NUMBER_BUTTON_BASE +
                    "-fx-background-color: #28a745; " +
                    "-fx-border-color: #155724;";

    public static final String PLAYERSTYLE = "-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: white;";
    public static final String BALANCESTYLE = "-fx-font-size: 18px; -fx-text-fill: white;";
}