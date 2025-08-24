public class NoDateException extends RuntimeException {
    public NoDateException() {
        super("No date found");
        System.out.println("i need a date for this event....");
    }
}
