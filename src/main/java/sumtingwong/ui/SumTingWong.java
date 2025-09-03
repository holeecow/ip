package sumtingwong.ui;

/**
 * Entry point and application wiring for the SumTingWong chatbot.
 */
public class SumTingWong {

    private Storage storage;
    private TaskList taskList;
    private TextUI textUI;
    private Parser parser;

    /**
     * Constructs the application, initializing storage, loading tasks,
     * and wiring the UI and parser.
     *
     * @param filePath path to the storage file
     */
    public SumTingWong(String filePath) {
        this.storage = new Storage(filePath);

        try {
            this.taskList = new TaskList(storage.loadTasks());

            textUI = new TextUI(this.taskList);
            parser = new Parser(textUI);
        } catch (SumTingWongException e) {
            textUI.showError(e.getMessage());
            taskList = new TaskList();

            textUI = new TextUI(taskList);
            parser = new Parser(textUI);
        }
    }

    /**
     * Starts the main input loop until the user issues the exit command.
     * Saves tasks after each command.
     */
    public void run() {
        textUI.showWelcomeMessage();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = textUI.getUserInput();
                parser.parseCommand(fullCommand);
                isExit = parser.isExit();
            } catch (SumTingWongException e) {
                textUI.showError(e.getMessage());
            } finally {
                Storage.saveTasks(taskList.getTasks());
            }
        }
    }

    /**
     * Program entry point. Configures the storage path and launches the app.
     *
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
        new SumTingWong("../data/TaskList.txt").run();
    }
}
