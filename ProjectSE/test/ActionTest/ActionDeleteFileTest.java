package ActionTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import projectse.model.action.ActionDeleteFile;

public class ActionDeleteFileTest {
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        // Crea un file temporaneo per il testing
        new JFXPanel();
        tempFile = File.createTempFile("testFile", ".txt");
    }

    @Test
    public void testExecuteAction_FileExists() throws InterruptedException {
        Assert.assertTrue("Il file temporaneo deve esistere prima dell'esecuzione", tempFile.exists());

        // Imposta un nuovo CountDownLatch per attendere il completamento dell'azione
        final CountDownLatch deleteLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            ActionDeleteFile action = new ActionDeleteFile(tempFile);
            action.executeAction();

            // Decrementa il latch quando l'alert è stato mostrato
            deleteLatch.countDown();
        });

        // Aspetta che l'alert sia stato gestito
        deleteLatch.await(1, TimeUnit.SECONDS);

        Assert.assertFalse("Il file dovrebbe essere stato eliminato", tempFile.exists());
    }
    
    @Test
    public void testExecuteAction_FileDoesNotExist() throws InterruptedException {
        boolean deleted = tempFile.delete();
         Assert.assertTrue("Il file temporaneo deve essere eliminato prima dell'esecuzione", deleted);
   
        final CountDownLatch deleteLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            ActionDeleteFile action = new ActionDeleteFile(tempFile);
            action.executeAction();

            deleteLatch.countDown();
        });

        deleteLatch.await(1, TimeUnit.SECONDS); 
        Assert.assertFalse("Il file dovrebbe essere stato eliminato", tempFile.exists());
    }
}
