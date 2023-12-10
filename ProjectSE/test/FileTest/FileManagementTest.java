/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package FileTest;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import projectse.controller.FileManagement;
import projectse.model.action.ActionMemo;
import projectse.model.rule.SetOfRules;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.TriggerTime;

/**
 *
 * @author group07
 */
public class FileManagementTest {

    private SetOfRules rules;
    private SetOfRules badRules;

    @Before
    public void setUp() {
        rules = new SetOfRules();
        badRules = new SetOfRules();
        rules.addRule(new SingleRule("Regola1", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Active", rules));
        rules.addRule(new SingleRule("Regola2", new TriggerTime("21", "01"), new ActionMemo("Update Trello"), "Active", rules));
        
    }

    @Test
    public void testSaveAndLoadRules() {
        FileManagement.saveRulesToFile(rules.getRules());
        SetOfRules loadedRules = new SetOfRules();
        FileManagement.loadRulesFromFile(loadedRules);

        assertEquals("Le liste dovrebbero avere la stessa dimensione", rules.getRules().size(), loadedRules.getRules().size());
    }

    @Test
    public void testLoadRulesFromNonExistingFile() {
        File file = new File("fileRule.txt");
        if (file.exists()) {
            file.delete();
        }
        SetOfRules loadedRules = new SetOfRules();
        FileManagement.loadRulesFromFile(loadedRules);

        assertTrue("La lista dovrebbe essere vuota se il file non esiste", loadedRules.getRules().isEmpty());
    }
}