/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectse.controller;

import javafx.stage.WindowEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import projectse.model.action.*;
import projectse.model.rule.Rule;
import projectse.model.rule.SetOfRules;
import projectse.model.rule.SingleRule;
import projectse.model.trigger.Trigger;
import projectse.model.trigger.TriggerExistingFileFactory;
import projectse.model.trigger.TriggerFactory;
import projectse.model.trigger.TriggerTime;
import projectse.model.trigger.TriggerTimeFactory;

/**
 * FXML Controller class
 *
 * @author pasqualegambino
 */
public class MyProjectSEViewController implements Initializable, RuleUpdateCallback, Observer{
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
    
    private SetOfRules rules = new SetOfRules();
    
    private RuleCheckerThread ruleCheckerThread;
    @FXML
    private MenuItem btnTime;
    @FXML
    private MenuItem btnDeleteFile;
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
    private Button btnRepetition;
    @FXML
    private CheckBox checkTotal;
    @FXML
    private MenuItem btnCopyFile;
    @FXML
    private Button btnChooseDirectory;
    @FXML
    private MenuItem btnMoveFile;
    @FXML
    private MenuItem btnOpenExternalProgram;
    @FXML
    private Label argLabel;
    @FXML
    private MenuItem btnFileCheck;
    @FXML
    private Label separatorSpinner;
    @FXML
    private TextField textTriggerDirectoryCheck;
    @FXML
    private TextField textTriggerFileCheck;
    @FXML
    private Button btnChooseDirectoryFileChecker;
    
    private File selectedFile = null;
    private Duration sleepingTime;
    private boolean repeat = false;
    private File selectedDirectory = null;


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
        this.rules.addObserver(this); // Registra il controller come observer

        // Configurare la colonna della checkbox per utilizzare una proprietà booleana della tua classe Rule
        // Assumendo che tu abbia un campo booleano (ad esempio, isSelected) in Rule
        columnCheck.setCellFactory(tc -> new CheckBoxTableCell<SingleRule, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = new CheckBox();
                    SingleRule rule = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(rule.getIsSelectedValue());
                    checkBox.setOnAction(e -> {
                        rule.setIsSelectedValue(checkBox.isSelected());
                        updateButtonState();
                    });
                    setGraphic(checkBox);
                } else {
                    setGraphic(null);
                }
            }
        });

        ruleList.addListener((ListChangeListener.Change<? extends SingleRule> change) -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateButtonState();
                }
            }
        });
        updateButtonState();
        btnAddTrigger.setDisable(true);
        btnAddAction.setDisable(true);
        argLabel.setVisible(false);
        
        ruleCheckerThread = new RuleCheckerThread(ruleList, this);
        Thread thread = new Thread(ruleCheckerThread);
        thread.start();
        // Imposta un listener per la chiusura della finestra
        
        btnChooseDirectory.setManaged(false);
        
        tableView.setItems(ruleList);
        Platform.runLater(() -> {
            // Qui puoi assegnare lo Stage a una variabile o utilizzarlo direttamente
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setOnCloseRequest(this::handleWindowClose);
        });
        
        FileManagement.loadRulesFromFile(rules);
        
    }

    private void updateDeleteButtonState() {
        
    }
    
    @FXML
    private void onCheckBox(ActionEvent event) {
        boolean isSelected = checkTotal.isSelected();
        for (SingleRule rule : ruleList) {
            rule.setIsSelectedValue(isSelected);
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
        btnChooseDirectoryFileChecker.setVisible(false);
        textTriggerDirectoryCheck.setVisible(false);
        textTriggerFileCheck.setVisible(false);
        numberTriggerH.setVisible(true);
        numberTriggerM.setVisible(true);
        separatorSpinner.setVisible(true);
        btnTrigger.setText("Time"); // Cambia il testo del MenuButton 
    }
    
    @FXML
    private void onBtnAddTrigger(ActionEvent event) {
    }
    
    @FXML
    private void onBtnAlarm(ActionEvent event) {
        btnChooseDirectory.setVisible(false);
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        argLabel.setVisible(false);
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
        btnChooseDirectory.setVisible(false);
        argLabel.setVisible(false);
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
        argLabel.setVisible(false);
        textAction.clear();
        textActionStringToFile.setPromptText("Inserisci Testo");
        btnAction.setText("Append text to file");
    }
    
    @FXML
    private void onBtnFileCheck(ActionEvent event){
        numberTriggerH.setVisible(false);
        numberTriggerM.setVisible(false);
        separatorSpinner.setVisible(false);
        btnChooseDirectoryFileChecker.setVisible(true);
        textTriggerFileCheck.setVisible(true);
        textTriggerDirectoryCheck.setVisible(true);
        textTriggerDirectoryCheck.setDisable(true);
        btnTrigger.setText("File Check");
        
    }
    
    @FXML
    private void onBtnAction(ActionEvent event) {
    }
    
    @FXML
    private void onBtnDeleteFile(ActionEvent event) {
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        argLabel.setVisible(false);
        textAction.clear();
        btnAction.setText("Delete file");
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

        action = createActionFactory(btnAction.getText()).createAction();
        trigger = createTriggerFactory(btnTrigger.getText()).createTrigger();
        
        SingleRule newRule = new SingleRule(textRuleName.getText(), trigger, action, "Active", rules);
        newRule.setCreation(LocalDateTime.now());
        if(repeat){
            newRule.setSleepingTime(sleepingTime);
            newRule.setRepeat(true);
            newRule.setRepetition(newRule.getCreation().plus(newRule.getSleepingTime()));
            repeat = false;
        }
        newRule.addObserver((o, arg) -> {
            // Questo metodo viene chiamato quando lo stato di selezione di newRule cambia
            updateDeleteButtonState();
        });
        rules.addRule(newRule);
        textRuleName.clear();
        numberTriggerH.getValueFactory().setValue(00);
        numberTriggerM.getValueFactory().setValue(00);
        textAction.clear();
        textActionStringToFile.clear();
        btnTrigger.setText("Choose a Trigger");
        btnAction.setText("Choose an Action"); 
    }
    
    private ActionFactory createActionFactory(String userChoice){
        switch(userChoice){
            case "Memo":
                return new ActionMemoFactory(textAction.getText());
            case "Alarm":
                return new ActionAlarmFactory(selectedFile);
            case "Append text to file":
                return new ActionAppendFileFactory(textActionStringToFile.getText(), selectedFile);
            case "Delete file":
                return new ActionDeleteFileFactory(selectedFile);
            case "Copy file":
                return new ActionCopyFileFactory(selectedFile.getAbsolutePath(), selectedDirectory.getAbsolutePath());
            case "Move file":
                return new ActionMoveFileFactory(selectedFile.getAbsolutePath(), selectedDirectory.getAbsolutePath());
            case "Open External Program":
                return new ActionOpenExternalProgramFactory(textActionStringToFile.getText(), selectedFile);
            default:
                throw new IllegalArgumentException("Action type not supported: " + userChoice);
        }
    }

    private TriggerFactory createTriggerFactory(String userChoice){
        switch (userChoice) {
            case "Time":
                return new TriggerTimeFactory(numberTriggerH.getValue().toString(), numberTriggerM.getValue().toString());
            case "File Check":
                return new TriggerExistingFileFactory(textTriggerFileCheck.getText(), textTriggerDirectoryCheck.getText());
            default:
                throw new IllegalArgumentException("Trigger type not supported: " + userChoice);
        }
    }
    
    @FXML
    private void onBtnDelete(ActionEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Conferma Eliminazione");
        confirmAlert.setHeaderText("Sei sicuro di voler eliminare le regole selezionate?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ruleList.stream().filter(SingleRule::getIsSelectedValue)
                .collect(Collectors.toList()).forEach(rules::deleteRule);

            updateButtonState();
        }
    }

    @FXML
    private void onBtnOnOff(ActionEvent event) {
        ruleList.stream().filter(SingleRule::getIsSelectedValue)
            .forEach(rule -> {
                rule.setIsShow(false); // Rimetti isShow a false in modo che venga ricontrollata
                // Cambia lo stato da "Active" a "Inactive" e viceversa
                rule.setState(rule.getState().equals("Active") ? "Deactivated" : "Active"); 
        });
        tableView.refresh();
        updateButtonState();
    }

    
    @FXML
    private void onBtnFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        // Controlla se il testo del pulsante btnAction è "Alarm"
        if ("Alarm".equals(btnAction.getText())) {
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File Audio (.mp3,.wav, .aac)", "*.mp3", "*.wav", "*.aac");
            fileChooser.getExtensionFilters().add(filter);
        } else if("Append text to file".equals(btnAction.getText())){
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File di testo (.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(filter);
        } else if("Open External Program".equals(btnAction.getText())){
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File eseguibili (.java)", "*.java");
            fileChooser.getExtensionFilters().add(filter);
        }

        selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            textAction.setText(selectedFile.getName()); 
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
            if (rule.getIsSelectedValue()) {
                anySelected = true;
                break;
            }
        }
        btnDelete.setDisable(!anySelected);
        btnOnOff.setDisable(!anySelected);
    }
    
    @FXML
    private void OnBtnRepetition(ActionEvent event) {
        // Crea il dialogo
        Dialog<Object> dialog = new Dialog<>();
        dialog.setTitle("Set Sleeping Time");

        // Imposta il tipo di bottone
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Crea i due campi di testo
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField textField1 = new TextField();
        textField1.setPromptText("0");
        TextField textField2 = new TextField();
        textField2.setPromptText("0");
        TextField textField3 = new TextField();
        textField3.setPromptText("0");

        grid.add(new Label("Days: "), 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(new Label("Hours: "), 0, 1);
        grid.add(textField2, 1, 1);
        grid.add(new Label("Minutes: "), 0, 2);
        grid.add(textField3, 1, 2);

        // Abilita/Disabilita il bottone OK a seconda se i TextField sono vuoti o meno
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        // Aggiungi un listener per il controllo dei campi di testo
        textField1.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus sul primo campo di testo all'apertura del dialog
        Platform.runLater(textField1::requestFocus);

        // Converti il risultato al click del bottone OK in una coppia di stringhe
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                sleepingTime = Duration.ofDays(Integer.parseInt(textField1.getText())).plusHours(Integer.parseInt(textField2.getText())).plusMinutes(Integer.parseInt(textField3.getText()));
            }
            return null;
        });

        // Mostra il dialog e attendi il risultato
        Optional<Object> result = dialog.showAndWait();
        repeat = true;
    }
    
    @FXML
    private void onBtnCopyFile(ActionEvent event){
        //Function to show the fields when we choose the copy file action
        textAction.setDisable(true);
        textActionStringToFile.setDisable(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        argLabel.setVisible(false);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setManaged(true);
        btnChooseDirectory.setVisible(true);
        
        textAction.clear();
        textActionStringToFile.clear();
        
        btnAction.setText("Copy file");
    }
    
    @FXML
    private void onBtnMoveFile(ActionEvent event){
        //Function to show the fields when we choose the copy file action
        textAction.setDisable(true);
        textActionStringToFile.setDisable(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        argLabel.setVisible(false);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setManaged(true);
        btnChooseDirectory.setVisible(true);
        
        textAction.clear();
        textActionStringToFile.clear();
        
        btnAction.setText("Move file");
    }
    
    @FXML
    private void onBtnChooseDirectory(ActionEvent event){
        //function to choose a directory to move or copy a file
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a Directory");

        selectedDirectory = directoryChooser.showDialog(((Node)event.getSource()).getScene().getWindow());
        if (selectedDirectory != null && btnTrigger.getText().equals("File Check")) {
            textTriggerDirectoryCheck.setText(selectedDirectory.getAbsolutePath());
        }else{
            textActionStringToFile.setText(selectedDirectory.getAbsolutePath());
        } 
    }
    
    @FXML
    private void onBtnOpenExternalProgram(ActionEvent event){
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setVisible(false);
        argLabel.setVisible(true);
        textAction.clear();
        textActionStringToFile.setPromptText("Inserisci argomenti*"); // Imposta un placeholder o un suggerimento
        btnAction.setText("Open External Program"); // Cambia il testo del MenuButton
    }

    @Override
    public void updateUI() {
        // Implementazione per aggiornare l'UI
        Platform.runLater(() -> {
            tableView.refresh();
        });
    }

    @Override
    public void executeAction(SingleRule rule) {
        Platform.runLater(() -> {
            rule.getActionObject().executeAction();
        });
    }
    

    private void handleWindowClose(WindowEvent event) {
        if (ruleCheckerThread != null) {
            ruleCheckerThread.stop();
        }
    }
    
    public static void showErrorPopup(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(title);
        alert.setContentText(message);
        Timeline timeline = new Timeline(new KeyFrame(
            javafx.util.Duration.seconds(3),
            ae -> alert.close()));
        timeline.play();
        alert.showAndWait();
    }

    public static void showSuccessPopup(String title, String message, Boolean flag) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(title);
        alert.setContentText(message);
        if(!flag){
        Timeline timeline = new Timeline(new KeyFrame(
            javafx.util.Duration.seconds(3),
            ae -> alert.close()));
        timeline.play();
        }
        alert.showAndWait();
    }

    public static void showWarningPopup(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(title);
        alert.setContentText(message);
        Timeline timeline = new Timeline(new KeyFrame(
            javafx.util.Duration.seconds(3),
            ae -> alert.close()));
        timeline.play();
        alert.showAndWait();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SetOfRules) {
            Platform.runLater(() -> {
                ruleList.setAll(((SetOfRules) o).getRules());
            });
        }
    }

}
