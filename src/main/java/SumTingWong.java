import java.util.Scanner;

public class SumTingWong {
    public static void main(String[] args) {
        String botName = "SumTingWong";
        System.out.println("------------------------------- \n");
        System.out.println("Hello! I'm " + botName + "\n"
                            + "What can I do for you? -.-");
        System.out.println("------------------------------- \n");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {
            System.out.println("------------------------------- \n");
            System.out.println(userInput + ": D");
            System.out.println("------------------------------- \n");
            userInput = myObj.nextLine();
        }
        System.out.println("------------------------------- \n");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("------------------------------- \n");
    }
}
