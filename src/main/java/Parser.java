import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Parser {

    private TextUI textUI;

    private static boolean isExit = false;

    public Parser(TextUI ui) {
        this.textUI = ui;
    }

    public void parseCommand(String userInput) {
        // Split the input based on spaces to get the command and arguments
        String[] parts = userInput.split(" ", 2);
        String command = parts[0].toLowerCase();  // Command e.g. "add", "list"
        String description = parts.length > 1 ? parts[1] : "";  // Remaining task description

        // Switch case for handling different commands
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
            case "bye":
                handleByeCommand();
                break;
        }
    }

    private void handleUnMarkCommand(String description) {
        int listIndex = Integer.parseInt(description) - 1;  // "2"

        textUI.showUnMarkMessage(listIndex);
    }

    private void handleMarkCommand(String description) {
        int listIndex = Integer.parseInt(description) - 1;  // "2"

        textUI.showMarkMessage(listIndex);
    }

    // Handle the "add" command
    private String handleAddCommand(String argument) {
        if (argument.isEmpty()) {
            return "The task description cannot be empty!";
        }
        return "Adding task: " + argument;  // You could integrate this with the TaskManager class
    }

    // Handle the "list" command
    private void handleListCommand() {
        textUI.showListMessage();
    }

    // Handle the "delete" command
    private String handleDeleteCommand(String argument) {
        if (argument.isEmpty()) {
            return "Please specify the task number to delete.";
        }
        return "Deleting task: " + argument;  // You can integrate this with the task deletion logic
    }

    private void handleByeCommand() {
        this.textUI.showByeMessage();
        isExit = true;
    }

    private void handleDeadlineCommand(String description) {
        String[] descriptionParts = description.split("/by", 2);
        if (descriptionParts.length < 2) {
            throw new NoDeadlineException();
        }

        if (descriptionParts[0].isEmpty()) {
            throw new NoDescriptionException();
        }

        String deadlineDescription = descriptionParts[0].trim();
        String deadline = descriptionParts[1].trim();

        // check if the first character of deadline is a digit (i.e. if the deadline is in the format "2/12/2019 1800")
        if (Character.isDigit(deadline.charAt(0))) {
            String[] deadlineParts = deadline.split(" "); // split up the deadline by space

            String dateStr = deadlineParts[0]; // "2/12/2019"
            String timeStr = deadlineParts[1]; // "1800"

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);

            int hour = Integer.parseInt(timeStr.substring(0, 2)); // "18"
            int minute = Integer.parseInt(timeStr.substring(2, 4)); // "00"

            // convert the time e.g. 1800 to a LocalTime object
            LocalTime time = LocalTime.of(hour, minute);

            Task task = new Deadline(deadlineDescription, time, date, false);

            textUI.showDeadlineMessage(task);
        }

        Task task = new Deadline(deadlineDescription, deadline, false);
        textUI.showDeadlineMessage(task);
    }

    private void handleToDoCommand(String description) {
        if (description.isEmpty()) {
            throw new NoDescriptionException();
        }
        Task task = new ToDo(description, false);
        textUI.showToDoMessage(task);
    }

    public boolean isExit() {
        return isExit;
    }
}
