public class NoDeadlineException extends RuntimeException {
    public NoDeadlineException() {
        super("No deadline found");
        System.out.println("WHERE IS YOUR DEADLINE?");
    }
}
