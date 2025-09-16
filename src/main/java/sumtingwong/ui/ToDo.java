package sumtingwong.ui;

/**
 * A simple task without any date/time component.
 */
public class ToDo extends Task {

    /**
     * Creates a todo task.
     *
     * @param description description of the task
     * @param isDone whether the task is completed
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
        assert this.description != null : "ToDo description should be set by parent constructor";
    }

    /**
     * Serializes this todo into the storage line format.
     *
     * @return line in the form: T | `isDone` | `description`
     */
    public String toFileFormat() {
        return "T" + " | " + super.isDone + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
