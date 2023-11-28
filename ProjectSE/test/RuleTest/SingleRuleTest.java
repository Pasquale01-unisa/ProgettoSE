/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package RuleTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;
import projectse.model.action.ActionMemo;
import projectse.model.rule.Rule;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.TriggerTime;

/**
 *
 * @author sara
 */
public class SingleRuleTest {
    SingleRule rule;
    SingleRule ruleException;
    ObservableList<SingleRule> rules =  FXCollections.observableArrayList();
    
    @Before
    public void setUp(){
        rule = new SingleRule("TestRule", new TriggerTime("00", "00"), new ActionMemo("TestAction"), "Active", rules);
        ruleException = new SingleRule("TestRule", new TriggerTime("00", "01"), new ActionMemo("TestAction"), "Deactivated", rules);
    }
    
    @Test 
    public void getSetNameTest(){
        assertEquals("TestRule", rule.getName());

        rule.setName("NewTestRule");
        assertEquals("NewTestRule", rule.getName());
    }
    
    @Test 
    public void getSetTriggerTest(){
        assertEquals("Time -> 00:00", rule.getTrigger());

        rule.setTrigger(new TriggerTime("01", "00"));
        assertEquals("Time -> 01:00", rule.getTrigger());
    }
    
    @Test 
    public void getSetActionTest(){
        assertEquals("Memo -> TestAction", rule.getAction());

        rule.setAction(new ActionMemo("TestAction2"));
        assertEquals("Memo -> TestAction2", rule.getAction());
    }
    
    @Test 
    public void getSetStateTest(){
        assertEquals("Active", rule.getState());

        rule.setState("Deactivated");
        assertEquals("Deactivated", rule.getState());
    }
    
    @Test
    public void getSetIsSelectedTest(){
        assertFalse(rule.getIsSelected());

        rule.setIsSelected(true);
        assertTrue(rule.getIsSelected());
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void addRuleTest(){
        rule.addRule(ruleException);
    }
   
    @Test(expected=UnsupportedOperationException.class)
    public void deleteRuleTest(){
        rule.deleteRule(ruleException);
    }
}
