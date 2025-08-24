public class UnknownEventException extends SumTingWongException {

    public UnknownEventException(String userInput) {
        super("Unknown Event Type");
        System.out.println("What kind of event is " + userInput + "?????");
    }
}
