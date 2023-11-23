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

    public TriggerTime(String hour, String minutes) {
        if (isValidHour(hour) && isValidMinutes(minutes)) {
            this.hour = hour;
            this.minutes = minutes;
        } else {
            throw new IllegalArgumentException("Ora o minuti non validi");
        }
    }

    private boolean isValidHour(String hour) {
        try {
            int h = Integer.parseInt(hour);
            return h >= 0 && h <= 23;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidMinutes(String minutes) {
        try {
            int m = Integer.parseInt(minutes);
            return m >= 0 && m <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public void checkTrigger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTrigger() {
        return "Time -> " + hour + ":" + minutes;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
    
    
}