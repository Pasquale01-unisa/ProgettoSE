package TriggerTest;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import projectse.model.trigger.TriggerTime;

public class TriggerTimeTest {

    private TriggerTime trigger;

    @Before
    public void setUp() {
        trigger = new TriggerTime("10", "30");
    }

    @Test
    public void testValidTriggerTime() {
        assertEquals("10", trigger.getHour());
        assertEquals("30", trigger.getMinutes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHour() {
        new TriggerTime("25", "30"); // Ora non valida
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMinutes() {
        new TriggerTime("10", "61"); // Minuti non validi
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHourFormat() {
        new TriggerTime("abc", "30"); // Formato ora non valido
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMinutesFormat() {
        new TriggerTime("10", "xyz"); // Formato minuti non valido
    }

    @Test
    public void testSetAndGetHour() {
        assertEquals("10", trigger.getHour());
        trigger.setHour("12");
        assertEquals("12", trigger.getHour());
    }

    @Test
    public void testSetAndGetMinutes() {
        assertEquals("30", trigger.getMinutes());
        trigger.setMinutes("45");
        assertEquals("45", trigger.getMinutes());
    }
    
    @Test
    public void testGetTrigger() {
        assertEquals("Time -> 10:30", trigger.getTrigger());
        trigger.setHour("09");
        trigger.setMinutes("15");
        assertEquals("Time -> 09:15", trigger.getTrigger());
    }
}
