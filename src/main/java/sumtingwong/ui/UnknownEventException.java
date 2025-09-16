package sumtingwong.ui;

/**
 * Thrown when an unrecognized command keyword is encountered.
 */
public class UnknownEventException extends SumTingWongException {

    public UnknownEventException(String userInput) {
        super("Unknown command: " + userInput);
        assert userInput != null : "Command cannot be null";
    }
}
