/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

/**
 *
 * @author group07
 */
public class TriggerSizeFileFactory extends TriggerFactory{
    String size;
    String fileToCheck;
    String typeSize;

    public TriggerSizeFileFactory(String size, String fileToCheck, String typeSize) {
        this.size = size;
        this.fileToCheck = fileToCheck;
        this.typeSize = typeSize;
    }
    
    @Override
    public Trigger createTrigger(){
        return new TriggerSizeFile(size,fileToCheck,typeSize);
    }
    
}
