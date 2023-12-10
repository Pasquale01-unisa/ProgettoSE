/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author group07
 */
public class TriggerSizeFile implements Trigger, Serializable{
    private String size;
    private String fileToCheck;
    private String typeSize;

    public TriggerSizeFile(String size, String fileToCheck, String typeSize) {
        this.size = size;
        this.fileToCheck = fileToCheck;
        this.typeSize = typeSize;
    }

    @Override
    public String getTrigger() {
        return "If file " + fileToCheck + " is bigger than " + size + " " + typeSize + " then do action!";
    }

    @Override
    public boolean checkTrigger() {
        try {
            int sizeInt = Integer.parseInt(size);
            Path filePath = Paths.get(fileToCheck);
            long fileSize = Files.size(filePath);
            if("Byte".equals(typeSize)){
                return fileSize > sizeInt;
            }else if("KB".equals(typeSize)){
                return fileSize/1024 > sizeInt;
            }else if("MB".equals(typeSize)){
                return fileSize/(1024*1024) > sizeInt;
            }else if("GB".equals(typeSize)){
                return fileSize/(1024*1024*1024) > sizeInt;
            }
        } catch (IOException ex) {
            Logger.getLogger(TriggerSizeFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
}
