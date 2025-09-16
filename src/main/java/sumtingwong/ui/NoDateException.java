package sumtingwong.ui;

/**
 * Thrown when an event command is missing date/time parts (e.g., /from or /to).
 */
public class NoDateException extends RuntimeException {
    public NoDateException() {
        super("No date found");
        System.out.println("i need a date for this event....");
    }
}
