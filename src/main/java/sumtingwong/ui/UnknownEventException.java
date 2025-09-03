package sumtingwong.ui;

/**
 * Thrown when an unrecognized command keyword is encountered.
 */
public class UnknownEventException extends SumTingWongException {

    public UnknownEventException(String userInput) {
        super("Unknown sumtingwong.ui.Event Type");
        System.out.println("What kind of event is " + userInput + "?????");
    }
}
