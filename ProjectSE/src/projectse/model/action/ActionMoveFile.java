package projectse.model.action;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class ActionMoveFile implements Action, Serializable {
    private String fromPathString;
    private String toDirectoryPathString; 

    public ActionMoveFile(String fromPathString, String toDirectoryPathString) {
        this.fromPathString = fromPathString;
        this.toDirectoryPathString = toDirectoryPathString;
    }

    @Override
    public String getAction() {
        Path fromPath = Paths.get(fromPathString);
        return "Move file -> " + fromPath.getFileName().toString() + " from " + fromPathString + " to " + toDirectoryPathString;
    }

    @Override
    public void executeAction() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            Path fromPath = Paths.get(fromPathString);
            Path toPath = Paths.get(toDirectoryPathString, fromPath.getFileName().toString());

            try {
                Files.move(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Spostamento File");
                alert.setHeaderText(null);
                alert.setContentText("File spostato con successo: " + fromPath.getFileName().toString());
            } catch (IOException e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Errore!");
                alert.setHeaderText(null);
                alert.setContentText("Si è verificato un errore durante lo spostamento del file: " + e.getMessage());
            }

            // Timeline per chiudere l'alert automaticamente dopo 2 secondi
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                ae -> alert.close()));
            timeline.play();

            alert.showAndWait();
        });
    }
}