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
        assert by != null : "Deadline 'by' field cannot be null";
        assert !by.trim().isEmpty() : "Deadline 'by' field cannot be empty";
        
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
        assert time != null : "Deadline time cannot be null";
        assert date != null : "Deadline date cannot be null";
        
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
        if (this.by.isEmpty()) {
            assert this.date != null : "Date should not be null when by is empty";
            assert this.time != null : "Time should not be null when by is empty";
            return "[D]" + super.toString() + " (by: " + this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + " "
                    + this.time.format(DateTimeFormatter.ofPattern("h:mm a")) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }
}