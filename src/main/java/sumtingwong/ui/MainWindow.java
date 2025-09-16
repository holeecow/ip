package sumtingwong.ui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private SumTingWong sumTingWong;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userpic.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/sumtingwong.jpg"));

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up the scroll pane to automatically scroll to the bottom when new content is added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the SumTingWong chatbot instance into this controller.
     * Also displays the initial welcome message.
     *
     * @param d the SumTingWong instance to use for processing user input
     */
    public void setSumTingWong(SumTingWong d) {
        assert d != null : "SumTingWong instance cannot be null";
        
        sumTingWong = d;
        String welcomeMessage = getDivider() + "Hello! I'm SumTingWong\n"
                + "What can I do for you? -.-\n" + getDivider();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(welcomeMessage, dukeImage)
        );
    }

    private static String getDivider() {
        return "------------------------------- \n";
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing the bot's reply,
     * then appends them to the dialog container. Clears the user input after processing.
     * If the user types "bye", the application will exit.
     */
    @FXML
    private void handleUserInput() {
        assert sumTingWong != null : "SumTingWong instance must be set before handling input";
        
        String input = userInput.getText();
        assert input != null : "User input should not be null";
        
        String response = sumTingWong.getResponse(input);
        assert response != null : "Response from SumTingWong should not be null";
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}