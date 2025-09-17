package sumtingwong.ui;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base type for all tasks managed by the application.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected Set<Tag> tags;

    /**
     * Creates a task with the given description and completion state.
     *
     * @param description short description of the task
     * @param isDone whether the task is completed
     */
    public Task(String description, boolean isDone) {
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";
        
        this.description = description;
        this.isDone = isDone;
        this.tags = new HashSet<>();
    }

    /**
     * Returns the status icon used in textual rendering.
     *
     * @return "X" if done, otherwise a single space
     */
    public String getStatusIcon() {

        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Adds a tag to this task.
     *
     * @param tag the tag to add
     */
    public void addTag(Tag tag) {
        assert tag != null : "Tag cannot be null";
        tags.add(tag);
    }
    
    /**
     * Adds a tag to this task by name.
     *
     * @param tagName the tag name (with or without # prefix)
     */
    public void addTag(String tagName) {
        addTag(new Tag(tagName));
    }
    
    /**
     * Removes a tag from this task.
     *
     * @param tag the tag to remove
     * @return true if the tag was removed, false if it wasn't present
     */
    public boolean removeTag(Tag tag) {
        assert tag != null : "Tag cannot be null";
        return tags.remove(tag);
    }
    
    /**
     * Removes a tag from this task by name.
     *
     * @param tagName the tag name (with or without # prefix)
     * @return true if the tag was removed, false if it wasn't present
     */
    public boolean removeTag(String tagName) {
        Tag tagToRemove = new Tag(tagName);
        return tags.remove(tagToRemove);
    }
    
    /**
     * Checks if this task has the specified tag.
     *
     * @param tag the tag to check for
     * @return true if the task has the tag
     */
    public boolean hasTag(Tag tag) {
        assert tag != null : "Tag cannot be null";
        return tags.contains(tag);
    }
    
    /**
     * Checks if this task has a tag with the specified name.
     *
     * @param tagName the tag name (with or without # prefix)
     * @return true if the task has the tag
     */
    public boolean hasTag(String tagName) {
        Tag tag = new Tag(tagName);
        return tags.contains(tag);
    }
    
    /**
     * Gets all tags associated with this task.
     *
     * @return a copy of the tags set
     */
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }
    
    /**
     * Gets a string representation of all tags.
     *
     * @return space-separated tag string, or empty string if no tags
     */
    public String getTagsString() {
        if (tags.isEmpty()) {
            return "";
        }
        return tags.stream()
                  .map(Tag::toString)
                  .sorted()
                  .collect(Collectors.joining(" "));
    }

    /**
     * Reconstructs a {@link Task} (or subclass) from its serialized line format.
     *
     * @param line one line from the storage file
     * @return a corresponding {@link ToDo}, {@link Deadline}, or {@link Event}
     */
    public static Task fromFileFormat(String line) {
        assert line != null : "File format line cannot be null";
        assert !line.trim().isEmpty() : "File format line cannot be empty";
        
        String[] parts = line.split(" \\| ");
        assert parts.length >= 4 : "File format must have at least 4 parts separated by ' | '" +
                " (type, isDone, description, tags)";
        
        String typeOfTask = parts[0].trim();
        Boolean isDone = Boolean.valueOf(parts[1].trim());
        String description = parts[2].trim();
        
        assert typeOfTask.matches("[TDE]") : "Task type must be T, D, or E";
        assert description != null && !description.isEmpty() : "Task description from file cannot be empty";

        Task task;
        if (typeOfTask.equals("T")) {
            task = new ToDo(description, isDone);
            // Tags are in parts[3]
            if (parts.length > 3) {
                addTagsFromString(task, parts[3].trim());
            }
        } else if (typeOfTask.equals("D")) {
            assert parts.length >= 5 : "Deadline format must have deadline field";
            String deadline = parts[3].trim();
            task = new Deadline(description, deadline, isDone);
            // Tags are in parts[4]
            if (parts.length > 4) {
                addTagsFromString(task, parts[4].trim());
            }
        } else {
            // Event task
            assert parts.length >= 6 : "Event format must have from and to fields";
            String from = parts[3].trim();
            String to = parts[4].trim();
            task = new Event(description, from, to, isDone);
            // Tags are in parts[5]
            if (parts.length > 5) {
                addTagsFromString(task, parts[5].trim());
            }
        }
        
        return task;

    }

    /**
     * Helper method to add tags to a task from a string representation.
     *
     * @param task the task to add tags to
     * @param tagsString space-separated string of tags
     */
    private static void addTagsFromString(Task task, String tagsString) {
        if (tagsString == null || tagsString.isEmpty()) {
            return;
        }
        
        String[] tagNames = tagsString.split("\\s+");
        for (String tagName : tagNames) {
            if (!tagName.isEmpty()) {
                try {
                    task.addTag(new Tag(tagName));
                } catch (IllegalArgumentException e) {
                    // Skip invalid tags during loading
                }
            }
        }
    }

    @Override
    public String toString() {
        String baseString = "[" + this.getStatusIcon() + "] " + this.description;
        String tagsString = getTagsString();
        if (!tagsString.isEmpty()) {
            baseString += " " + tagsString;
        }
        return baseString;
    }
}