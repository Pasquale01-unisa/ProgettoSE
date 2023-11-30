/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ActionTest;
import javafx.embed.swing.JFXPanel;
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
import projectse.model.action.ActionAppendFile;

public class ActionAppendFileTest {

    private File tempFile;
    private final String testString = "Test String";
    private final CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setUp() throws IOException {
        // Inizializza l'ambiente JavaFX
        new JFXPanel();

        // Crea un file temporaneo
        tempFile = File.createTempFile("testFile", ".txt");
    }

    @Test
    public void testExecuteAction_AppendToFile() throws InterruptedException, IOException {
        // Assicurati che il file esista prima di eseguire l'azione
        Assert.assertTrue("Il file temporaneo deve esistere prima dell'esecuzione", tempFile.exists());

        // Crea e esegui l'azione per appendere la stringa al file
        ActionAppendFile actionAppendFile = new ActionAppendFile(testString, tempFile);
        actionAppendFile.executeAction();

        // Attendi un tempo breve per permettere a Platform.runLater() di eseguire l'azione
        latch.await(3, TimeUnit.SECONDS); // Aspetta fino a un massimo di 3 secondi per l'esecuzione

        // Leggi il contenuto del file
        List<String> fileContent = Files.readAllLines(Paths.get(tempFile.toURI()));

        // Verifica che il contenuto del file includa la stringa testata
        Assert.assertTrue("Il file dovrebbe contenere la stringa di test",
                          fileContent.contains(testString));
    }
}
