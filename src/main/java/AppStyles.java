public final class AppStyles {
    private AppStyles() {}

    /**
     * Blue gradient background for the welcome scene.
     */
    public static final String BACKGROUND_GRADIENT =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #2a5298, #1e3c72);";

    /**
     * Large white text for the "KENO" title.
     */
    public static final String KENO_TITLE =
            "-fx-fill: white; -fx-font-size: 100; -fx-font-weight: bold;";

    /**
     * Main style for large, green action buttons like "START" and "Menu".
     */
    public static final String PRIMARY_BUTTON =
            "-fx-background-color: #4CAF50; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 5; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1); " +
                    "-fx-font-size: 24px; ";

    /**
     * Style for the 80 individual number buttons in the game grid.
     */
    public static final String NUMBER_BUTTON =
            "-fx-background-radius: 10; -fx-background-color: #ff4b19; " +
                    "-fx-pref-width: 40px; -fx-pref-height: 40px; -fx-text-fill: white;";
}