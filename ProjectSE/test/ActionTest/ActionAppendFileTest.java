package ActionTest;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import projectse.model.action.ActionAppendFile;

public class ActionAppendFileTest {
    private File tempFile;
    private final String testString = "Test String";
    private final CountDownLatch latch = new CountDownLatch(1);
    
    @Before
    public void setUp() throws IOException {
        // Inizializza l'ambiente JavaFX
        new JFXPanel();

        // Disattiva gli Alert per i test
        ActionAppendFile.setTestMode(true);
        
        // Crea un file temporaneo
        tempFile = File.createTempFile("testFileAppend", ".txt");
    }

    @After
    public void tearDown() {
        // Riattiva gli Alert dopo i test
        ActionAppendFile.setTestMode(false);
    }

    @Test
    public void testExecuteAction_AppendToFile() throws IOException, InterruptedException {
        Assert.assertTrue("Il file temporaneo deve esistere prima dell'esecuzione", tempFile.exists());

        Platform.runLater(() -> {
            ActionAppendFile actionAppendFile = new ActionAppendFile(testString, tempFile);
            actionAppendFile.executeAction();
            latch.countDown();
        });

        latch.await(1, TimeUnit.SECONDS);
        List<String> fileContent = Files.readAllLines(Paths.get(tempFile.toURI()));
        Assert.assertTrue("Il file dovrebbe contenere la stringa di test", fileContent.contains(testString));
    }
}
