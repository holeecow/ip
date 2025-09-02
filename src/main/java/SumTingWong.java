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
//            } else if (userInput.startsWith("todo")) {
//                if (userInput.length() <= 4) {
//                    throw new NoDescriptionException();
//                }
//                String[] parts = userInput.split(" ", 2);
//                String description = parts[1];
//
//                Task task = new ToDo(description, false);
//                allTasks.add(task);
//                currentIndex++;
//
//                System.out.println("------------------------------ \n"
//                        + " Got it. I've added this task: \n    "
//                        + allTasks.get(currentIndex - 1).toString()
//                        + "\n Now you have "
//                        + currentIndex
//                        + " tasks in the list \n"
//                        + "------------------------------");
//            } else if (userInput.startsWith("event")) {
//                String details = userInput.substring(userInput.indexOf(" ") + 1);
//
//                // Split into [description, start+end]
//                String[] parts = details.split("/from", 2);
//
//                if (parts[0].isEmpty()) {
//                    throw new NoDescriptionException();
//                }
//
//                if (parts.length < 2) {
//                    throw new NoDateException();
//                }
//                String description = parts[0].trim(); // "project meeting"
//
//                // Split the time part into [start, end]
//                String[] times = parts[1].split("/to", 2);
//                String startTime = times[0].trim();   // "Mon 2pm"
//                String endTime = times[1].trim();     // "4pm"
//
//                Task task = new Event(description, startTime, endTime, false);
//                allTasks.add(task);
//                currentIndex++;
//
//                System.out.println("------------------------------ \n"
//                        + " Got it. I've added this task: \n    "
//                        + allTasks.get(currentIndex - 1).toString()
//                        + "\n Now you have "
//                        + currentIndex
//                        + " tasks in the list \n"
//                        + "------------------------------");
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
