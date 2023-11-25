/*
 
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package projectse.model.action;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class ActionAlarm implements Action, Serializable {
    private File file;
    private transient MediaPlayer mediaPlayer;

    public ActionAlarm(File file) {
        setFile(file);
        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initializeMediaPlayer();
    }

    @Override
    public void executeAction() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                mediaPlayer.play();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Allarme");
                alert.setHeaderText("Riproduzione Allarme");
                alert.setContentText("Premi Interrompi per fermare l'allarme.");

                ButtonType buttonTypeInterrompi = new ButtonType("Interrompi");
                alert.getButtonTypes().setAll(buttonTypeInterrompi);

                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeInterrompi) {
                        mediaPlayer.stop();
                    }
                });
            });
        } else {
            System.err.println("MediaPlayer non è stato inizializzato.");
        }
    }

    public void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        if (isValidAudioFile(file)) {
            this.file = file;
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        } else {
            System.err.println("File non supportato. Sono supportati solo file MP3, WAV e AAC.");
        }
    }

    private boolean isValidAudioFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".aac");
    }
    
    @Override
    public String getAction() {
        return "Alarm -> " + file.toString();
    }
    
}