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
import javafx.util.Duration;

/**
 *
 * @author group07
 */
public class ActionAlarm implements Action, Serializable {
    private File file;
    private transient MediaPlayer mediaPlayer;

    public ActionAlarm(File file) {
        setFile(file);
        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        if (file != null) {
            Media media = new Media(file.toURI().toString()); //Create a Media object using the file URL 
            mediaPlayer = new MediaPlayer(media); //Inizialize the MediaPlayer object
        }
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); //Manage MediaPlayer reading from file 
        initializeMediaPlayer(); //Inizialize the MadiaPlayer object
    }
    
    @Override
    public void executeAction() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                // If the song is over it restarts
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                });

                mediaPlayer.play();

                //Prompt to stop the alarm
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alarm");
                alert.setHeaderText("Alarm playback");
                alert.setContentText("Press stop to interrupt the alarm");

                ButtonType buttonTypeInterrompi = new ButtonType("Stop");
                alert.getButtonTypes().setAll(buttonTypeInterrompi);

                //Check, if the user pressed the button the alarm stops
                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeInterrompi) {
                        mediaPlayer.stop();
                        mediaPlayer.setOnEndOfMedia(null); // Remove the listener so it doesn't repeat
                    }
                });
            });
        } else {
            System.err.println("MediaPlayer non è stato inizializzato.");
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