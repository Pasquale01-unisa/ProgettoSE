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
 * @author pasqualegambino
 */
public class FileManagement implements Observer {
    private static FileManagement instance;
    private static File file;
    
    private FileManagement(){
        file = new File("fileRule.txt");
    }
    
    // Metodo statico per ottenere l'istanza
    public static FileManagement getInstance() {
        if (instance == null) {
            instance = new FileManagement();
        }
        return instance;
    }

    
    public static void saveRulesToFile(List<SingleRule> rules) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<SingleRule>(rules)); // Salva l'intera lista
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }
    }

    public static void loadRulesFromFile(SetOfRules setOfRules) {
        if (!file.exists()) return; // Se il file non esiste, non c'è nulla da caricare

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<SingleRule> loadedRules = (List<SingleRule>) ois.readObject();
            for (SingleRule rule : loadedRules) {
                rule.setObserver();
                setOfRules.addRule(rule); // Usa il metodo addRule per aggiungere la regola
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List<?>) {
            saveRulesToFile((List<SingleRule>) arg);
        }
    }

    
}