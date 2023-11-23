/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.rule;

import javafx.collections.ObservableList;

/**
 *
 * @author sara
 */
public class SetOfRules implements Rule{
    private ObservableList<Rule> rules;
    
    public SetOfRules(ObservableList<Rule> rules) {
        this.rules = rules;
    }

    public ObservableList<Rule> getRules() {
        return rules;
    }
    
    @Override
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    @Override
    public void deleteRule(Rule rule) {
        rules.remove(rule);
    }

    @Override
    public void deleteAll() {
        rules.clear();
    }
}
