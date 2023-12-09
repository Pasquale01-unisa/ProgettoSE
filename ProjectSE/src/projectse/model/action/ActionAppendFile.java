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
import projectse.controller.MyProjectSEViewController;

/**
 *
 * @author group07
 */
public class ActionAppendFile implements Action, Serializable {
    private String stringToWriteInFile;
    private File fileToAppend;
    private static boolean isTestMode = false; // Variable to check the test modality
    
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
    
    public static boolean isTestMode() {
        return isTestMode;
    }

    public static void setTestMode(boolean isTestMode) {
        ActionAppendFile.isTestMode = isTestMode;
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
        if (fileToAppend == null || !fileToAppend.exists()) {
            //check for the class test
            if (!isTestMode) {
                MyProjectSEViewController.showErrorPopup("Invalid or nonexisting file", "File :" + fileToAppend + " invalid or nonexisting");
            }
            return;
        }
        //write on file 
        try (FileWriter fileWriter = new FileWriter(fileToAppend, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(stringToWriteInFile);
            if (!isTestMode) {
                MyProjectSEViewController.showSuccessPopup("Text Iserted in File", "Text successfully inserted into the file: " + fileToAppend, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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