/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ActionTest;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import projectse.model.action.ActionOpenExternalProgram;

/**
 *
 * @author sara
 */
public class ActionOpenExternalProgramTest {
    private ActionOpenExternalProgram action;
    private ActionOpenExternalProgram badAction;
    private File file;
    private File badFile;

    @Before
    public void setUp() {
        new JFXPanel();
        String args = "Sara;Abagnale";
        String badArgs = "";
        file = new File("Saluti.java");
        badFile = new File("Hello.java");
        action = new ActionOpenExternalProgram(args, file);
        badAction = new ActionOpenExternalProgram(badArgs, badFile);
    }

    @Test
    public void testGetAction() {
        String expected = "Open External Program -> " + "Sara;Abagnale";
        assertEquals(expected, action.getAction());
    }

    @Test
    public void testExecuteActionWithJavaFile() throws InterruptedException, InterruptedException {
        assertTrue("The file must exist", file.exists());
        
        final CountDownLatch deleteLatch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            action.executeAction();
            deleteLatch.countDown();
        });
        
        deleteLatch.await(1, TimeUnit.SECONDS);

        int expectedExitCode = 0; // 0 indica un'esecuzione senza errori
        int actualExitCode = action.getLastExitCode();

        assertEquals("The exit code should be 0", expectedExitCode, actualExitCode);
    }
    
    @Test
    public void testExecuteActionWithJavaFileNotExisting() throws InterruptedException, InterruptedException {
        assertFalse("The file must not exist", badFile.exists());
        
        final CountDownLatch deleteLatch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            badAction.executeAction();
            deleteLatch.countDown();
        });
        
        deleteLatch.await(1, TimeUnit.SECONDS);

        int actualCompileExitCode = badAction.getCompileExitCode();

        assertTrue("Error during the compilation", actualCompileExitCode!=0);
    }
}
