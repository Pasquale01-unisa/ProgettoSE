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
public class ActionAppendFileFactory extends ActionFactory{
    private String str;
    private File file;

    public ActionAppendFileFactory(String str, File file) {
        this.str = str;
        this.file = file;
    }
    
    @Override
    public Action createAction() {
        return new ActionAppendFile(str, file);
    }
}
