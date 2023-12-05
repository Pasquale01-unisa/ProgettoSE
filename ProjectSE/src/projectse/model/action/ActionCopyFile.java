/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.File;
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

/**
 *
 * @author viki0
 */
public class ActionCopyFile implements Action, Serializable{
    private String sourceFile;
    private String destinationDirectory;
    
    public ActionCopyFile(String sourceFile,String destinationDirectory){
        this.destinationDirectory = destinationDirectory;
        this.sourceFile = sourceFile;
    }

    @Override
    public String getAction() {
        return "File -> " + sourceFile + " will be copied to directory -> " + destinationDirectory;
    }

    @Override
    public void executeAction() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            Path fromPath = Paths.get(sourceFile);
            Path toPath = Paths.get(destinationDirectory, fromPath.getFileName().toString());

            try {
                Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Copia File");
                alert.setHeaderText(null);
                alert.setContentText("File copiato con successo: " + fromPath.getFileName().toString());
            } catch (IOException e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Errore!");
                alert.setHeaderText(null);
                alert.setContentText("Si è verificato un errore durante la copia del file: " + e.getMessage());
                e.printStackTrace(); // Visualizza lo stack trace in caso di errore
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
    
    
    

