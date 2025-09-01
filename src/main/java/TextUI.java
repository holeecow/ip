import java.util.Scanner;

public class TextUI {
    private final Scanner scanner;

    private static final String DIVIDER = "------------------------------- \n";

    private static final String BOT_NAME = "SumTingWong";

    public TextUI() {
        this.scanner = new Scanner(System.in);
    }

    public static String getDIVIDER() {
        return DIVIDER;
    }

    public void showWelcomeMessage() {
        System.out.println(DIVIDER + "Hello! I'm " + BOT_NAME + "\n"
                + "What can I do for you? -.-\n" + DIVIDER);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getUserInput() {
        return scanner.nextLine().trim();
    }

    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            showMessage("No tasks available.");
        } else {
            showMessage("Here are the tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                showMessage(tasks.get(i).toString());
            }
        }
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }
}