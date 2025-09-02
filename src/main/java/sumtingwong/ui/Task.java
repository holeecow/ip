package sumtingwong.ui;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    // mark done task with X
    public String getStatusIcon() {

        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        String typeOfTask = parts[0].trim();
        Boolean isDone = Boolean.valueOf(parts[1].trim());
        String description = parts[2].trim();

        if (typeOfTask.equals("T")) {
            return new ToDo(description, isDone);
        } else if (typeOfTask.equals("D")) {
            String deadline = parts[3].trim();
            return new Deadline(description, deadline, isDone);
        }

        // task is an sumtingwong.ui.Event
        String from = parts[3].trim();
        String to = parts[4].trim();
        return new Event(description, from, to, isDone);

    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}