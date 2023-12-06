/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

/**
 *
 * @author sara
 */
public class ActionMemoFactory extends ActionFactory{
    String memo;

    public ActionMemoFactory(String memo) {
        this.memo = memo;
    }

    @Override
    public Action createAction() {
        return new ActionMemo(memo);
    }    
}
