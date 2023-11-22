/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectse.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * FXML Controller class
 *
 * @author pasqualegambino
 */
public class MyProjectSEViewController implements Initializable {

    @FXML
    private TableView<?> tableView; 
    @FXML
    private TableColumn<?, ?> columnCheck; 
    @FXML
    private TableColumn<?, ?> columnName; 
    @FXML
    private TableColumn<?, ?> columnTrigger; 
    @FXML
    private TableColumn<?, ?> columnAction; 
    @FXML
    private TableColumn<?, ?> columnState;
    
    @FXML
    private MenuItem btnTime;
    

    @FXML
    private Button btnFile;
    @FXML
    private TextField textRuleName;
    @FXML
    private MenuButton btnTrigger;
    @FXML
    private Button btnAddTrigger;
    @FXML
    private Spinner numberTriggerH;
    @FXML
    private Spinner numberTriggerM;
    
    @FXML
    private MenuButton btnAction;
    @FXML
    private Button btnAddAction;
    @FXML
    private TextField textAction;
    @FXML
    private Button btnCommit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnOnOff;
    
    @FXML
    private CheckBox checkTotal;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCommit.disableProperty().bind(
            textRuleName.textProperty().isEmpty()
            .or(textAction.textProperty().isEmpty())
            .or(Bindings.createBooleanBinding(() ->
                btnAction.getText().equals("Choose an Action"), btnAction.textProperty()))
            .or(Bindings.createBooleanBinding(() ->
                btnTrigger.getText().equals("Choose a Trigger"), btnTrigger.textProperty()))
        );
    }

    
    
    private void updateDeleteButtonState() {
        
    }
    

    @FXML
    private void onCheckBox(ActionEvent event) {
        
    }

    @FXML
    private void onTextFieldName(ActionEvent event) {
    }

    @FXML
    private void onBtnTrigger(ActionEvent event) {
       
    }
    
    @FXML
    private void onBtnTime(ActionEvent event) {
        btnTrigger.setText("Time"); // Cambia il testo del MenuButton 
    }


    @FXML
    private void onBtnAddTrigger(ActionEvent event) {
    }
    
    @FXML
    private void onBtnAlarm(ActionEvent event) {
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        textAction.clear();
        btnAction.setText("Alarm");
    }
    
    @FXML
    private void onBtnMemo(ActionEvent event) {
        textAction.setDisable(false);
        btnFile.setManaged(false);
        btnFile.setVisible(false);
        textAction.clear();
        textAction.setPromptText("Inserisci promemoria"); // Imposta un placeholder o un suggerimento
        btnAction.setText("Memo"); // Cambia il testo del MenuButton
    }
    
    
    @FXML
    private void onBtnAction(ActionEvent event) {
    }

    @FXML
    private void onBtnAddAction(ActionEvent event) {
    }

    @FXML
    private void onTextFieldAction(ActionEvent event) {
    }

    @FXML
    private void onBtnCommit(ActionEvent event) {
        
    }


    @FXML
    private void onBtnDelete(ActionEvent event) {
       
    }

    @FXML
    private void onBtnOnOff(ActionEvent event) {
        
    }
    
    @FXML
    private void onBtnFile(ActionEvent event) {
        
    }

    
    
}
