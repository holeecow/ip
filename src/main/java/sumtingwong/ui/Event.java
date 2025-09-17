package sumtingwong.ui;

/**
 * A task that spans a period with a start and end.
 */
public class Event extends Task{

    protected String from;
    protected String to;

    /**
     * Creates an event task with start and end descriptors.
     *
     * @param description task description
     * @param from start descriptor
     * @param to end descriptor
     * @param isDone completion state
     */
    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        assert from != null : "Event 'from' field cannot be null";
        assert to != null : "Event 'to' field cannot be null";
        assert !from.trim().isEmpty() : "Event 'from' field cannot be empty";
        assert !to.trim().isEmpty() : "Event 'to' field cannot be empty";
        
        this.from = from;
        this.to = to;
    }

    /**
     * Serializes this event into the storage line format.
     *
     * @return line in the form: E | <isDone> | <description> | <from> | <to> | <tags>
     */
    public String toFileFormat() {
        return "E" + " | " + super.isDone + " | " + description + " | " + this.from + " | " + this.to + " | " + getTagsString();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
