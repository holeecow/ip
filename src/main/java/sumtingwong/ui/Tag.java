package sumtingwong.ui;

import java.util.Objects;

/**
 * Represents a tag that can be attached to tasks for categorization.
 * Tags are case-insensitive and must start with # (e.g., #fun, #work, #urgent).
 */
public class Tag {
    private final String name;
    
    /**
     * Creates a tag with the given name.
     *
     * @param name the tag name (with or without # prefix)
     */
    public Tag(String name) {
        assert name != null : "Tag name cannot be null";
        assert !name.trim().isEmpty() : "Tag name cannot be empty";
        
        // Normalize tag name - ensure it starts with # and is lowercase
        String normalizedName = name.trim().toLowerCase();
        if (!normalizedName.startsWith("#")) {
            normalizedName = "#" + normalizedName;
        }
        
        // Validate tag format
        if (!isValidTagName(normalizedName)) {
            throw new IllegalArgumentException("Invalid tag name: " + name + 
                ". Tags must contain only letters, numbers, and underscores after #");
        }
        
        this.name = normalizedName;
    }
    
    /**
     * Validates if a tag name is in the correct format.
     *
     * @param tagName the tag name to validate
     * @return true if valid, false otherwise
     */
    private static boolean isValidTagName(String tagName) {
        if (!tagName.startsWith("#") || tagName.length() < 2) {
            return false;
        }
        
        // Check that everything after # is alphanumeric or underscore
        String nameAfterHash = tagName.substring(1);
        return nameAfterHash.matches("[a-z0-9_]+");
    }
    
    /**
     * Creates a tag from a string, validating the format.
     *
     * @param tagString the tag string (e.g., "#fun" or "fun")
     * @return the created Tag
     * @throws IllegalArgumentException if the tag format is invalid
     */
    public static Tag fromString(String tagString) {
        return new Tag(tagString);
    }
    
    /**
     * Gets the tag name (including the # prefix).
     *
     * @return the tag name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the tag name without the # prefix.
     *
     * @return the tag name without #
     */
    public String getNameWithoutHash() {
        return name.substring(1);
    }
    
    /**
     * Checks if this tag matches the given tag name (case-insensitive).
     *
     * @param tagName the tag name to match (with or without #)
     * @return true if they match
     */
    public boolean matches(String tagName) {
        if (tagName == null) {
            return false;
        }
        
        String normalizedInput = tagName.trim().toLowerCase();
        if (!normalizedInput.startsWith("#")) {
            normalizedInput = "#" + normalizedInput;
        }
        
        return this.name.equals(normalizedInput);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Tag tag = (Tag) other;
        return Objects.equals(name, tag.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    /**
     * Returns a display-friendly version of the tag with color/formatting.
     *
     * @return formatted tag string
     */
    public String toDisplayString() {
        return name; // Could be enhanced with colors in future
    }
}
