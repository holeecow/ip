package sumtingwong.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for SumTingWong using FXML.
 */
public class Main extends Application {

    private SumTingWong sumTingWong = new SumTingWong();

    /**
     * Starts the JavaFX application by loading the FXML layout and setting up the scene.
     *
     * @param stage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // set limit to window size
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setSumTingWong(sumTingWong);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}