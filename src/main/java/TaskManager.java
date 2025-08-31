import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
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
                    if (task.getClass() == ToDo.class){
                        ToDo todo = (ToDo) task;
                        writer.write(todo.toFileFormat());
                    } else if(task.getClass() == Deadline.class){
                        Deadline deadline = (Deadline) task;
                        writer.write(deadline.toFileFormat());
                    } else {
                        Event event = (Event) task;
                        writer.write(event.toFileFormat());
                    }
                    writer.newLine();
                }
                // make sure that all data is written
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);

            // if the file doesn't exist, return empty task list
            if (!file.exists()) {
                return tasks;
            }

            // read tasks from the file and add it to tasks
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromFileFormat(line);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }
}