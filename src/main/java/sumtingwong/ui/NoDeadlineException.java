package sumtingwong.ui;

/**
 * Thrown when a deadline command is missing the /by section or value.
 */
public class NoDeadlineException extends SumTingWongException {
    /**
     * Creates the exception indicating missing /by part in a deadline command.
     */
    public NoDeadlineException() {
        super("Deadline must be specified using /by");
    }
}
