/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.rule;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import projectse.model.action.Action;
import projectse.model.trigger.Trigger;

/**
 *
 * @author sara
 */
public class SingleRule implements Rule{
    private Trigger trigger;
    private Action action;
    private String name;
    private String state;
    private BooleanProperty isSelected;// Campo booleano per la gestione delle checkbox

    // Costruttore
    public SingleRule(String name, Trigger trigger, Action action, String state) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = state;
        this.isSelected = new SimpleBooleanProperty(false); // Inizialmente non selezionato
    }

    // Getter e Setter per name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter e Setter per trigger
    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    // Getter e Setter per action
    public Action getAction() {
        return action;
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
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }
    
    @Override
    public void addRule(Rule rule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRule(Rule rule) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
