import java.util.Scanner;
import java.util.ArrayList;

public class SumTingWong {
    public static void main(String[] args) {
        ArrayList<Task> allTasks = new ArrayList<>();
        String botName = "SumTingWong";
        int currentIndex = 0;

        System.out.println("------------------------------- \n");
        System.out.println("Hello! I'm " + botName + "\n"
                            + "What can I do for you? -.-");
        System.out.println("------------------------------- \n");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        String userInput = myObj.nextLine();

        while (!userInput.equals("bye")) {

            if (userInput.equals("list")) {
                System.out.println("-------------------------------");
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < allTasks.size(); i++) {
                    int temp = i + 1;
                    System.out.println(temp
                                        + "."
                                        + allTasks.get(i).toString());
                }
                System.out.println("-------------------------------");
            } else if (userInput.startsWith("unmark")) {
                Scanner sc = new Scanner(userInput);
                sc.next();  // skip "unmark"
                int markIndex = sc.nextInt() - 1;
                allTasks.get(markIndex).markAsNotDone();

                System.out.println("------------------------------ \n"
                        + "OK, I've marked this task as not done yet: \n"
                        + allTasks.get(markIndex).toString()
                        + "\n------------------------------");
            } else if (userInput.startsWith("mark")) {
                Scanner sc = new Scanner(userInput);
                sc.next();  // skip "mark"
                int markIndex = sc.nextInt() - 1;
                allTasks.get(markIndex).markAsDone();

                System.out.println("------------------------------ \n"
                        + "Good job I guess. I've marked this task as done: \n"
                        + allTasks.get(markIndex).toString()
                        + "\n------------------------------");

            } else if (userInput.startsWith("deadline")) {
                String withoutCommand = userInput.substring(9); // skip first 9 characters ("deadline ")
                String[] parts = withoutCommand.split("/by", 2);
                if (parts.length < 2) {
                    throw new NoDeadlineException();
                }

                if (parts[0].isEmpty()) {
                    throw new NoDescriptionException();
                }

                String description = parts[0].trim();
                String time = parts[1].trim();

                System.out.print(description + "\n");

                Task task = new Deadline(description, time);
                allTasks.add(task);
                currentIndex++;

                System.out.println("------------------------------ \n"
                        + " Got it. I've added this task: \n    "
                        + allTasks.get(currentIndex - 1).toString()
                        + "\n Now you have "
                        + currentIndex
                        + " tasks in the list \n"
                        + "------------------------------");
            } else if (userInput.startsWith("todo")) {
                if (userInput.length() <= 4) {
                    throw new NoDescriptionException();
                }
                String[] parts = userInput.split(" ", 2);
                String description = parts[1];

                Task task = new ToDo(description);
                allTasks.add(task);
                currentIndex++;

                System.out.println("------------------------------ \n"
                        + " Got it. I've added this task: \n    "
                        + allTasks.get(currentIndex - 1).toString()
                        + "\n Now you have "
                        + currentIndex
                        + " tasks in the list \n"
                        + "------------------------------");
            } else if (userInput.startsWith("event")) {
                String details = userInput.substring(userInput.indexOf(" ") + 1);

                // Split into [description, start+end]
                String[] parts = details.split("/from", 2);

                if (parts[0].isEmpty()) {
                    throw new NoDescriptionException();
                }

                if (parts.length < 2) {
                    throw new NoDateException();
                }
                String description = parts[0].trim(); // "project meeting"

                // Split the time part into [start, end]
                String[] times = parts[1].split("/to", 2);
                String startTime = times[0].trim();   // "Mon 2pm"
                String endTime = times[1].trim();     // "4pm"

                Task task = new Event(description, startTime, endTime);
                allTasks.add(task);
                currentIndex++;

                System.out.println("------------------------------ \n"
                        + " Got it. I've added this task: \n    "
                        + allTasks.get(currentIndex - 1).toString()
                        + "\n Now you have "
                        + currentIndex
                        + " tasks in the list \n"
                        + "------------------------------");
            } else if (userInput.startsWith("delete")) {
                Scanner sc = new Scanner(userInput);
                sc.next();  // skip "delete"

                int deleteIndex = sc.nextInt() - 1;
                Task deletedTask = allTasks.get(deleteIndex);
                allTasks.remove(deleteIndex);
                currentIndex--;

                System.out.println("------------------------------ \n"
                        + " Noted. I've removed this task: \n    "
                        + deletedTask.toString()
                        + "\n Now you have "
                        + currentIndex
                        + " tasks in the list \n"
                        + "------------------------------");
            } else {
                throw new UnknownEventException(userInput);
            }

            userInput = myObj.nextLine();
        }

        TaskManager.saveTasks(allTasks);

        System.out.println("------------------------------- \n");
        System.out.println("Bye. Hope you never come back >:");
        System.out.println("------------------------------- \n");
    }
}
