import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SumTingWong {

    private Storage storage;
    private TaskList taskList;
    private TextUI textUI;
    private Parser parser;

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

    public static void main(String[] args) {
        new SumTingWong("data/TaskList.txt").run();
    }
}
