/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectse.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;
import projectse.model.action.Action;
import projectse.model.action.ActionMemo;
import projectse.model.rule.Rule;
import projectse.model.rule.SetOfRules;
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
    private SetOfRules rules = new SetOfRules(ruleList);
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
        columnCheck.setCellValueFactory(new PropertyValueFactory<>("isSelected"));
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
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
        // Configurare la colonna della checkbox per utilizzare una proprietà booleana della tua classe Rule
        // Assumendo che tu abbia un campo booleano (ad esempio, isSelected) in Rule
        columnCheck.setCellFactory(tc -> new CheckBoxTableCell<SingleRule, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    SingleRule rule = getTableView().getItems().get(getIndex());
                    checkBox.selectedProperty().bindBidirectional(rule.isSelectedProperty());
                    checkBox.setOnAction(e -> updateButtonState());
                }
            }
        });

        
        rules.getRules().addListener((ListChangeListener.Change<? extends SingleRule> change) -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateButtonState();
                }
            }
        });
        updateButtonState();
        RuleCheckerService ruleCheckerService = new RuleCheckerService(rules.getRules(), this);
        ruleCheckerService.start();
        tableView.setItems(rules.getRules());

        
        
    }

    
    
    private void updateDeleteButtonState() {
        
    }
    

    @FXML
    private void onCheckBox(ActionEvent event) {
        boolean isSelected = checkTotal.isSelected();
        for (SingleRule rule : rules.getRules()) {
            rule.setIsSelected(isSelected);
        }
        tableView.refresh(); // Aggiorna la TableView per mostrare le modifiche
        updateButtonState(); // Aggiorna lo stato dei pulsanti
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
        Trigger trigger = null;
        Action action = null;
        if (btnTrigger.getText().equals("Time")){
            trigger = new TriggerTime(numberTriggerH.getValue().toString(), numberTriggerM.getValue().toString());
        }

        if (btnAction.getText().equals("Memo")){
            action = new ActionMemo(textAction.getText());
        }

        SingleRule newRule = new SingleRule(textRuleName.getText(), trigger, action, "Active");
        newRule.isSelectedProperty().addListener((obs, oldVal, newVal) -> updateDeleteButtonState());
        rules.addRule(newRule);
        textRuleName.clear();
        numberTriggerH.getValueFactory().setValue(00);
        numberTriggerM.getValueFactory().setValue(00);
        textAction.clear();
        btnTrigger.setText("Choose a Trigger");
        btnAction.setText("Choose an Action"); 
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
    
    private void showDetails(SingleRule selectedRule) {
        if (selectedRule == null) {
            // Nessuna riga selezionata
            return;
        }

        // Crea un Alert per mostrare i dettagli
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dettagli Regola");
        alert.setHeaderText("Informazioni sulla Regola Selezionata");

        // Costruisci il messaggio con i dettagli della riga
        StringBuilder details = new StringBuilder();
        details.append("Nome: ").append(selectedRule.getName()).append("\n");
        details.append("Trigger: ").append(selectedRule.getTrigger()).append("\n");
        details.append("Azione: ").append(selectedRule.getAction()).append("\n");
        details.append("State: ").append((selectedRule.getState()));

        alert.setContentText(details.toString());

        // Mostra l'Alert
        alert.showAndWait();

        // Deseleziona la riga nella TableView nel thread dell'interfaccia utente
        Platform.runLater(() -> {
            tableView.getSelectionModel().clearSelection();
            
        });
    }
    
    private void updateButtonState() {
        boolean anySelected = false;
        for (SingleRule rule : rules.getRules()) {
            if (rule.getIsSelected()) {
                anySelected = true;
                break;
            }
        }
        btnDelete.setDisable(!anySelected);
        btnOnOff.setDisable(!anySelected);
    }
    
    public void update(){
        tableView.refresh();
    }
    
    
}
