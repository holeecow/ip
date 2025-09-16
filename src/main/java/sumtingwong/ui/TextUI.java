package sumtingwong.ui;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Console-based user interface for displaying messages and interacting
 * with the {@link TaskList} during the application's lifecycle.
 */
public class TextUI {
    private static final String DIVIDER = "------------------------------- \n";
    private static final String BOT_NAME = "SumTingWong";
    protected static TaskList taskList;

    private final Scanner scanner;
    private final Consumer<String> printer;

    /**
     * Creates a UI bound to the provided task list.
     *
     * @param taskList the task list to display and mutate
     */
    public TextUI(TaskList taskList) {
        this(taskList, System.out::println);
    }

    /**
     * Creates a UI with a custom printer. For GUI, supply a printer that buffers output.
     *
     * @param taskList the task list to display and mutate
     * @param printer function to handle output (e.g., System.out::println for console, StringBuilder::append for GUI)
     */
    public TextUI(TaskList taskList, Consumer<String> printer) {
        assert taskList != null : "TaskList cannot be null";
        assert printer != null : "Printer function cannot be null";
        
        this.scanner = new Scanner(System.in);
        TextUI.taskList = taskList;
        this.printer = printer;
    }

    /**
     * Returns the divider string used in console output.
     *
     * @return divider string
     */
    public static String getDivider() {
        return DIVIDER;
    }

    /**
     * Prints the full list of tasks to the console.
     */
    public void showListMessage() {
        printer.accept(DIVIDER
                + "Here are the tasks in your list: \n"
                + taskList.toString()
                + DIVIDER);
    }

    /**
     * Prints the goodbye message.
     */
    public void showByeMessage() {
        printer.accept(DIVIDER + "Bye. Hope you never come back >: \n" + DIVIDER);
    }

    /**
     * Reads a trimmed line of input from the console.
     *
     * @return user input without leading/trailing spaces
     */
    public String getUserInput() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints an error message.
     *
     * @param message the error to display
     */
    public void showError(String message) {
        printer.accept("Error: " + message);
    }

    /**
     * Marks a task as not done and prints feedback.
     *
     * @param listIndex zero-based index of the task
     */
    public void showUnMarkMessage(int listIndex) {
        assert listIndex >= 0 : "List index must be non-negative";
        assert listIndex < taskList.size() : "List index must be within bounds";
        
        printer.accept(DIVIDER
                + "OK, I've marked this task as not done yet: \n"
                + taskList.get(listIndex).toString()
                + "\n" + DIVIDER);
    }

    /**
     * Marks a task as done and prints feedback.
     *
     * @param listIndex zero-based index of the task
     */
    public void showMarkMessage(int listIndex) {
        assert listIndex >= 0 : "List index must be non-negative";
        assert listIndex < taskList.size() : "List index must be within bounds";
        
        printer.accept(DIVIDER
                + "Nice! I've marked this task as done: \n"
                + taskList.get(listIndex).toString()
                + "\n" + DIVIDER);
    }

    /**
     * Adds a deadline task to the list and prints feedback.
     *
     * @param deadline the task to add
     */
    public void showDeadlineMessage(Task deadline) {
        assert deadline != null : "Deadline task cannot be null";
        assert deadline instanceof Deadline : "Task must be a Deadline instance";
        
        printer.accept(DIVIDER
                + "Got it. I've added this task: \n    "
                + deadline.toString()
                + "\nNow you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    /**
     * Adds a todo task to the list and prints feedback.
     *
     * @param todo the task to add
     */
    public void showToDoMessage(Task todo) {
        assert todo != null : "Todo task cannot be null";
        assert todo instanceof ToDo : "Task must be a ToDo instance";
        
        printer.accept(DIVIDER
                + "Got it. I've added this task: \n    "
                + todo.toString()
                + "\nNow you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    /**
     * Adds an event task to the list and prints feedback.
     *
     * @param event the task to add
     */
    public void showEventMessage(Task event) {
        assert event != null : "Event task cannot be null";
        assert event instanceof Event : "Task must be an Event instance";
        
        printer.accept(DIVIDER
                + "Got it. I've added this task: \n    "
                + event.toString()
                + "\nNow you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    /**
     * Deletes the task at the given index and prints feedback.
     *
     * @param deletedTask the Task object that has been deleted
     */
    public void showDeleteMessage(Task deletedTask) {
        assert deletedTask != null : "Deleted task cannot be null";
        
        printer.accept(DIVIDER
                + " Noted. I've removed this task: \n    "
                + deletedTask.toString()
                + "\n Now you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    /**
     * Displays tasks that match a keyword search.
     *
     * @param tasks string representation of the tasks that match the keyword
     */
    public void showFindMessage(String tasks) {
        assert tasks != null : "Tasks string cannot be null";
        
        printer.accept(DIVIDER
                + "Here are the matching tasks in your list: \n"
                + tasks
                + DIVIDER);
    }
}