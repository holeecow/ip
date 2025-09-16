package sumtingwong.ui;

/**
 * Thrown when an unrecognized command keyword is encountered.
 */
public class UnknownEventException extends SumTingWongException {

    public UnknownEventException(String userInput) {
        super("Unknown sumtingwong.ui.Event Type");
        assert userInput != null : "User input for unknown command cannot be null";
        System.out.println("What kind of event is " + userInput + "?????");
    }
}
