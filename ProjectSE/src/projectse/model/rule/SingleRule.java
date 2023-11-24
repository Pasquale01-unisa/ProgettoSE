/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.rule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private String state;
    private transient BooleanProperty isSelected;
    private boolean isSelectedValue;
    private boolean isShow = false;

    // Costruttore
    public SingleRule(String name, Trigger trigger, Action action, String state) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = state;
        this.isSelected = new SimpleBooleanProperty(false);
        this.isSelectedValue = false;
    }

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
        return state;
    }

    public void setState(String state) {
        this.state = state; 
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
        isSelected = new SimpleBooleanProperty(isSelectedValue);
    }

    @Override
    public void addRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRule(SingleRule rule) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
}
