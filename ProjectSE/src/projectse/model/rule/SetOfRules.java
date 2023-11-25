/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.rule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import projectse.controller.FileManagement;

/**
 *
 * @author sara
 */
public class SetOfRules implements Rule{
    private ObservableList<SingleRule> rules;
    
    public SetOfRules(ObservableList<SingleRule> rules) {
        this.rules = rules;
        FileManagement.loadRulesFromFile(rules);
    }

    public ObservableList<SingleRule> getRules() {
        return rules;
    }
    
    @Override
    public void addRule(SingleRule rule) {
        rules.add(rule);
        FileManagement.saveRulesToFile(rules); 
    }

    @Override
    public void deleteRule(SingleRule rule) {
        rules.remove(rule);
        FileManagement.saveRulesToFile(rules);
    }

    @Override
    public void deleteAll() {
        rules.clear();
        FileManagement.saveRulesToFile(rules);
    }
}
