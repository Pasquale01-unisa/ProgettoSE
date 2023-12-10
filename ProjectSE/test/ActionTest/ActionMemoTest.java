/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ActionTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import projectse.model.action.ActionMemo;

/**
 *
 * @author group07
 */
public class ActionMemoTest {
    private ActionMemo action;
    String memoText;

    @Before
    public void setUp() {
        action = new ActionMemo("Test Memo");
        memoText = "Test Memo";
    }

    @Test
    public void testGetMemo() {
        String result = action.getMemo();
        assertEquals(memoText, result);
    }
    
    @Test
    public void testSetMemo() {
        String newMemo = "New Memo";
        action.setMemo(newMemo);
        assertEquals(newMemo, action.getMemo());
    }

    @Test
    public void testGetAction() {
        String result = action.getAction();
        assertEquals("Memo -> " + memoText, result);
    }
}


