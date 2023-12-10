/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package RuleTest;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import projectse.model.action.ActionMemo;
import projectse.model.rule.SetOfRules;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.TriggerTime;

/**
 *
 * @author group07
 */
public class SetOfRulesTest {
    private SetOfRules setOfRules;

    @Before
    public void setUp() {
        setOfRules = new SetOfRules();
    }

    @Test
    public void testAddRule() {
        SingleRule rule = new SingleRule("Regola1", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Active", setOfRules); 
        setOfRules.addRule(rule);

        List<SingleRule> rules = setOfRules.getRules();
        assertTrue("Rule should be added", rules.contains(rule));
    }

    @Test
    public void testDeleteRule() {
        SingleRule rule = new SingleRule("Regola1", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Active", setOfRules);
        setOfRules.addRule(rule);
        setOfRules.deleteRule(rule);

        List<SingleRule> rules = setOfRules.getRules();
        assertFalse("Rule should be deleted", rules.contains(rule));
    }

    @Test
    public void testInitialState() {
        List<SingleRule> rules = setOfRules.getRules();
        assertTrue("Initial rules list should be empty", rules.isEmpty());
    }
}

