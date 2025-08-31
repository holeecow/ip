public class ToDo extends Task{

    protected String by;

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
