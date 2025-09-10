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
     * Default constructor used by GUI.
     */
    public SumTingWong() {
        this("data/TaskList.txt");
    }

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
            parser = new Parser(textUI, this.taskList);
        } catch (SumTingWongException e) {
            textUI = new TextUI(new TaskList());
            taskList = new TaskList();
            parser = new Parser(this.textUI, this.taskList);
        }
    }

    /**
     * Processes a single user input and returns the bot's reply for GUI.
     * The method does not block or print to System.out.
     *
     * @param input user input line
     * @return reply text to display in GUI
     */
    public String getResponse(String input) {
        StringBuilder sb = new StringBuilder();
        TextUI guiUi = new TextUI(taskList, sb::append);
        Parser guiParser = new Parser(guiUi, taskList);
        try {
            guiParser.parseCommand(input);
        } catch (SumTingWongException e) {
            guiUi.showError(e.getMessage());
        } finally {
            Storage.saveTasks(taskList.getTasks());
        }
        return sb.toString();
    }

}
