/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import projectse.controller.FileManagement;

/**
 *
 * @author group07
 */
public class SetOfRules extends Observable implements Rule, Serializable {
    private List<SingleRule> rules;

    public SetOfRules() {
        this.rules = new ArrayList<>();
        this.addObserver(FileManagement.getInstance());
    }

    public List<SingleRule> getRules() {
        return rules;
    }

    @Override
    public void addRule(SingleRule rule) {
        rules.add(rule);
        setChanged(); // Indicates that the state of the object has changed
        notifyObservers(this.rules); // Notify all the observers that something was added
    }

    @Override
    public void deleteRule(SingleRule rule) {
        rules.remove(rule);
        setChanged();
        notifyObservers(this.rules);
    }
}
