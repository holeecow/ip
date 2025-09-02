package sumtingwong.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected String by;

    protected LocalDate date;

    protected LocalTime time;

    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    public Deadline(String description, LocalTime time, LocalDate date, boolean isDone) {
        super(description, isDone);
        this.time = time;
        this.date = date;
        this.by = "";
    }

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