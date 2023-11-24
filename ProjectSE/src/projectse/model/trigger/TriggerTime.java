/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sara
 */
public class TriggerTime implements Trigger {
    private String hour;
    private String minutes;

    public TriggerTime(String hour, String minutes) {
        setHour(hour);
        setMinutes(minutes);
    }

    public void setHour(String hour) {
        if (isValidHour(hour)) {
            this.hour = String.format("%02d", Integer.parseInt(hour));
        } else {
            throw new IllegalArgumentException("Ora non valida");
        }
    }

    public void setMinutes(String minutes) {
        if (isValidMinutes(minutes)) {
            this.minutes = String.format("%02d", Integer.parseInt(minutes));
        } else {
            throw new IllegalArgumentException("Minuti non validi");
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
    public boolean checkTrigger() {
        LocalTime tempoAttuale = LocalTime.now();
        String orario = this.getHour() + ":" + this.getMinutes();
        LocalTime tempoTrigger = LocalTime.parse(orario, DateTimeFormatter.ofPattern("HH:mm"));
        return tempoAttuale.getHour() == tempoTrigger.getHour() && tempoAttuale.getMinute() == tempoTrigger.getMinute();
    }

    @Override
    public String getTrigger() {
        return "Time -> " + hour + ":" + minutes;
    }

    public String getHour() {
        return hour;
    }


    public String getMinutes() {
        return minutes;
    }  
}