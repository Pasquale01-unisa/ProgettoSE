package projectse.model.action;

import java.io.File;
import java.io.Serializable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class ActionDeleteFile implements Action, Serializable {
    private File file;

    public ActionDeleteFile(File file) {
        this.file = file;
    }

    @Override
    public String getAction() {
        return "Delete file -> " + file.toString();
    }

    @Override
    public void executeAction() {
        Platform.runLater(() -> {
            if (file != null && file.exists()) {
                boolean isDeleted = file.delete();
                Alert alert;
                if (isDeleted) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Eliminazione File");
                    alert.setHeaderText(null);
                    alert.setContentText("File eliminato con successo: " + file);
                    // Timeline per chiudere l'alert automaticamente dopo 2 secondi
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> alert.close()));
                    timeline.play();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText("Impossibile eliminare il file: " + file);
                    // Timeline per chiudere l'alert automaticamente dopo 2 secondi
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> alert.close()));
                    timeline.play();
                }
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("File Non Trovato");
                alert.setHeaderText(null);
                alert.setContentText("File non trovato o già eliminato: " + file);
                // Timeline per chiudere l'alert automaticamente dopo 2 secondi
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> alert.close()));
                    timeline.play();
                alert.showAndWait();
            }
        });
    }
}

