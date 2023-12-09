/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TriggerTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import projectse.model.trigger.TriggerDate;

/**
 *
 * @author Utente
 */

public class TriggerDateTest {

    @Test
    public void testDayOfWeekTrigger() {
        TriggerDate trigger = new TriggerDate(DayOfWeek.MONDAY, null, null, "Day Of Week");
        assertEquals("Day Of Week -> MONDAY", trigger.getTrigger());
        // Test True
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        trigger = new TriggerDate(today, null, null, "Day Of Week");
        assertTrue(trigger.checkTrigger());
        // Test False
        trigger = new TriggerDate(DayOfWeek.SUNDAY, null, null, "Day Of Week");
        assertFalse(trigger.checkTrigger());
    }

    @Test
    public void testDayOfMonthTrigger() {
        TriggerDate trigger = new TriggerDate(null, 15, null, "Day Of Month");
        assertEquals("Day Of Month -> 15", trigger.getTrigger());
        // Test True
        Integer today= LocalDate.now().getDayOfMonth();
        trigger = new TriggerDate(null, today, null, "Day Of Month");
        assertTrue(trigger.checkTrigger());
        // Test false
        trigger = new TriggerDate(null, 20, null, "Day Of Month");
        assertFalse(trigger.checkTrigger());
    }

    @Test
    public void testGenericDateTrigger() {
        LocalDate currentDate = LocalDate.now();
        TriggerDate trigger = new TriggerDate(null, null, currentDate, "Generic Date");
        assertEquals("Date -> " + currentDate.toString(), trigger.getTrigger());
        // Test True
        assertTrue(trigger.checkTrigger());
        //Test false
        LocalDate differentDate = currentDate.plusDays(1);
        trigger = new TriggerDate(null, null, differentDate, "Generic Date");
        assertFalse(trigger.checkTrigger());
    }
}
