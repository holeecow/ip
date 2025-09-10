package sumtingwong.ui;

import java.util.ArrayList;

/**
 * In-memory collection of tasks with simple list-like operations.
 */
public class TaskList {
    private static ArrayList<Task> tasks;

    /**
     * Creates a task list seeded with the provided tasks.
     *
     * @param tasks initial tasks to populate
     */
    public TaskList(ArrayList<Task> tasks) {
        TaskList.tasks = new ArrayList<>(tasks);
    }

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return the task at the index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes the task at the given index.
     *
     * @param index zero-based index
     */
    public void remove(int index) {
        tasks.remove(index);
    }

    /**
     * Returns whether the list contains no tasks.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a copy of the underlying task list for persistence.
     *
     * @return tasks copy
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds tasks whose descriptions contain the provided keyword (case-insensitive).
     *
     * @param keyword search term to match within task descriptions
     * @return list of matching tasks, in their original order
     */
    public ArrayList<Task> findByKeyword(String keyword) {
        ArrayList<Task> result = new ArrayList<>();
        String needle = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.description != null && task.description.toLowerCase().contains(needle)) {
                result.add(task);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
