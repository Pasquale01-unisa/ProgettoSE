/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.trigger;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 *
 * @author group07
 */
public class TriggerDateFactory extends TriggerFactory{
    private DayOfWeek dayOfWeek;
    private Integer dayOfMonth;
    private LocalDate specificDate;
    private String triggerType;

    public TriggerDateFactory(String dW, Integer dayOfMonth, LocalDate specificDate, String triggerType) {
        if(!dW.equalsIgnoreCase("WEEKDAYS")){
            this.dayOfWeek = DayOfWeek.valueOf(dW);
        }
        else this.dayOfWeek = null;
        if(!triggerType.equalsIgnoreCase("Day Of Month")){
            this.dayOfMonth = null;
        }
        else this.dayOfMonth = dayOfMonth;
        this.specificDate = specificDate;
        this.triggerType = triggerType;
    }

    @Override
    public Trigger createTrigger() {
       return new TriggerDate(dayOfWeek, dayOfMonth, specificDate, triggerType);
    }
    
}
