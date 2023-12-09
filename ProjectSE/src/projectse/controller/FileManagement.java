/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import projectse.model.rule.SetOfRules;
import projectse.model.rule.SingleRule;

/**
 *
 * @author group07
 */
public class FileManagement implements Observer {
    private static FileManagement instance;
    private static File file;
    
    private FileManagement(){
        file = new File("fileRule.txt");
    }
    
    //PATTERN SINGLETON, I want only one file manager
    // static method to get the instance
    public static FileManagement getInstance() {
        if (instance == null) {
            instance = new FileManagement();
        }
        return instance;
    }

    
    public static void saveRulesToFile(List<SingleRule> rules) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<SingleRule>(rules)); // Save the intere list
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }
    }

    public static void loadRulesFromFile(SetOfRules setOfRules) {
        if (!file.exists()) return; // If the file doesn't exist, there is nothing to load

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<SingleRule> loadedRules = (List<SingleRule>) ois.readObject();
            for (SingleRule rule : loadedRules) {
                rule.setObserver();
                setOfRules.addRule(rule); // Use the addRule method to add the rule, so I can add the observer to each observer
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("File vuoto: " + e.getMessage());
        }
    }

    //Observer method to update the file
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List<?>) {
            saveRulesToFile((List<SingleRule>) arg);
        }
    }  
}