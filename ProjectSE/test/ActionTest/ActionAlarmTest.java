/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ActionTest;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
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
    
    @Before
    public void setUp(){
        testFile = new File("test.txt");
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
        File newFile = new File("new.txt");
        action.setFile(newFile);
        result = action.getFile();
        assertEquals(newFile, result);
    }
    
    @Test
    public void testGetAction() {
        String resultString = action.getAction();
        assertEquals("Alarm -> " + testFile.toString() , resultString);
    }
}
