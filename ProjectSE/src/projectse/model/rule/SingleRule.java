/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.rule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import projectse.controller.FileManagement;
import projectse.model.action.Action;
import projectse.model.trigger.Trigger;

/**
 *
 * @author sara
 */
public class SingleRule implements Rule, Serializable{
    private Trigger trigger;
    private Action action;
    private String name;
    private transient StringProperty stateProperty;
    private String state;
    private transient BooleanProperty isSelected;
    private boolean isSelectedValue;
    private boolean isShow = false;
    private transient ObservableList<SingleRule> rules;
    //---------------
    private LocalDateTime creation;
    private Duration sleepingTime;
    private boolean repeat = false;
    private boolean sleeping = false;
    private LocalDateTime repetition;

    // Costruttore
    public SingleRule(String name, Trigger trigger, Action action, String state, ObservableList<SingleRule> rules){
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = state;
        this.stateProperty = new SimpleStringProperty(state);
        this.isSelected = new SimpleBooleanProperty(false);
        this.isSelectedValue = false;
        stateProperty.addListener((observable, oldValue, newValue) -> {
            this.state = newValue; // Aggiorna il campo serializzabile
            FileManagement.saveRulesToFile(rules); 
        });
    }
    //----

    public LocalDateTime getRepetition() {
        return repetition;
    }

    public void setRepetition(LocalDateTime repetition) {
        this.repetition = repetition;
    }
  
    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public Duration getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(Duration sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }
    
    public boolean isRepeat() {
        return repeat;
    }
    
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    //----
    // Getter e Setter per name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trigger getTriggerObject(){
        return trigger;
    }
    
    // Getter e Setter per trigger
    public String getTrigger() {
        return trigger.getTrigger();
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Action getActionObject(){
        return action;
    }
    
    // Getter e Setter per action
    public String getAction() {
        return action.getAction();
    }

    public void setAction(Action action) {
        this.action = action;
    }

    // Getter e Setter per state
    public String getState() {
        return stateProperty.get();
    }

    public void setState(String state) {
        this.stateProperty.set(state); 
    }

    public StringProperty getStateProperty() {
        return stateProperty;
    }

    public void setStateProperty(StringProperty stateProperty) {
        this.stateProperty = stateProperty;
    }
    
    // Getter e Setter per isSelected
    public boolean getIsSelected() {
        return isSelected.get();
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
        this.isSelectedValue = isSelected; // Mantieni sincronizzato il valore primitivo
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public boolean isIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }
    
    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        aInputStream.defaultReadObject();
        isSelected = new SimpleBooleanProperty(false);
        this.stateProperty = new SimpleStringProperty(this.state);
    }
    
    @Override
    public void addRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public void setRulesList(ObservableList<SingleRule> rules) {
        this.rules = rules;
        
        stateProperty.addListener((observable, oldValue, newValue) -> {
            this.state = newValue;
            FileManagement.saveRulesToFile(rules); // Assicurati di avere accesso alla lista delle regole
        });
    }

    
    
    
}
