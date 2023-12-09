/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ActionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import projectse.model.action.ActionCopyFile;

public class ActionCopyFileTest {

    private Path tempFile;
    private Path tempDirectory;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("testFile", ".txt");
        // Crea una directory temporanea
        tempDirectory = Files.createTempDirectory("testDir");
        new JFXPanel();
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDirectory.resolve(tempFile.getFileName()));
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void testFileCopy() throws IOException, InterruptedException {
        String originalFilePath = tempFile.toString();
        String destinationDirectoryPath = tempDirectory.toString();
        final CountDownLatch deleteLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ActionCopyFile actionCopyFile = new ActionCopyFile(originalFilePath, destinationDirectoryPath);
            actionCopyFile.executeAction();
            deleteLatch.countDown();
        });
        deleteLatch.await(1, TimeUnit.SECONDS);
        
        assertTrue("Il file non e' stato spostato correttamente", Files.exists(tempDirectory.resolve(tempFile.getFileName())));
    }
}