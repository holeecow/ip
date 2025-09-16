package sumtingwong.ui;

/**
 * Thrown when a command requiring a description is issued without one.
 */
public class NoDescriptionException extends SumTingWongException {
    public NoDescriptionException() {
        super("Description cannot be empty");
    }
}
