package sumtingwong.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Parses user input and delegates actions to the {@link TextUI} and model classes.
 * <p>
 * Responsible for validating command formats and creating appropriate task
 * objects before handing them to the UI for display and persistence.
 */
public class Parser {

    private static boolean isExit = false;

    private TextUI textUI;

    private TaskList taskList;

    /**
     * Constructs a parser that will use the provided {@link TextUI} for output
     * and interactions with the underlying {@link TaskList}.
     *
     * @param ui the UI instance to delegate actions to
     */
    public Parser(TextUI ui, TaskList taskList) {
        assert ui != null : "TextUI cannot be null";
        assert taskList != null : "TaskList cannot be null";
        
        this.textUI = ui;
        this.taskList = taskList;
    }

    /**
     * Parses a single line of user input and executes the corresponding command.
     *
     * @param userInput raw command line entered by the user
     * @throws SumTingWongException if the command is unknown or arguments are invalid
     */
    public void parseCommand(String userInput) {
        assert userInput != null : "User input cannot be null";
        assert !userInput.trim().isEmpty() : "User input cannot be empty";
        
        // Split the input based on spaces to get the command and arguments
        String[] parts = userInput.split(" ", 2);
        String command = parts[0].toLowerCase();
        String description = parts.length > 1 ? parts[1] : "";

        switch (command) {
        case "list":
            handleListCommand();
            break;
        case "unmark":
            handleUnMarkCommand(description);
            break;
        case "mark":
            handleMarkCommand(description);
            break;
        case "deadline":
            handleDeadlineCommand(description);
            break;
        case "todo":
            handleToDoCommand(description);
            break;
        case "event":
            handleEventCommand(description);
            break;
        case "delete":
            handleDeleteCommand(description);
            break;
        case "find":
            handleFindCommand(description);
            break;
        case "bye":
            handleByeCommand();
            break;
        default:
            throw new UnknownEventException(command);
        }
    }

    /**
     * Marks a task as not done given its 1-based index in the list.
     *
     * @param description textual index provided by the user
     * @throws NumberFormatException if the index is not a number
     */
    private void handleUnMarkCommand(String description) {
        assert description != null : "Description for unmark command cannot be null";
        assert !description.trim().isEmpty() : "Description for unmark command cannot be empty";
        
        int listIndex = Integer.parseInt(description) - 1;
        assert listIndex >= 0 : "Task index must be positive (1-based)";
        
        taskList.get(listIndex).markAsNotDone();
        textUI.showUnMarkMessage(listIndex);
    }

    /**
     * Marks a task as done given its 1-based index in the list.
     *
     * @param description textual index provided by the user
     * @throws NumberFormatException if the index is not a number
     */
    private void handleMarkCommand(String description) {
        assert description != null : "Description for mark command cannot be null";
        assert !description.trim().isEmpty() : "Description for mark command cannot be empty";
        
        int listIndex = Integer.parseInt(description) - 1;
        assert listIndex >= 0 : "Task index must be positive (1-based)";
        
        taskList.get(listIndex).markAsDone();
        textUI.showMarkMessage(listIndex);
    }

    /**
     * Displays all tasks.
     */
    private void handleListCommand() {
        textUI.showListMessage();
    }

    /**
     * Deletes a task given its 1-based index in the list.
     *
     * @param description textual index provided by the user
     * @throws NumberFormatException if the index is not a number
     */
    private void handleDeleteCommand(String description) {
        assert description != null : "Description for delete command cannot be null";
        assert !description.trim().isEmpty() : "Description for delete command cannot be empty";
        
        int listIndex = Integer.parseInt(description) - 1;
        assert listIndex >= 0 : "Task index must be positive (1-based)";

        Task deletedTask = taskList.get(listIndex);
        assert deletedTask != null : "Task to delete should not be null";
        taskList.remove(listIndex);

        textUI.showDeleteMessage(deletedTask);
    }

    /**
     * Handles application termination.
     */
    private void handleByeCommand() {
        this.textUI.showByeMessage();
        isExit = true;
    }

    /**
     * Finds tasks whose description contains the provided keyword.
     * Empty keywords are treated as no-op.
     *
     * @param keyword user-provided keyword
     */
    private void handleFindCommand(String keyword) {
        if (keyword.isEmpty()) {
            throw new NoKeywordException();
        }
        StringBuilder sb = new StringBuilder();
        int displayIndex = 1;
        for (Task task : taskList.findByKeyword(keyword)) {
            sb.append(displayIndex++).append(". ").append(task.toString()).append("\n");
        }
        textUI.showFindMessage(sb.toString());
    }

    /**
     * Parses and adds a deadline task.
     * <p>
     * Supports two formats for the deadline value after "/by":
     * - A free-form string (e.g., "next Friday 6pm").
     * - A date-time pattern starting with a digit, formatted as d/M/yyyy HHmm
     *   (e.g., "2/12/2019 1800"), which is parsed into {@link LocalDate} and
     *   {@link LocalTime} and rendered prettily.
     *
     * @param description the raw argument after the command keyword
     * @throws NoDeadlineException if "/by" and a value are not provided
     * @throws NoDescriptionException if the task description is missing
     */
    private void handleDeadlineCommand(String description) {
        String[] parts = description.split("/by", 2);
        if (parts.length < 2) {
            throw new NoDeadlineException();
        }

        if (parts[0].isEmpty()) {
            throw new NoDescriptionException();
        }

        String deadlineDescription = parts[0].trim();
        String deadline = parts[1].trim();

        // check if the first character of deadline is a digit (i.e. if the deadline is in the format "2/12/2019 1800")
        if (Character.isDigit(deadline.charAt(0))) {
            String[] deadlineParts = deadline.split(" ");
            assert deadlineParts.length >= 2 : "Date-time format must have both date and time parts";

            String dateStr = deadlineParts[0];
            String timeStr = deadlineParts[1];
            assert timeStr.length() == 4 : "Time format must be exactly 4 digits (HHMM)";

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);

            int hour = Integer.parseInt(timeStr.substring(0, 2));
            int minute = Integer.parseInt(timeStr.substring(2, 4));
            assert hour >= 0 && hour <= 23 : "Hour must be between 0 and 23";
            assert minute >= 0 && minute <= 59 : "Minute must be between 0 and 59";

            // convert the time e.g. 1800 to a LocalTime object
            LocalTime time = LocalTime.of(hour, minute);

            Task task = new Deadline(deadlineDescription, time, date, false);

            taskList.add(task);

            textUI.showDeadlineMessage(task);
        } else {
            Task task = new Deadline(deadlineDescription, deadline, false);

            taskList.add(task);

            textUI.showDeadlineMessage(task);
        }
    }

    /**
     * Parses and adds a todo task.
     *
     * @param description the todo description; must not be empty
     * @throws NoDescriptionException if description is empty
     */
    private void handleToDoCommand(String description) {
        if (description.isEmpty()) {
            throw new NoDescriptionException();
        }
        Task task = new ToDo(description, false);
        taskList.add(task);
        textUI.showToDoMessage(task);
    }

    /**
     * Parses and adds an event task in the format
     * "desc /from start /to end".
     *
     * @param description the raw argument after the command keyword
     * @throws NoDescriptionException if the description is empty
     * @throws NoDateException if either /from or /to is missing
     */
    private void handleEventCommand(String description) {
        String[] parts = description.split("/from", 2);

        if (parts[0].isEmpty()) {
            throw new NoDescriptionException();
        }

        if (parts.length < 2) {
            throw new NoDateException();
        }
        String eventDescription = parts[0].trim();

        // Split the time part into [start, end]
        String[] times = parts[1].split("/to", 2);
        assert times.length == 2 : "Event must have both /from and /to parts";
        String startTime = times[0].trim();
        String endTime = times[1].trim();
        assert !startTime.isEmpty() : "Event start time cannot be empty";
        assert !endTime.isEmpty() : "Event end time cannot be empty";

        Task task = new Event(eventDescription, startTime, endTime, false);
        taskList.add(task);

        textUI.showEventMessage(task);
    }

    /**
     * Returns whether the user has issued the exit command.
     *
     * @return true if exit was requested; false otherwise
     */
    public boolean isExit() {
        return isExit;
    }
}
