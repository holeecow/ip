import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TaskManager {
    private static final String FILE_PATH = "./data/TaskList.txt";

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            // create the data directory if it doesn't exist
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // create the TaskList.txt file if it doesn't exist
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            // write tasks to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Task task : tasks) {
                    writer.write(task.toString());
                    writer.newLine();
                }
                // make sure that all data is written
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}