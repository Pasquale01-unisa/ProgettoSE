/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TriggerTest;
import java.io.IOException;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import projectse.model.trigger.TriggerSizeFile;

/**
 *
 * @author group07
 */
public class TriggerSizeFileTest {

    private TriggerSizeFile trigger;
    private String size;
    private String fileToCheck;
    private String typeSize;
    private Path testFilePath;

    @Before
    public void setUp() throws IOException {
        size = "100";
        typeSize = "KB";
        fileToCheck = "testFile.txt";
        testFilePath = Paths.get(fileToCheck);

        Files.createFile(testFilePath);
        Files.write(testFilePath, new byte[1024 * 150]);

        trigger = new TriggerSizeFile(size, fileToCheck, typeSize);
    }

    @After
    public void tearDown() throws IOException {
        // Delete the test file after each test
        Files.deleteIfExists(testFilePath);
    }

    @Test
    public void testGetTrigger() {
        String expectedTrigger = "If file " + fileToCheck + " is bigger than " + size + " " + typeSize + " then do action!";
        assertEquals(expectedTrigger, trigger.getTrigger());
    }

    @Test
    public void testCheckTrigger_FileSizeLarger_ShouldReturnTrue() {
        assertTrue(trigger.checkTrigger());
    }

    @Test
    public void testCheckTrigger_FileSizeSmaller_ShouldReturnFalse() throws IOException {
        
        Files.write(testFilePath, new byte[1024 * 80]);
        assertFalse(trigger.checkTrigger());
    }
}
