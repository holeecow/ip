package sumtingwong.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link Storage} save behavior and file creation.
 */
public class StorageTest {
    /**
     * Verifies that {@link Storage#saveTasks(ArrayList)} writes the expected
     * serialized lines for ToDo, Deadline, and Event tasks.
     */
    @Test
    public void saveTasks_writesExpectedContent(@TempDir Path tempDir) throws IOException {
        //create temporary directory for testing
        Path tempFile = tempDir.resolve("tasks.txt");
        new Storage(tempFile.toString());

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book", false));
        tasks.add(new Deadline("submit assignment", "Sunday", true));
        tasks.add(new Event("team meeting", "2025-01-01 10:00", "2025-01-01 11:00", false));

        Storage.saveTasks(tasks);

        // read the contents of the tasks.txt file 
        List<String> lines = Files.readAllLines(tempFile, StandardCharsets.UTF_8);
        assertEquals(3, lines.size());
        assertEquals("T | false | read book", lines.get(0));
        assertEquals("D | true | submit assignment | Sunday", lines.get(1));
        assertEquals("E | false | team meeting | 2025-01-01 10:00 | 2025-01-01 11:00", lines.get(2));
    }

    /**
     * Ensures that {@link Storage#saveTasks(ArrayList)} creates the data file
     * if it does not already exist.
     */
    @Test
    //Test if the file is created if it doesn't exist
    public void saveTasks_createsFileIfMissing(@TempDir Path tempDir) {
        Path tempFile = tempDir.resolve("tasks_missing.txt");
        new Storage(tempFile.toString());

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("sample", true));

        Storage.saveTasks(tasks);

        assertTrue(Files.exists(tempFile));
    }
}