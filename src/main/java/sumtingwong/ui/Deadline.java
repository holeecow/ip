package sumtingwong.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A task with a deadline, which can be represented either as a free-form
 * string or a structured date/time pair.
 */
public class Deadline extends Task {

    protected String by;

    protected LocalDate date;

    protected LocalTime time;

    /**
     * Creates a deadline with a free-form string representation (e.g. "tomorrow 6pm").
     *
     * @param description task description
     * @param by free-form deadline string
     * @param isDone completion state
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Creates a deadline with structured date and time.
     *
     * @param description task description
     * @param time the time component
     * @param date the date component
     * @param isDone completion state
     */
    public Deadline(String description, LocalTime time, LocalDate date, boolean isDone) {
        super(description, isDone);
        this.time = time;
        this.date = date;
        this.by = "";
    }

    /**
     * Serializes this deadline into the storage line format.
     *
     * @return line in the form: D | `isDone` | `description` | `by`
     */
    public String toFileFormat() {
        return "D" + " | " + super.isDone + " | " + description + " | " + this.by;
    }

    @Override
    public String toString() {
        return this.by.isEmpty()
                ? "[D]" + super.toString() + " (by: " + this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + " "
                    + this.time.format(DateTimeFormatter.ofPattern("h:mm a")) + ")"
                : "[D]" + super.toString() + " (by: " + by + ")";
    }
}