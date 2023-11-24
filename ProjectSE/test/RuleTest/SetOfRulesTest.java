/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package RuleTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projectse.model.action.ActionMemo;
import projectse.model.rule.Rule;
import projectse.model.rule.SetOfRules;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.TriggerTime;


/**
 *
 * @author sara
 */
public class SetOfRulesTest {
    ObservableList<SingleRule> rules;
    SetOfRules setOfRules;
    SingleRule rule;
    
    @Before
    public void setUp() {
        rules = FXCollections.observableArrayList();
        setOfRules = new SetOfRules(rules);  
        rule = new SingleRule("Regola1", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Active"); 
    }
    
    @Test
    public void testGetRule() {
         rules.add(rule);
         ObservableList<SingleRule> retrievedRules = setOfRules.getRules();
         assertEquals(rules, retrievedRules);
    }
    
    @Test
    public void testAddRule() {
        setOfRules.addRule(rule);
        assertTrue(rules.contains(rule));
    }
    
    @Test
    public void testDeleteRule() {
        setOfRules.addRule(rule);
        setOfRules.deleteRule(rule);
        assertFalse(rules.contains(rule));
    }
    
    @Test
    public void testDeleteAll() {
        setOfRules.addRule(rule);
        SingleRule rule1 = new SingleRule("Regola2", new TriggerTime("21", "00"), new ActionMemo("Update Trello!"), "Active"); 
        setOfRules.addRule(rule1);
        setOfRules.deleteAll();
        assertTrue(setOfRules.getRules().isEmpty());
    }
}
