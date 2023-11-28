/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package FileTest;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projectse.controller.FileManagement;
import projectse.model.action.ActionMemo;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.TriggerTime;

/**
 *
 * @author Utente
 */
public class FileManagementTest {
    
    private ObservableList<SingleRule> rules;
    private ObservableList<SingleRule> badRules;
    
    @Before
    public void setUp() {
        rules = FXCollections.observableArrayList();
        badRules = FXCollections.observableArrayList();
        rules.add(new SingleRule("Regola1", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Active", rules));
        rules.add(new SingleRule("Regola2", new TriggerTime("21", "01"), new ActionMemo("Update Trello"), "Active", rules));
        badRules.add(new SingleRule("Regola1", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Active", badRules));
    }

    @Test
    public void testSaveAndLoadRules() {
        FileManagement.saveRulesToFile(rules);
        ObservableList<SingleRule> loadedRules = FXCollections.observableArrayList();
        FileManagement.loadRulesFromFile(loadedRules);

        assertEquals("Le liste dovrebbero avere la stessa dimensione", rules.size(), loadedRules.size());
    }
    
    @Test
    public void testLoadRulesFromNonExistingFile() {
        File file = new File("fileRule.txt");
        if (file.exists()) {
            file.delete();
        }
        ObservableList<SingleRule> loadedRules = FXCollections.observableArrayList();
        FileManagement.loadRulesFromFile(loadedRules);

        assertTrue("La lista dovrebbe essere vuota se il file non esiste", loadedRules.isEmpty());
    }
}
