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
        // Inizializza l'ambiente JavaFX (se ancora necessario)
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
        // Assicurati che il file esista prima di eseguire l'azione
        Assert.assertTrue("Il file temporaneo deve esistere prima dell'esecuzione", tempFile.exists());

        Platform.runLater(() -> {
            ActionAppendFile actionAppendFile = new ActionAppendFile(testString, tempFile);
            actionAppendFile.executeAction();
            latch.countDown();
        });

        // Attendi il completamento dell'azione
        latch.await(10, TimeUnit.SECONDS);
        Thread.sleep(2000); // Attendi per assicurarti che l'azione di scrittura sia completata

        // Leggi il contenuto del file
        List<String> fileContent = Files.readAllLines(Paths.get(tempFile.toURI()));

        // Verifica che il contenuto del file includa la stringa testata
        Assert.assertTrue("Il file dovrebbe contenere la stringa di test", fileContent.contains(testString));
    }
}
