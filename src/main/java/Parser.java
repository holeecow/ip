import java.util.ArrayList;

public class Parser {

    private ArrayList<String> commands;

    private static TextUI UI;

    private static boolean isExit = false;

    public Parser(TextUI ui) {
        UI = ui;
        this.commands = new ArrayList<>();
    }

    public static void parseCommand(String userInput) {
        // Split the input based on spaces to get the command and arguments
        String[] parts = userInput.split(" ", 2);
        String command = parts[0].toLowerCase();  // Command e.g. "add", "list"
        String description = parts.length > 1 ? parts[1] : "";  // Task description or argument

        // Switch case for handling different commands
        switch (command) {
            case "list":
                handleListCommand();
                break;
            case "unmark":
                handleByeCommand();
                break;
            case "bye":
                handleByeCommand();
                break;
        }
    }
    // Handle the "add" command (e.g., add task)
    private String handleAddCommand(String argument) {
        if (argument.isEmpty()) {
            return "The task description cannot be empty!";
        }
        return "Adding task: " + argument;  // You could integrate this with the TaskManager class
    }

    // Handle the "list" command (e.g., show task list)
    private static void handleListCommand() {
        UI.showListMessage();
    }

    // Handle the "delete" command (e.g., delete task)
    private String handleDeleteCommand(String argument) {
        if (argument.isEmpty()) {
            return "Please specify the task number to delete.";
        }
        return "Deleting task: " + argument;  // You can integrate this with the task deletion logic
    }

    private static void handleByeCommand() {
        UI.showByeMessage();
        isExit = true;
    }

    public boolean isExit() {
        return isExit;
    }
}
