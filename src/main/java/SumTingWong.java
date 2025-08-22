import java.util.Scanner;

public class SumTingWong {
    public static void main(String[] args) {
        Task[] allTasks = new Task[100];
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
                for (int i = 0; allTasks[i] != null; i++) {
                    int temp = i + 1;
                    System.out.println(temp
                                        + "."
                                        + "["
                                        + allTasks[i].getStatusIcon()
                                        + "] "
                                        + allTasks[i].getDiscription());
                }
                System.out.println("-------------------------------");
            } else if (userInput.startsWith("unmark")) {
                Scanner sc = new Scanner(userInput);
                sc.next();  // skip "unmark"
                int markIndex = sc.nextInt() - 1;
                allTasks[markIndex].markAsNotDone();

                System.out.println("------------------------------ \n"
                        + "OK, I've marked this task as not done yet: \n"
                        + "["
                        + allTasks[markIndex].getStatusIcon()
                        + "] "
                        + allTasks[markIndex].getDiscription()
                        + "\n------------------------------");
            } else if (userInput.startsWith("mark")) {
                Scanner sc = new Scanner(userInput);
                sc.next();  // skip "mark"
                int markIndex = sc.nextInt() - 1;
                allTasks[markIndex].markAsDone();

                System.out.println("------------------------------ \n"
                        + "Good job I guess. I've marked this task as done: \n"
                        + "["
                        + allTasks[markIndex].getStatusIcon()
                        + "] "
                        + allTasks[markIndex].getDiscription()
                        + "\n------------------------------");

            } else {
                Task task = new Task(userInput);
                allTasks[currentIndex] = task;
                currentIndex++;

                System.out.println("------------------------------- \n");
                System.out.println("added: " + userInput);
                System.out.println("------------------------------- \n");
            }

            userInput = myObj.nextLine();
        }

        System.out.println("------------------------------- \n");
        System.out.println("Bye. Hope you never come back >:");
        System.out.println("------------------------------- \n");
    }
}
