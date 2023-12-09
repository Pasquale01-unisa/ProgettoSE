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

/**
 *
 * @author group07
 */
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
    private List<SingleRule> list; //Reference to the list where the singleRule is found
    
    public SingleRule(String name, Trigger trigger, Action action, String state, SetOfRules list) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = state;
        this.isSelectedValue = false;
        this.list = list.getRules();
        this.addObserver(FileManagement.getInstance()); //add the listener to the instance of FileManagement
    }

    //SET METHODS
    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
            setChanged();
            notifyObservers(this.list); //Notify the fileManagement Observer to say that something is changed in the singleRule
        }
    }

    public void setTrigger(Trigger trigger) {
        if (this.trigger != trigger) {
            this.trigger = trigger;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setAction(Action action) {
        if (this.action != action) {
            this.action = action;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setState(String state) {
        if (!Objects.equals(this.state, state)) {
            this.state = state;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setIsSelectedValue(boolean isSelectedValue) {
        if (this.isSelectedValue != isSelectedValue) {
            this.isSelectedValue = isSelectedValue;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setIsShow(boolean isShow) {
        if (this.isShow != isShow) {
            this.isShow = isShow;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setCreation(LocalDateTime creation) {
        if (!Objects.equals(this.creation, creation)) {
            this.creation = creation;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setSleepingTime(Duration sleepingTime) {
        if (!Objects.equals(this.sleepingTime, sleepingTime)) {
            this.sleepingTime = sleepingTime;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setRepeat(boolean repeat) {
        if (this.repeat != repeat) {
            this.repeat = repeat;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setSleeping(boolean sleeping) {
        if (this.sleeping != sleeping) {
            this.sleeping = sleeping;
            setChanged();
            notifyObservers(this.list);
        }
    }

    public void setRepetition(LocalDateTime repetition) {
        if (!Objects.equals(this.repetition, repetition)) {
            this.repetition = repetition;
            setChanged();
            notifyObservers(this.list);
        }
    }
    
    public void setObserver() {
        this.addObserver(FileManagement.getInstance());
    }

    //GET METHODS
    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public boolean getIsSelectedValue() {
        return isSelectedValue;
    }

    public boolean isShow() {
        return isShow;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public Duration getSleepingTime() {
        return sleepingTime;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public LocalDateTime getRepetition() {
        return repetition;
    }
    
    public Trigger getTriggerObject(){
        return trigger;
    }
    
    public String getTrigger() {
        return trigger.getTrigger();
    }

    public Action getActionObject(){
        return action;
    }
    
    public String getAction() {
        return action.getAction();
    }

    @Override
    public void addRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}
