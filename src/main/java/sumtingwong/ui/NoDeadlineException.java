package sumtingwong.ui;

/**
 * Thrown when a deadline command is missing the /by section or value.
 */
public class NoDeadlineException extends RuntimeException {
    public NoDeadlineException() {
        super("No deadline found");
        System.out.println("WHERE IS YOUR DEADLINE?");
    }
}
