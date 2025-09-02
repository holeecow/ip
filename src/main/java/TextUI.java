import java.util.Scanner;

public class TextUI {
    private final Scanner scanner;

    private static TaskList taskList;

    private static final String DIVIDER = "------------------------------- \n";

    private static final String BOT_NAME = "SumTingWong";

    public TextUI(TaskList taskList) {
        this.scanner = new Scanner(System.in);
        TextUI.taskList = taskList;
    }

    public static String getDIVIDER() {
        return DIVIDER;
    }

    public void showListMessage() {
        System.out.println("this is the list in TextUI.java: " + taskList.toString());
        System.out.println(DIVIDER
                + "Here are the tasks in your list: \n"
                + taskList.toString()
                + DIVIDER);
    }

    public void showWelcomeMessage() {
        System.out.println(DIVIDER + "Hello! I'm " + BOT_NAME + "\n"
                + "What can I do for you? -.-\n" + DIVIDER);
    }

    public void showByeMessage() {
        System.out.println(DIVIDER + "Bye. Hope you never come back >: \n" + DIVIDER);
    }

    public String getUserInput() {
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showUnMarkMessage(int listIndex) {
        taskList.get(listIndex).markAsNotDone();
        System.out.println(DIVIDER
                + "OK, I've marked this task as not done yet: \n"
                + taskList.get(listIndex).toString()
                + "\n" + DIVIDER);
    }

    public void showMarkMessage(int listIndex) {
        taskList.get(listIndex).markAsDone();
        System.out.println(DIVIDER
                + "Nice! I've marked this task as done: \n"
                + taskList.get(listIndex).toString()
                + "\n" + DIVIDER);
    }

    public void showDeadlineMessage(Task deadline) {
        taskList.add(deadline);
        System.out.println(DIVIDER
                + "Got it. I've added this task: \n    "
                + deadline.toString()
                + "\nNow you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    public void showToDoMessage(Task todo) {
        taskList.add(todo);
        System.out.println(DIVIDER
                + "Got it. I've added this task: \n    "
                + todo.toString()
                + "\nNow you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    public void showEventMessage(Task event) {
        taskList.add(event);
        System.out.println(DIVIDER
                + "Got it. I've added this task: \n    "
                + event.toString()
                + "\nNow you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }

    public void showDeleteMessage(int listIndex) {
        Task deletedTask = taskList.get(listIndex);
        taskList.remove(listIndex);

        System.out.println(DIVIDER
                + " Noted. I've removed this task: \n    "
                + deletedTask.toString()
                + "\n Now you have "
                + taskList.size()
                + " tasks in the list \n"
                + DIVIDER);
    }
}