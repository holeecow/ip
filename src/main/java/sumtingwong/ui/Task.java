package sumtingwong.ui;

/**
 * Base type for all tasks managed by the application.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description and completion state.
     *
     * @param description short description of the task
     * @param isDone whether the task is completed
     */
    public Task(String description, boolean isDone) {
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";
        
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon used in textual rendering.
     *
     * @return "X" if done, otherwise a single space
     */
    public String getStatusIcon() {

        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Reconstructs a {@link Task} (or subclass) from its serialized line format.
     *
     * @param line one line from the storage file
     * @return a corresponding {@link ToDo}, {@link Deadline}, or {@link Event}
     */
    public static Task fromFileFormat(String line) {
        assert line != null : "File format line cannot be null";
        assert !line.trim().isEmpty() : "File format line cannot be empty";
        
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "File format must have at least 3 parts separated by ' | '";
        
        String typeOfTask = parts[0].trim();
        Boolean isDone = Boolean.valueOf(parts[1].trim());
        String description = parts[2].trim();
        
        assert typeOfTask.matches("[TDE]") : "Task type must be T, D, or E";
        assert description != null && !description.isEmpty() : "Task description from file cannot be empty";

        if (typeOfTask.equals("T")) {
            return new ToDo(description, isDone);
        } else if (typeOfTask.equals("D")) {
            assert parts.length >= 4 : "Deadline format must have deadline field";
            String deadline = parts[3].trim();
            return new Deadline(description, deadline, isDone);
        }

        // task is an sumtingwong.ui.Event
        assert parts.length >= 5 : "Event format must have from and to fields";
        String from = parts[3].trim();
        String to = parts[4].trim();
        return new Event(description, from, to, isDone);

    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}