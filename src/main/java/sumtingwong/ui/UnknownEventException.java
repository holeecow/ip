package sumtingwong.ui;

/**
 * Thrown when an unrecognized command keyword is encountered.
 */
public class UnknownEventException extends SumTingWongException {

    /**
     * Creates an exception for an unknown command.
     *
     * @param userInput the unrecognized command keyword
     */
    public UnknownEventException(String userInput) {
        super("Unknown event type");
        System.out.println("What kind of event is " + userInput + "?????");
    }
}
