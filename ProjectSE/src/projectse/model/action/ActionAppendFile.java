/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ActionAppendFile implements Action, Serializable {
    private String stringToWriteInFile;
    private File fileToAppend;

    public ActionAppendFile(String stringToWriteInFile, File fileToAppend) {
        this.stringToWriteInFile = stringToWriteInFile;
        setFileToAppend(fileToAppend);
    }

    public String getStringToWriteInFile() {
        return stringToWriteInFile;
    }

    public void setStringToWriteInFile(String stringToWriteInFile) {
        this.stringToWriteInFile = stringToWriteInFile;
    }

    public File getFileToAppend() {
        return fileToAppend;
    }

    public void setFileToAppend(File fileToAppend) {
        if (fileToAppend == null) {
            System.err.println("Il file passato è null.");
            return;
        }
        if (isValidFile(fileToAppend)) {
            this.fileToAppend = fileToAppend;
        } else {
            System.err.println("File non supportato. Sono supportati solo file TXT.");
        }
    }

    @Override
    public void executeAction() {
        Platform.runLater(() -> {
            if (fileToAppend == null || !fileToAppend.exists()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File non valido o non esiste");
                    alert.setHeaderText(null);
                    alert.setContentText("Il file :" + fileToAppend + " non e' valido o non esiste");
            }
            try (FileWriter fileWriter = new FileWriter(fileToAppend, true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                printWriter.println(stringToWriteInFile);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Testo Inserito nel File");
                alert.setHeaderText(null);
                alert.setContentText("Testo inserito con successo nel file: " + fileToAppend);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public String getAction() {
        return "String -> " + "(" + stringToWriteInFile + ")" + " will be written in file -> " + fileToAppend;
    }
    
    private boolean isValidFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".txt");
    }
}