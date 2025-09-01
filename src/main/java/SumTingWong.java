import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SumTingWong {

    private Storage storage;
    private TaskList taskList;
    private static TextUI textUI;
    private Parser parser;

    public SumTingWong(String filePath) {
        storage = new Storage(filePath);

        try {
            this.taskList = new TaskList(storage.loadTasks());

            textUI = new TextUI(taskList);
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
                Parser.parseCommand(fullCommand);
                isExit = parser.isExit();
            } catch (SumTingWongException e) {
                textUI.showError(e.getMessage());
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
//
//            if (userInput.equals("list")) {
//                System.out.println("-------------------------------");
//                System.out.println("Here are the tasks in your list: ");
//                for (int i = 0; i < allTasks.size(); i++) {
//                    int temp = i + 1;
//                    System.out.println(temp
//                            + "."
//                            + allTasks.get(i).toString());
//                }
//                System.out.println("-------------------------------");
//            } else if (userInput.startsWith("unmark")) {
//                Scanner sc = new Scanner(userInput);
//                sc.next();  // skip "unmark"
//                int markIndex = sc.nextInt() - 1;
//                allTasks.get(markIndex).markAsNotDone();
//
//                System.out.println("------------------------------ \n"
//                        + "OK, I've marked this task as not done yet: \n"
//                        + allTasks.get(markIndex).toString()
//                        + "\n------------------------------");
//            } else if (userInput.startsWith("mark")) {
//                Scanner sc = new Scanner(userInput);
//                sc.next();  // skip "mark"
//                int markIndex = sc.nextInt() - 1;
//                allTasks.get(markIndex).markAsDone();
//
//                System.out.println("------------------------------ \n"
//                        + "Good job I guess. I've marked this task as done: \n"
//                        + allTasks.get(markIndex).toString()
//                        + "\n------------------------------");
//
//            } else if (userInput.startsWith("deadline")) {
//                String withoutName = userInput.substring(9); // skip first 9 characters ("deadline ")
//                String[] parts = withoutName.split("/by", 2);
//                if (parts.length < 2) {
//                    throw new NoDeadlineException();
//                }
//
//                if (parts[0].isEmpty()) {
//                    throw new NoDescriptionException();
//                }
//
//                String description = parts[0].trim();
//                String deadline = parts[1].trim();
//
//                // check if the first character of deadline is a digit (i.e. if the deadline is in the format "2/12/2019 1800")
//                if (Character.isDigit(deadline.charAt(0))) {
//
//                    String[] deadlineParts = deadline.split(" "); // split up the deadline by space
//
//                    String dateStr = deadlineParts[0]; // "2/12/2019"
//                    String timeStr = deadlineParts[1]; // "1800"
//
//                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
//                    LocalDate date = LocalDate.parse(dateStr, dateFormatter);
//
//                    int hour = Integer.parseInt(timeStr.substring(0, 2)); // "18"
//                    int minute = Integer.parseInt(timeStr.substring(2, 4)); // "00"
//
//                    // convert the time e.g. 1800 to a LocalTime object
//                    LocalTime time = LocalTime.of(hour, minute);
//
//                    Task task = new Deadline(description, time, date, false);
//                    allTasks.add(task);
//                    currentIndex++;
//                } else {
//                    Task task = new Deadline(description, deadline, false);
//                    allTasks.add(task);
//                    currentIndex++;
//                }
//
//                System.out.println("------------------------------ \n"
//                        + " Got it. I've added this task: \n    "
//                        + allTasks.get(currentIndex - 1).toString()
//                        + "\n Now you have "
//                        + currentIndex
//                        + " tasks in the list \n"
//                        + "------------------------------");
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
