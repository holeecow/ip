package sumtingwong.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles persistence of tasks to and from the local filesystem.
 * <p>
 * The storage writes tasks to a plain text file and reads them back into
 * memory when the application starts. The file path is provided at
 * construction time and stored in a static field so calls to {@link #saveTasks(ArrayList)}
 * can be made without an instance.
 */
public class Storage {
    private static String filePath;

    /**
     * Creates a new Storage configured to read from and write to the given path.
     *
     * @param filePath path to the data file (e.g. "data/TaskList.txt")
     */
    public Storage(String filePath) {
        Storage.filePath = filePath;
    }

    /**
     * Saves all tasks to the configured file in their file format representation.
     * <p>
     * Ensures the target directory and file exist before writing.
     * Each task is serialized using its {@code toFileFormat()} method.
     *
     * @param tasks list of tasks to persist
     */
    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            // create the data directory if it doesn't exist
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // create the sumtingwong.ui.TaskList.txt file if it doesn't exist
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            // write tasks to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Task task : tasks) {
                    if (task.getClass() == ToDo.class) {
                        ToDo todo = (ToDo) task;
                        writer.write(todo.toFileFormat());
                    } else if (task.getClass() == Deadline.class) {
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

    /**
     * Loads tasks from the configured file.
     * <p>
     * If the file does not exist, an empty list is returned. Each line is
     * parsed using {@link Task#fromFileFormat(String)}.
     *
     * @return list of tasks loaded from disk; never {@code null}
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);

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