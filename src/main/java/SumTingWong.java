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
//    public void run() {
//
//        int currentIndex = 0;
//
//        textUI.showWelcomeMessage();
//
//        String userInput = textUI.getUserInput();
//
//        while (!userInput.equals("bye")) {
//            } else if (userInput.startsWith("delete")) {
//                Scanner sc = new Scanner(userInput);
//                sc.next();  // skip "delete"
//
//                int deleteIndex = sc.nextInt() - 1;
//                Task deletedTask = allTasks.get(deleteIndex);
//                allTasks.remove(deleteIndex);
//                currentIndex--;
//
//                System.out.println("------------------------------ \n"
//                        + " Noted. I've removed this task: \n    "
//                        + deletedTask.toString()
//                        + "\n Now you have "
//                        + currentIndex
//                        + " tasks in the list \n"
//                        + "------------------------------");
//            } else {
//                throw new UnknownEventException(userInput);
//            }
//
//            userInput = textUI.getUserInput();
//        }
//
//        System.out.println("------------------------------- \n");
//        System.out.println("Bye. Hope you never come back >:");
//        System.out.println("------------------------------- \n");
//    }

    public static void main(String[] args) {
        new SumTingWong("data/TaskList.txt").run();
    }
}
