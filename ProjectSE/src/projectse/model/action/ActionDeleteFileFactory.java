/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.File;

/**
 *
 * @author sara
 */
public class ActionDeleteFileFactory extends ActionFactory{
    private File file;

    public ActionDeleteFileFactory(File file) {
        this.file = file;
    }
    
    @Override
    public Action createAction() {
        return new ActionDeleteFile(file);
    }
}
