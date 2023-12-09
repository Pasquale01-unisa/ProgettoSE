/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 *
 * @author group07
 */
public class TriggerDate implements Trigger, Serializable{
    private DayOfWeek dayOfWeek;
    private Integer dayOfMonth;
    private LocalDate specificDate;
    private String triggerType;

    public TriggerDate(DayOfWeek dayOfWeek, Integer dayOfMonth, LocalDate specificDate, String triggerType) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.specificDate = specificDate;
        this.triggerType = triggerType;
    }

    @Override
    public String getTrigger() {
        switch (triggerType) {
            case "Day Of Week":
               return "Day Of Week -> " + dayOfWeek.toString();
            case "Day Of Month":
               return "Day Of Month -> " + dayOfMonth.toString();
            case "Generic Date":
               return "Date -> " + specificDate.toString();
            default:
                throw new IllegalStateException("Tipo di TriggerDate non supportato: " + triggerType);
        }         
    }
    
    @Override
    public boolean checkTrigger() {
        LocalDate currentDate = LocalDate.now();
        //If the currentDate is different from the day/date the user specified the trigger is not executed
        switch (triggerType) {
            case "Day Of Week":
                if (currentDate.getDayOfWeek() != dayOfWeek) {
                    return false;
                }
                break;
            case "Day Of Month":
                if (currentDate.getDayOfMonth() != dayOfMonth) {
                    return false;
                }
                break;
            case "Generic Date":
                if (!currentDate.isEqual(specificDate)) {
                    return false;
                }
                break;
            default:
                throw new IllegalStateException("Tipo di TriggerDate non supportato: " + triggerType);
        }
        return true;
    }
}
