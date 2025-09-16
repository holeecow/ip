package sumtingwong.ui;

/**
 * Thrown when a find command is issued without a keyword.
 */
public class NoKeywordException extends SumTingWongException {
    public NoKeywordException() {
        super("Find command requires a search keyword");
    }
}
