/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

/**
 *
 * @author sara
 */
public class ActionMoveFileFactory extends ActionFactory{
    private String fromPathString;
    private String toDirectoryPathString;
    
    public ActionMoveFileFactory(String fromPathString, String toDirectoryPathString) {
        this.fromPathString = fromPathString;
        this.toDirectoryPathString = toDirectoryPathString;
    }
    
    @Override
    public Action createAction() {
        return new ActionMoveFile(fromPathString, toDirectoryPathString);
    }   
}
