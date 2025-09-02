package sumtingwong.ui;

public class ToDo extends Task{

    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }


    public String toFileFormat() {
        return "T" + " | " + super.isDone + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
