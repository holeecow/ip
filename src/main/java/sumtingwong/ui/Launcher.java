package sumtingwong.ui;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * Application entry point that launches the JavaFX GUI.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}