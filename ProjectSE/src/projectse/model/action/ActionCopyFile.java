/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import projectse.controller.MyProjectSEViewController;

/**
 *
 * @author group07
 */
public class ActionCopyFile implements Action, Serializable{
    private String sourceFile;
    private String destinationDirectory;
    
    public ActionCopyFile(String sourceFile, String destinationDirectory){
        this.destinationDirectory = destinationDirectory;
        this.sourceFile = sourceFile;
    }

    @Override
    public String getAction() {
        return "File -> " + sourceFile + " will be copied to directory -> " + destinationDirectory;
    }

    @Override
    public void executeAction() {
        Path fromPath = Paths.get(sourceFile);
        Path toPath = Paths.get(destinationDirectory, fromPath.getFileName().toString());

        try {
            Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
            MyProjectSEViewController.showSuccessPopup("Copy File", "File successfully copied: " + fromPath.getFileName().toString(), false);
        } catch (IOException e) {
            MyProjectSEViewController.showErrorPopup("Error!", "An error occurred during the copy of the file: " + e.getMessage());
        }
    }
}
    
    
    

