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
public class ActionAlarmFactory extends ActionFactory{
    File file;

    public ActionAlarmFactory(File file) {
        this.file = file;
    }
    
    @Override
    public Action createAction() {
        return new ActionAlarm(file);
    }
}
