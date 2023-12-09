/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ActionTest;

import java.io.File;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projectse.model.action.ActionAlarm;

/**
 *
 * @author sara
 */
public class ActionAlarmTest {
    File testFile, result;
    ActionAlarm action;
    
    @BeforeClass
    public static void setUpClass() {
        // Inizializza l'ambiente JavaFX
        new JFXPanel();
    }
    
    @Before
    public void setUp(){
        testFile = new File("song01.wav");
        action = new ActionAlarm(testFile);
    }
    
    @Test
    public void testActionAlarm(){
        assertNotNull(action);
    }
    
    @Test
    public void testGetFile(){
        result=action.getFile();
        assertEquals(testFile, result);
    }
    
    @Test
    public void testSetFile() {
        File newFile = new File("song02.wav");
        action.setFile(newFile);
        result = action.getFile();
        assertEquals(newFile, result);
    }
    
    private boolean isValidAudioFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".aac");
    }
        
    @Test
    public void testGetAction() {
        String resultString = action.getAction();
        assertEquals("Alarm -> " + testFile.toString() , resultString);
    }
    
    @Test
    public void testIsValidAudioFileWithMP3() {
        File file = new File("test.mp3");
        assertTrue("Il file dovrebbe essere un file audio valido", isValidAudioFile(file));
    }

    @Test
    public void testIsValidAudioFileWithWAV() {
        File file = new File("test.wav");
        assertTrue("Il file dovrebbe essere un file audio valido", isValidAudioFile(file));
    }

    @Test
    public void testIsValidAudioFileWithAAC() {
        File file = new File("test.aac");
        assertTrue("Il file dovrebbe essere un file audio valido", isValidAudioFile(file));
    }

    @Test
    public void testIsInvalidAudioFile() {
        File file = new File("test.txt");
        assertFalse("Il file non dovrebbe essere un file audio valido", isValidAudioFile(file));
    }
}
