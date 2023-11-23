/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

/**
 *
 * @author sara
 */
public class TriggerTime implements Trigger{
    private String hour;
    private String minutes;
    
    @Override
    public void checkTrigger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTrigger() {
        return "Time -> " + hour + ":" + minutes;
    }
}