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

    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_BYE = "bye";
    
    private static final String DEADLINE_SEPARATOR = "/by";
    private static final String EVENT_FROM_SEPARATOR = "/from";
    private static final String EVENT_TO_SEPARATOR = "/to";
    private static final String DATE_TIME_PATTERN = "d/M/yyyy";
    private static final int TIME_STRING_LENGTH = 4;

    private boolean isExit = false;

    private final TextUI textUI;
    private final TaskList taskList;

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
        
        String[] parts = userInput.split(" ", 2);
        String command = parts[0].toLowerCase();
        String description = parts.length > 1 ? parts[1] : "";

        switch (command) {
        case COMMAND_LIST:
            handleListCommand();
            break;
        case COMMAND_UNMARK:
            handleUnmarkCommand(description);
            break;
        case COMMAND_MARK:
            handleMarkCommand(description);
            break;
        case COMMAND_DEADLINE:
            handleDeadlineCommand(description);
            break;
        case COMMAND_TODO:
            handleToDoCommand(description);
            break;
        case COMMAND_EVENT:
            handleEventCommand(description);
            break;
        case COMMAND_DELETE:
            handleDeleteCommand(description);
            break;
        case COMMAND_FIND:
            handleFindCommand(description);
            break;
        case COMMAND_BYE:
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
    private void handleUnmarkCommand(String description) {
        int listIndex = Integer.parseInt(description) - 1;
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
        int listIndex = Integer.parseInt(description) - 1;
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
        int listIndex = Integer.parseInt(description) - 1;

        Task deletedTask = taskList.get(listIndex);
        taskList.remove(listIndex);

        textUI.showDeleteMessage(deletedTask);
    }

    /**
     * Handles application termination.
     */
    private void handleByeCommand() {
        textUI.showByeMessage();
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
        String[] parts = description.split(DEADLINE_SEPARATOR, 2);
        if (parts.length < 2) {
            throw new NoDeadlineException();
        }

        if (parts[0].isEmpty()) {
            throw new NoDescriptionException();
        }

        String deadlineDescription = parts[0].trim();
        String deadline = parts[1].trim();

        // Parse structured date-time format (e.g., "2/12/2019 1800")
        if (Character.isDigit(deadline.charAt(0))) {
            String[] deadlineParts = deadline.split(" ");

            String dateStr = deadlineParts[0];
            String timeStr = deadlineParts[1];
            assert timeStr.length() == TIME_STRING_LENGTH : "Time format must be exactly 4 digits (HHMM)";

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);

            int hour = Integer.parseInt(timeStr.substring(0, 2));
            int minute = Integer.parseInt(timeStr.substring(2, 4));

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
        String[] parts = description.split(EVENT_FROM_SEPARATOR, 2);

        if (parts[0].isEmpty()) {
            throw new NoDescriptionException();
        }

        if (parts.length < 2) {
            throw new NoDateException();
        }
        String eventDescription = parts[0].trim();

        String[] times = parts[1].split(EVENT_TO_SEPARATOR, 2);
        assert times.length == 2 : "Event must have both /from and /to parts";
        String startTime = times[0].trim();
        String endTime = times[1].trim();

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
