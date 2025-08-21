import java.util.Arrays;
import java.util.Scanner;

public class SumTingWong {
    public static void main(String[] args) {
        String[] tasks = new String[100];
        String botName = "SumTingWong";
        int currentIndex = 0;

        System.out.println("------------------------------- \n");
        System.out.println("Hello! I'm " + botName + "\n"
                            + "What can I do for you? -.-");
        System.out.println("------------------------------- \n");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {
            tasks[currentIndex] = userInput;
            currentIndex++;

            if (userInput.equals("list")) {
                System.out.println("-------------------------------");
                for (int i = 1; tasks[i] != null; i++) {
                    System.out.println(i + ". " + tasks[i - 1]);
                }
                System.out.println("-------------------------------");
            } else {
                System.out.println("------------------------------- \n");
                System.out.println("added: " + userInput);
                System.out.println("------------------------------- \n");
            }

            userInput = myObj.nextLine();
        }

        System.out.println("------------------------------- \n");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("------------------------------- \n");
    }
}
