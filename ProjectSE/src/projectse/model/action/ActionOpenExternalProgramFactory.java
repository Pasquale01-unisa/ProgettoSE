/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.File;

/**
 *
 * @author group07
 */
public class ActionOpenExternalProgramFactory extends ActionFactory{
    private String args;
    private File file;

    public ActionOpenExternalProgramFactory(String args, File file) {
        this.args = args;
        this.file = file;
    }

    @Override
    public Action createAction() {
        return new ActionOpenExternalProgram(args, file);
    }
}
