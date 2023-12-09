/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

/**
 *
 * @author group07
 */
public class TriggerTimeFactory extends TriggerFactory{
    private String hour;
    private String minutes;

    public TriggerTimeFactory(String hour, String minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    @Override
    public Trigger createTrigger() {
        return new TriggerTime(hour, minutes);
    }
}
