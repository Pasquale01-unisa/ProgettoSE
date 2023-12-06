/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

/**
 *
 * @author viki0
 */
public class TriggerExistingFileFactory extends TriggerFactory {
    String fileToCheck;
    String directoryToCheck;

    public TriggerExistingFileFactory(String fileToCheck, String directoryToCheck) {
        this.fileToCheck = fileToCheck;
        this.directoryToCheck = directoryToCheck;
    }
    
    @Override
    public Trigger createTrigger(){
        return new TriggerExistingFile(fileToCheck,directoryToCheck);
    }
}
