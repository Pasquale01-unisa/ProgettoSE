package projectse.model.rule;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import projectse.controller.FileManagement;
import projectse.model.action.Action;
import projectse.model.trigger.Trigger;

public class SingleRule extends Observable implements Rule, Serializable {
    private Trigger trigger;
    private Action action;
    private String name;
    private String state;
    private boolean isSelectedValue;
    private boolean isShow = false;
    private LocalDateTime creation;
    private Duration sleepingTime;
    private boolean repeat = false;
    private boolean sleeping = false;
    private LocalDateTime repetition;
    private List<SingleRule> list;
    
    public SingleRule(String name, Trigger trigger, Action action, String state, SetOfRules list) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = state;
        this.isSelectedValue = false;
        this.list = list.getRules();
        this.addObserver(FileManagement.getInstance());
    }
    
    

    // Metodo setter per 'name'
    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'trigger'
    public void setTrigger(Trigger trigger) {
        if (this.trigger != trigger) {
            this.trigger = trigger;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'action'
    public void setAction(Action action) {
        if (this.action != action) {
            this.action = action;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'state'
    public void setState(String state) {
        if (!Objects.equals(this.state, state)) {
            this.state = state;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'isSelectedValue'
    public void setIsSelectedValue(boolean isSelectedValue) {
        if (this.isSelectedValue != isSelectedValue) {
            this.isSelectedValue = isSelectedValue;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'isShow'
    public void setIsShow(boolean isShow) {
        if (this.isShow != isShow) {
            this.isShow = isShow;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'creation'
    public void setCreation(LocalDateTime creation) {
        if (!Objects.equals(this.creation, creation)) {
            this.creation = creation;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'sleepingTime'
    public void setSleepingTime(Duration sleepingTime) {
        if (!Objects.equals(this.sleepingTime, sleepingTime)) {
            this.sleepingTime = sleepingTime;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'repeat'
    public void setRepeat(boolean repeat) {
        if (this.repeat != repeat) {
            this.repeat = repeat;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'sleeping'
    public void setSleeping(boolean sleeping) {
        if (this.sleeping != sleeping) {
            this.sleeping = sleeping;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo setter per 'repetition'
    public void setRepetition(LocalDateTime repetition) {
        if (!Objects.equals(this.repetition, repetition)) {
            this.repetition = repetition;
            setChanged();
            notifyObservers(this.list);
        }
    }

    // Metodo getter per 'name'
    public String getName() {
        return name;
    }

    // Metodo getter per 'state'
    public String getState() {
        return state;
    }

    // Metodo getter per 'isSelectedValue'
    public boolean getIsSelectedValue() {
        return isSelectedValue;
    }

    // Metodo getter per 'isShow'
    public boolean isShow() {
        return isShow;
    }

    // Metodo getter per 'creation'
    public LocalDateTime getCreation() {
        return creation;
    }

    // Metodo getter per 'sleepingTime'
    public Duration getSleepingTime() {
        return sleepingTime;
    }

    // Metodo getter per 'repeat'
    public boolean isRepeat() {
        return repeat;
    }

    // Metodo getter per 'sleeping'
    public boolean isSleeping() {
        return sleeping;
    }

    // Metodo getter per 'repetition'
    public LocalDateTime getRepetition() {
        return repetition;
    }

    @Override
    public void addRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
       public Trigger getTriggerObject(){
        return trigger;
    }
    
    // Getter e Setter per trigger
    public String getTrigger() {
        return trigger.getTrigger();
    }

    

    public Action getActionObject(){
        return action;
    }
    
    // Getter e Setter per action
    public String getAction() {
        return action.getAction();
    }

    public void setObserver() {
        this.addObserver(FileManagement.getInstance());
    }

    
}
