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
            case "bye":
                handleByeCommand();
                break;
        }
    }

    private void handleUnMarkCommand(String description) {

        int listIndex = Integer.parseInt(description) - 1;  // "2"

        textUI.showUnMarkMessage(listIndex);
    }

    // Handle the "add" command (e.g., add task)
    private String handleAddCommand(String argument) {
        if (argument.isEmpty()) {
            return "The task description cannot be empty!";
        }
        return "Adding task: " + argument;  // You could integrate this with the TaskManager class
    }

    // Handle the "list" command (e.g., show task list)
    private void handleListCommand() {
        textUI.showListMessage();
    }

    // Handle the "delete" command (e.g., delete task)
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

    public boolean isExit() {
        return isExit;
    }
}
