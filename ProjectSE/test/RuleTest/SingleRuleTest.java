package RuleTest;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.time.Duration;
import static org.junit.Assert.*;
import projectse.model.action.Action; // Assuming you have an Action interface or class with a constructor
import projectse.model.action.ActionMemo;
import projectse.model.rule.SetOfRules;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.Trigger; // Assuming you have a Trigger interface or class with a constructor
import projectse.model.trigger.TriggerTime;

public class SingleRuleTest {

    private SingleRule singleRule;
    private SetOfRules setOfRules;

    @Before
    public void setUp() {
        // Initialize SetOfRules and SingleRule here. Assuming Action and Trigger have proper constructors.
        setOfRules = new SetOfRules();
        singleRule = new SingleRule("RuleName", new TriggerTime("09", "00"), new ActionMemo("Scrum Daily Meeting"), "Deactivated", setOfRules);
    }

    @Test
    public void testSetName() {
        String newName = "NewRuleName";
        singleRule.setName(newName);
        assertEquals("The name should be updated to NewRuleName", newName, singleRule.getName());
    }

    
    @Test
    public void testSetTrigger() {
        Trigger newTrigger = new TriggerTime("09", "00"); // Assuming constructor
        singleRule.setTrigger(newTrigger);
        assertSame("The trigger should be updated", newTrigger, singleRule.getTriggerObject());
    }

    @Test
    public void testSetAction() {
        Action newAction = new ActionMemo("Scrum Daily Meeting"); // Assuming constructor
        singleRule.setAction(newAction);
        assertSame("The action should be updated", newAction, singleRule.getActionObject());
    }

    @Test
    public void testSetState() {
        String newState = "Active";
        singleRule.setState(newState);
        assertEquals("The state should be updated to NewState", newState, singleRule.getState());
    }

    @Test
    public void testSetIsSelectedValue() {
        singleRule.setIsSelectedValue(true);
        assertTrue("The isSelectedValue should be true", singleRule.getIsSelectedValue());
    }

    @Test
    public void testSetIsShow() {
        singleRule.setIsShow(true);
        assertTrue("The isShow should be true", singleRule.isShow());
    }

    @Test
    public void testSetCreation() {
        LocalDateTime newCreation = LocalDateTime.now();
        singleRule.setCreation(newCreation);
        assertEquals("The creation date should be updated", newCreation, singleRule.getCreation());
    }

    
}
