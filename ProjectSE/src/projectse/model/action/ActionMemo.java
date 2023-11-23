/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

/**
 *
 * @author sara
 */
public class ActionMemo implements Action{
    private String memo;
    
    @Override
    public String getAction() {
        return "Memo -> " + memo;
    }

    @Override
    public void executeAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
