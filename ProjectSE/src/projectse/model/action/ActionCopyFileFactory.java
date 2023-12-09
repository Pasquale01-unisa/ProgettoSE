/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

/**
 *
 * @author group07
 */
public class ActionCopyFileFactory extends ActionFactory{
    private String sourceFile;
    private String destinationDirectory;

    public ActionCopyFileFactory(String sourceFile, String destinationDirectory) {
        this.sourceFile = sourceFile;
        this.destinationDirectory = destinationDirectory;
    }
    
    @Override
    public Action createAction() {
        return new ActionCopyFile(sourceFile, destinationDirectory);
    }
    
}
