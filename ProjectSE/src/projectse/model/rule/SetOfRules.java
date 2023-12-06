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




public class SetOfRules extends Observable implements Rule, Serializable {
    private List<SingleRule> rules; // Usa una normale ArrayList

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
        setChanged(); // Indica che lo stato dell'oggetto è cambiato
        notifyObservers(this.rules); // Notifica tutti gli observer
    }

    @Override
    public void deleteRule(SingleRule rule) {
        rules.remove(rule);
        setChanged(); // Indica che lo stato dell'oggetto è cambiato
        notifyObservers(this.rules); // Notifica tutti gli observer
    }
}
