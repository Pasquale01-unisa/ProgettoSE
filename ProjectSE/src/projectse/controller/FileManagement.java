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
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import projectse.model.rule.SingleRule;

/**
 *
 * @author pasqualegambino
 */
public class FileManagement {
    private static File file=new File("fileRule.txt");
    
    public static void saveRulesToFile(ObservableList<SingleRule> rules) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<SingleRule>(rules)); // Salva l'intera lista
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }
    }

    // Metodo per caricare le regole dal file utilizzando la serializzazione
    public static void loadRulesFromFile(ObservableList<SingleRule> rules) {
        if (!file.exists()) return; // Se il file non esiste, non c'è nulla da caricare

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<SingleRule> loadedRules = (List<SingleRule>) ois.readObject();
            rules.clear();
            rules.addAll(loadedRules);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }
    
}
