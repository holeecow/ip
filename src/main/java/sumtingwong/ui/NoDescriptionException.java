package sumtingwong.ui;

public class NoDescriptionException extends SumTingWongException {
    public NoDescriptionException() {
        super("No description found");
        System.out.println("yo dumbass, I need a DESCRIPTION!!!");
    }
}
