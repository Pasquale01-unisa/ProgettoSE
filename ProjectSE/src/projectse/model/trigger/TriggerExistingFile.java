/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author group07
 */
public class TriggerExistingFile implements Trigger, Serializable{
    String fileToCheck;
    String directoryToCheck;

    public TriggerExistingFile(String fileToCheck, String directoryToCheck) {
        this.fileToCheck = fileToCheck;
        this.directoryToCheck = directoryToCheck;
    }
    
    @Override
    public boolean checkTrigger(){
        File file = new File(directoryToCheck + "/" + fileToCheck);
        return file.exists();
    }
    
    @Override
    public String getTrigger(){
        return "If file " + fileToCheck + " exists in directory " + directoryToCheck + " then do action!";
    }
}
