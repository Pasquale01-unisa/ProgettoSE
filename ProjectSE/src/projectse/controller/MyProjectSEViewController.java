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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;
import projectse.model.action.Action;
import projectse.model.action.ActionMemo;
import projectse.model.rule.Rule;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.Trigger;
import projectse.model.trigger.TriggerTime;

/**
 * FXML Controller class
 *
 * @author pasqualegambino
 */
public class MyProjectSEViewController implements Initializable {

    @FXML
    private TableView<SingleRule> tableView; 
    @FXML
    private TableColumn<SingleRule, Boolean> columnCheck; 
    @FXML
    private TableColumn<SingleRule, String> columnName; 
    @FXML
    private TableColumn<SingleRule, String> columnTrigger; 
    @FXML
    private TableColumn<SingleRule, String> columnAction;
    @FXML
    private TableColumn<SingleRule, String> columnState;
    
    private ObservableList<SingleRule> ruleList = FXCollections.observableArrayList();
    
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
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnTrigger.setCellValueFactory(new PropertyValueFactory<>("trigger"));
        columnAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        btnCommit.disableProperty().bind(
            textRuleName.textProperty().isEmpty()
            .or(textAction.textProperty().isEmpty())
            .or(Bindings.createBooleanBinding(() ->
                btnAction.getText().equals("Choose an Action"), btnAction.textProperty()))
            .or(Bindings.createBooleanBinding(() ->
                btnTrigger.getText().equals("Choose a Trigger"), btnTrigger.textProperty()))
        );
        btnFile.setManaged(false);
        // Configura gli Spinner per le ore e i minuti
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        numberTriggerH.setValueFactory(hoursFactory);
        numberTriggerM.setValueFactory(minutesFactory);

        // Personalizza la visualizzazione dei valori negli Spinner
        setupSpinnerWithCustomTextFormatter(numberTriggerH);
        setupSpinnerWithCustomTextFormatter(numberTriggerM);
        tableView.setItems(ruleList);

        
        
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

    private void setupSpinnerWithCustomTextFormatter(Spinner<Integer> spinner) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter() {
            @Override
            public String toString(Integer value) {
                // Questo metodo formatta il valore da visualizzare nell'editor
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                // Questo metodo converte da stringa a valore
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }, 0, // Default value
        change -> {
            // Questo filtro valida il testo inserito nell'editor
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,2}")) {
                return change;
            }
            return null;
        });

        spinner.getEditor().setTextFormatter(formatter);
        spinner.getValueFactory().setValue(0); // Imposta il valore iniziale dello Spinner

        // Aggiorna il testo dell'editor ogni volta che il valore dello spinner cambia
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            formatter.setValue(newValue);
        });
    }
    
    
}
