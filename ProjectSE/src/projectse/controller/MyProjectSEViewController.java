/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectse.controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import projectse.model.action.Action;
import projectse.model.action.ActionAlarm;
import projectse.model.action.ActionAppendFile;
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
    private TextField textActionStringToFile;
    @FXML
    private MenuItem btnAppentTextToFile;
    @FXML
    private Button btnCommit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnOnOff;
   
    
    @FXML
    private CheckBox checkTotal;
    
    private File selectedFile = null;


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
            (observable, oldValue, newValue) -> {
                if (newValue != null && !tableView.getSelectionModel().isEmpty()) {
                    showDetails(newValue);
                }
            }
        );
       
        btnCommit.disableProperty().bind(
            textRuleName.textProperty().isEmpty()
            .or(textAction.textProperty().isEmpty())
            .or(Bindings.createBooleanBinding(() ->
                btnAction.getText().equals("Choose an Action"), btnAction.textProperty()))
            .or(Bindings.createBooleanBinding(() ->
                btnTrigger.getText().equals("Choose a Trigger"), btnTrigger.textProperty()))
        );
        btnFile.setManaged(false);
        textActionStringToFile.setManaged(false);
        // Configura gli Spinner per le ore e i minuti
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        numberTriggerH.setValueFactory(hoursFactory);
        numberTriggerM.setValueFactory(minutesFactory);

        // Personalizza la visualizzazione dei valori negli Spinner
        setupSpinnerWithCustomTextFormatter(numberTriggerH, true); // Per ore
        setupSpinnerWithCustomTextFormatter(numberTriggerM, false); // Per minuti

        // Configurare la colonna della checkbox per utilizzare una proprietà booleana della tua classe Rule
        // Assumendo che tu abbia un campo booleano (ad esempio, isSelected) in Rule
        columnCheck.setCellFactory(tc -> new CheckBoxTableCell<SingleRule, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = new CheckBox();
                    SingleRule rule = getTableView().getItems().get(getIndex());
                    checkBox.selectedProperty().bindBidirectional(rule.isSelectedProperty());
                    checkBox.setOnAction(e -> {
                        rule.setIsSelected(checkBox.isSelected());
                        updateButtonState();
                    });
                    setGraphic(checkBox);
                } else {
                    setGraphic(null);
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
        btnAddTrigger.setDisable(true);
        btnAddAction.setDisable(true);
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
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        textAction.clear();
        btnAction.setText("Alarm");
    }
    
    @FXML
    private void onBtnMemo(ActionEvent event) {
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        textAction.setDisable(false);
        btnFile.setManaged(false);
        btnFile.setVisible(false);
        textAction.clear();
        textAction.setPromptText("Inserisci promemoria"); // Imposta un placeholder o un suggerimento
        btnAction.setText("Memo"); // Cambia il testo del MenuButton
    }
    @FXML
    private void onBtnAppentTextToFile(ActionEvent event) {
        textAction.setDisable(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        textAction.clear();
        textActionStringToFile.setPromptText("Inserisci Testo");
        btnAction.setText("Append text to file");
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
        else if (btnAction.getText().equals("Alarm")){
            action = new ActionAlarm(selectedFile);
        }
        else if (btnAction.getText().equals("Append text to file")){
            action = new ActionAppendFile(textActionStringToFile.getText(),selectedFile);
        }
        SingleRule newRule = new SingleRule(textRuleName.getText(), trigger, action, "Active", rules.getRules());
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
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Conferma Eliminazione");
        confirmAlert.setHeaderText("Sei sicuro di voler eliminare le regole selezionate?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            rules.getRules().stream().filter(SingleRule::getIsSelected)
                .collect(Collectors.toList()).forEach(rules::deleteRule);

            updateButtonState();
        }
    }


    @FXML
    private void onBtnOnOff(ActionEvent event) {
        rules.getRules().stream().filter(SingleRule::getIsSelected)
            .forEach(rule -> {
                // Cambia lo stato da "Active" a "Inactive" e viceversa
                rule.setState(rule.getState().equals("Active") ? "Deactivated" : "Active");

                // Controlla se la regola è attiva e l'hai già vista
                if (rule.getState().equals("Active") && rule.isIsShow()) {
                    rule.setIsShow(false); // Rimetti isShow a false in modo che venga ricontrollata
                }   
        });
        tableView.refresh();
        updateButtonState();
    }
    
    @FXML
    private void onBtnFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        if(btnAction.getText().equals("Alarm")){
            fileChooser.setTitle("Seleziona un File Audio");

            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File Audio (*.mp3, *.wav, *.aac)", "*.mp3", "*.wav", "*.aac");
            fileChooser.getExtensionFilters().add(filter);

            selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                textAction.setText(selectedFile.getName()); 
            }
        }else{
            fileChooser.setTitle("Seleziona un file sul quale scrivere una stringa");
            
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File di testo (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(filter);
            
            selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                textAction.setText(selectedFile.getName()); 
            }

        }
        
    }

    private void setupSpinnerWithCustomTextFormatter(Spinner<Integer> spinner, boolean isHour) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), spinner.getValue(), change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Permette il campo vuoto
            }

            try {
                int newValue = Integer.parseInt(newText);
                if ((isHour && newValue >= 0 && newValue <= 23) || (!isHour && newValue >= 0 && newValue <= 59)) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Non fa nulla se non è un numero valido
            }

            return null; // Ignora le modifiche non valide
        });

        spinner.getEditor().setTextFormatter(formatter);
        spinner.setEditable(true); // Rendi lo Spinner modificabile

        formatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                String text = String.format("%02d", newValue); // Formatta come stringa a due cifre
                if (!text.equals(spinner.getEditor().getText())) {
                    spinner.getEditor().setText(text);
                }
                spinner.getValueFactory().setValue(newValue);
            }
        });
        spinner.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Quando lo spinner perde il focus
                Integer currentValue = spinner.getValue();
                if (currentValue != null) {
                    String text = String.format("%02d", currentValue);
                    spinner.getEditor().setText(text);
                }
            }
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
