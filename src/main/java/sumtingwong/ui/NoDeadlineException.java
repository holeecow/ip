package sumtingwong.ui;

/**
 * Thrown when a deadline command is missing the /by section or value.
 */
public class NoDeadlineException extends RuntimeException {
    /**
     * Creates the exception indicating missing /by part in a deadline command.
     */
    public NoDeadlineException() {
        super("No deadline found");
        System.out.println("WHERE IS YOUR DEADLINE?");
    }
}
