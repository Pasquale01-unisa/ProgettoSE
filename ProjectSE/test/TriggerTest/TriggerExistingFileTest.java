/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TriggerTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import projectse.model.trigger.TriggerExistingFile;

public class TriggerExistingFileTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCheckTriggerFileExists() throws IOException {
        String fileToCheck = "testFile.txt";
        TriggerExistingFile trigger = new TriggerExistingFile(fileToCheck, tempFolder.getRoot().getAbsolutePath());

        File testFile = tempFolder.newFile(fileToCheck);

        assertTrue(trigger.checkTrigger());
    }

    @Test
    public void testCheckTriggerFileDoesNotExist() {
        String fileToCheck = "nonexistentFile.txt";
        TriggerExistingFile trigger = new TriggerExistingFile(fileToCheck, tempFolder.getRoot().getAbsolutePath());

        assertFalse(trigger.checkTrigger());
    }

    @Test
    public void testGetTrigger() {
        String fileToCheck = "sampleFile.txt";
        String directoryToCheck = tempFolder.getRoot().getAbsolutePath();
        TriggerExistingFile trigger = new TriggerExistingFile(fileToCheck, directoryToCheck);

        String triggerMessage = trigger.getTrigger();

        assertEquals("If file " + fileToCheck + " exists in directory " + directoryToCheck + " then do action!", triggerMessage);
    }
}
