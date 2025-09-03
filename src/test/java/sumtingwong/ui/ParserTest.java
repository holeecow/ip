package sumtingwong.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void handleDeadline_addsDeadlineWithByString() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        parser.parseCommand("deadline submit report /by tomorrow 6pm");

        assertEquals(1, taskList.size());
        assertEquals("[D][ ] submit report (by: tomorrow 6pm)", taskList.get(0).toString());
    }

    @Test
    public void handleDeadline_missingBy_throwsNoDeadlineException() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        assertThrows(NoDeadlineException.class, () -> parser.parseCommand("deadline submit report"));
    }

    @Test
    public void handleDeadline_missingDescription_throwsNoDescriptionException() {
        TaskList taskList = new TaskList();
        TextUI ui = new TextUI(taskList);
        Parser parser = new Parser(ui);

        assertThrows(NoDescriptionException.class, () -> parser.parseCommand("deadline /by tomorrow"));
    }

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