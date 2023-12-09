/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.Serializable;
import projectse.controller.MyProjectSEViewController;

/**
 *
 * @author group07
 */
public class ActionMemo implements Action, Serializable{
    private String memo;

    public ActionMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    @Override
    public String getAction() {
        return "Memo -> " + memo;
    }

    @Override
    public void executeAction() {
        MyProjectSEViewController.showSuccessPopup("Time for your reminder!", this.getMemo(), true);
    }
}
