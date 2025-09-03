package sumtingwong.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Parser} deadline command handling.
 */
public class ParserTest {
    /**
     * Adds a deadline when /by is followed by a free-form string, and ensures
     * the task is appended with the expected rendering.
     */
    @Test
    public void handleDeadline_addsDeadlineWithByString() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        parser.parseCommand("deadline submit report /by tomorrow 6pm");

        assertEquals(1, taskList.size());
        assertEquals("[D][ ] submit report (by: tomorrow 6pm)", taskList.get(0).toString());
    }

    /**
     * Missing /by and value should trigger {@link NoDeadlineException}.
     */
    @Test
    public void handleDeadline_missingBy_throwsNoDeadlineException() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        assertThrows(NoDeadlineException.class, () -> parser.parseCommand("deadline submit report"));
    }

    /**
     * Missing description before /by should trigger {@link NoDescriptionException}.
     */
    @Test
    public void handleDeadline_missingDescription_throwsNoDescriptionException() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        assertThrows(NoDescriptionException.class, () -> parser.parseCommand("deadline /by tomorrow"));
    }

    /**
     * Digit-starting date-time in d/M/yyyy HHmm should be parsed and rendered
     * in pretty form, resulting in a single appended task.
     */
    @Test
    public void handleDeadline_dateTimePattern_addsSinglePrettyFormattedTask() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        parser.parseCommand("deadline finish project /by 2/12/2019 1800");

        assertEquals(1, taskList.size());
        assertEquals("[D][ ] finish project (by: Dec 02 2019 6:00 PM)", taskList.get(0).toString());
    }
} 