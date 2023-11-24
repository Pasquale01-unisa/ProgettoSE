/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.model.action;

import java.io.Serializable;
import javafx.scene.control.Alert;

/**
 *
 * @author sara
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Promemoria");
        alert.setHeaderText("È tempo del tuo promemoria!");
        alert.setContentText(memo);
        alert.showAndWait(); 
    }
}
