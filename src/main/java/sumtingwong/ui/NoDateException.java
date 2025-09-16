package sumtingwong.ui;

/**
 * Thrown when an event command is missing date/time parts (e.g., /from or /to).
 */
public class NoDateException extends SumTingWongException {
    public NoDateException() {
        super("Event must specify both /from and /to");
    }
}
