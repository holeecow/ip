package sumtingwong.ui;

/**
 * Thrown when a command requiring a description is issued without one.
 */
public class NoDescriptionException extends SumTingWongException {
    public NoDescriptionException() {
        super("No description found");
        System.out.println("yo dumbass, I need a DESCRIPTION!!!");
    }
}
