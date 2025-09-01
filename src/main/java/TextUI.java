import java.util.Scanner;

public class TextUI {
    private Scanner scanner;

    public TextUI() {
        this.scanner = new Scanner(System.in);
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