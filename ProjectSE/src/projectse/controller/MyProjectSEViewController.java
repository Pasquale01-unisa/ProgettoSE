/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectse.controller;

import javafx.stage.WindowEvent;
import java.io.File;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import projectse.model.action.*;
import projectse.model.rule.*;
import projectse.model.trigger.*;

/**
 * FXML Controller class
 *
 * @author group07
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
    @FXML
    private HBox triggerTimeFields;
    @FXML
    private MenuButton menuButtonTriggerDate;
    @FXML
    private MenuItem btnDayOfWeek;
    @FXML
    private MenuItem btnDayOfMonth;
    @FXML
    private MenuItem btnGenericDate;
    @FXML
    private MenuItem btnDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private MenuButton btnWeekDays;
    @FXML
    private MenuItem btnMonday;
    @FXML
    private MenuItem btnTuesday;
    @FXML
    private MenuItem btnWednesday;
    @FXML
    private MenuItem btnThursday;
    @FXML
    private MenuItem btnFriday;
    @FXML
    private MenuItem btnSaturday;
    @FXML
    private MenuItem btnSunday;
    @FXML
    private Spinner spinnerDayOfMonth;
    @FXML
    private HBox boxDate;
    @FXML
    private VBox boxFile;
    @FXML
    private VBox boxCommit;

    private ObservableList<SingleRule> ruleList = FXCollections.observableArrayList();
    private SetOfRules rules = new SetOfRules();
    private RuleCheckerThread ruleCheckerThread;
    private File selectedFile = null;
    private Duration sleepingTime;
    private boolean repeat = false;
    private File selectedDirectory = null;
    private DayOfWeek dayOfWeek;
    private Integer dayOfMonth;
    private LocalDate specificDate;

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

        menuButtonTriggerDate.setText("Choose a DateType");
        btnCommit.disableProperty().bind(
            textRuleName.textProperty().isEmpty()
            .or(textAction.textProperty().isEmpty())
            .or(btnTrigger.textProperty().isEqualTo("Choose a Trigger"))
            .or(btnAction.textProperty().isEqualTo("Choose an Action"))
            .or(btnTrigger.textProperty().isEqualTo("Date")
                .and(menuButtonTriggerDate.textProperty().isEqualTo("Choose a DateType")
                .or(
                    menuButtonTriggerDate.textProperty().isEqualTo("Day Of Month")
                    .and(spinnerDayOfMonth.valueProperty().isNull())
                ).or(
                    menuButtonTriggerDate.textProperty().isEqualTo("Day Of Week")
                    .and(btnWeekDays.textProperty().isEqualTo("WeekDays"))
                ).or(
                    menuButtonTriggerDate.textProperty().isEqualTo("Generic Date")
                    .and(datePicker.valueProperty().isNull())
                )))
            .or(btnTrigger.textProperty().isEqualTo("File Check")
                .and(textTriggerFileCheck.textProperty().isEmpty()
                    .or(textTriggerDirectoryCheck.textProperty().isEmpty())))
        );
        
        btnFile.setManaged(false);
        
        //TRIGGERTIME---
        triggerTimeFields.setManaged(false);
        numberTriggerH.setManaged(false);
        numberTriggerM.setManaged(false);
        separatorSpinner.setManaged(false);

        boxFile.setManaged(false);
        textTriggerFileCheck.setManaged(false);
        textTriggerDirectoryCheck.setManaged(false);
        btnChooseDirectoryFileChecker.setManaged(false);

        //TRIGGERDATE---
        menuButtonTriggerDate.setManaged(false);
        datePicker.setManaged(false);
        boxDate.setManaged(false);
        btnWeekDays.setManaged(false);
        spinnerDayOfMonth.setManaged(false);

        //ACTION
        textActionStringToFile.setManaged(false); 
        textAction.setManaged(false);
        textAction.setDisable(true);
        btnAddTrigger.setDisable(true);
        btnAddAction.setDisable(true);

        argLabel.setManaged(false);
        argLabel.setVisible(false);
        btnChooseDirectory.setManaged(false);
        
        SpinnerValueFactory<Integer> dayOfMonthFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 0);
        spinnerDayOfMonth.setValueFactory(dayOfMonthFactory);
        setDatePickerRange(datePicker);

        // Set the Spinner for the hours and minutes
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        numberTriggerH.setValueFactory(hoursFactory);
        numberTriggerM.setValueFactory(minutesFactory);

        // Costimize the display of values in spinners
        setupSpinnerWithCustomTextFormatter(numberTriggerH, true); // Hours
        setupSpinnerWithCustomTextFormatter(numberTriggerM, false); // Minutes
        setupSpinnerDateWithCustomTextFormatter(spinnerDayOfMonth); //DayOfMonth
        this.rules.addObserver(this); // Set the controller as an observer

        // Configurare la colonna della checkbox per utilizzare una proprietà booleana della tua classe Rule
        // Assumendo che tu abbia un campo booleano (ad esempio, isSelected) in Rule
        columnCheck.setCellFactory(tc -> new CheckBoxTableCell<SingleRule, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = new CheckBox();
                    SingleRule rule = getTableView().getItems().get(getIndex()); //Obtain the singleRule object associated to the current line of the table
                    checkBox.setSelected(rule.getIsSelectedValue());
                    checkBox.setOnAction(e -> {
                        rule.setIsSelectedValue(checkBox.isSelected()); //when the checkbox is selected the checkBox state change
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

        ruleCheckerThread = new RuleCheckerThread(ruleList, this);
        Thread thread = new Thread(ruleCheckerThread);
        thread.start();

        tableView.setItems(ruleList); // Connect the ObservableList to the table
        Platform.runLater(() -> {
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setOnCloseRequest(this::handleWindowClose);
        });

        FileManagement.loadRulesFromFile(rules); // I take what i have on the file and put it into setOfRules
    }

    @FXML
    private void onCheckBox(ActionEvent event) {
        boolean isSelected = checkTotal.isSelected();
        for (SingleRule rule : ruleList) {
            rule.setIsSelectedValue(isSelected);
        }
        tableView.refresh(); // Update the TableView to show the changes
        updateButtonState(); // Update the buttons states
    }

    @FXML
    private void onBtnTime(ActionEvent event) {
        //TRIGGERTIME
        triggerTimeFields.setManaged(true);
        triggerTimeFields.setVisible(true);
        numberTriggerH.setManaged(true);
        numberTriggerM.setManaged(true);
        separatorSpinner.setManaged(true);
        numberTriggerH.setVisible(true);
        numberTriggerM.setVisible(true);
        separatorSpinner.setVisible(true);

        //TRIGGERFILE
        boxFile.setManaged(false);
        boxFile.setVisible(false);
        textTriggerFileCheck.setManaged(false);
        textTriggerFileCheck.setVisible(false);
        textTriggerDirectoryCheck.setManaged(false);
        textTriggerDirectoryCheck.setVisible(false);
        btnChooseDirectoryFileChecker.setManaged(false);
        btnChooseDirectoryFileChecker.setVisible(false);

        //TRIGGERDATE
        menuButtonTriggerDate.setManaged(false);
        menuButtonTriggerDate.setVisible(false);
            //GENERIC DATE
        datePicker.setManaged(false);
        datePicker.setVisible(false);
            //MONTH & WEEK
        boxDate.setManaged(false);
        boxDate.setVisible(false);
            //WEEK
        btnWeekDays.setManaged(false);
        btnWeekDays.setVisible(false);
            //MONTH
        spinnerDayOfMonth.setManaged(false);
        spinnerDayOfMonth.setVisible(false);
        
        btnTrigger.setText("Time"); // change the MenuButton text
        btnWeekDays.setText("WeekDays");

    }

    @FXML
    private void onBtnAlarm(ActionEvent event) {
        argLabel.setManaged(false);
        argLabel.setVisible(false);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setManaged(false);
        btnChooseDirectory.setVisible(false);
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        argLabel.setVisible(false);
        textAction.clear();
        btnAction.setText("Alarm");
    }

    @FXML
    private void onBtnMemo(ActionEvent event) {
        argLabel.setManaged(false);
        argLabel.setVisible(false);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textAction.setDisable(false);
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        btnFile.setManaged(false);
        btnFile.setVisible(false);
        btnChooseDirectory.setVisible(false);
        argLabel.setVisible(false);
        textAction.clear();
        textAction.setPromptText("Insert memo"); // Set a placeholder or suggestion
        btnAction.setText("Memo");
    }
    @FXML
    private void onBtnAppentTextToFile(ActionEvent event) {
        argLabel.setManaged(false);
        argLabel.setVisible(false);
        textAction.setDisable(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        textActionStringToFile.setDisable(false);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setManaged(false);
        btnChooseDirectory.setVisible(false);
        argLabel.setVisible(false);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textAction.clear();
        textActionStringToFile.setPromptText("Insert text");
        btnAction.setText("Append text to file");
    }

    @FXML
    private void onBtnFileCheck(ActionEvent event){
        //TRIGGERTIME-----
        triggerTimeFields.setManaged(false);
        triggerTimeFields.setVisible(false);
        numberTriggerH.setManaged(false);
        numberTriggerH.setVisible(false);
        numberTriggerM.setManaged(false);
        numberTriggerM.setVisible(false);
        separatorSpinner.setManaged(false);
        separatorSpinner.setVisible(false);

        //ACTION
        textAction.setManaged(false);
        textAction.setVisible(false);
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);

        //TRIGGERDATE----
        menuButtonTriggerDate.setManaged(false);
        menuButtonTriggerDate.setVisible(false);
            //GENERIC DATE
        datePicker.setManaged(false);
        datePicker.setVisible(false);
            //MONTH & WEEK
        boxDate.setManaged(false);
        boxDate.setVisible(false);
            //WEEK
        btnWeekDays.setManaged(false);
        btnWeekDays.setVisible(false);
            //MONTH
        spinnerDayOfMonth.setManaged(false);
        spinnerDayOfMonth.setVisible(false);
        
        //TRIGGERFILE
        boxFile.setManaged(true);
        boxFile.setVisible(true);
        textTriggerFileCheck.setManaged(true);
        textTriggerFileCheck.setVisible(true);
        textTriggerDirectoryCheck.setManaged(true);
        textTriggerDirectoryCheck.setVisible(true);
        textTriggerDirectoryCheck.setDisable(true);
        btnChooseDirectoryFileChecker.setManaged(true);
        btnChooseDirectoryFileChecker.setVisible(true);
        
        btnTrigger.setText("File Check");
    }

    @FXML
    private void onBtnDeleteFile(ActionEvent event) {
        btnChooseDirectory.setManaged(false);
        btnChooseDirectory.setVisible(false);
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        argLabel.setVisible(false);
        textAction.clear();
        btnAction.setText("Delete file");
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
        rules.addRule(newRule);
        this.reset();
    }

    @FXML
    private void OnBtnDate(ActionEvent event) {
        btnTrigger.setText("Date");
        //TRIGGERTIME
        triggerTimeFields.setManaged(false);
        triggerTimeFields.setVisible(false);
        numberTriggerH.setManaged(false);
        numberTriggerH.setVisible(false);
        numberTriggerM.setManaged(false);
        numberTriggerM.setVisible(false);
        separatorSpinner.setManaged(false);
        separatorSpinner.setVisible(false);

        //TRIGGERDATE----
        menuButtonTriggerDate.setManaged(true);
        menuButtonTriggerDate.setVisible(true);
        datePicker.setManaged(false);
        datePicker.setVisible(false);
            //MONTH & WEEK---
        boxDate.setManaged(false);
        boxDate.setVisible(false);
            //WEEK
        btnWeekDays.setManaged(false);
        btnWeekDays.setVisible(false);
            //MONTH
        spinnerDayOfMonth.setManaged(false);
        spinnerDayOfMonth.setVisible(false);
        //TRIGGERFILE
        boxFile.setManaged(false);
        boxFile.setVisible(false);
        textTriggerFileCheck.setManaged(false);
        textTriggerFileCheck.setVisible(false);
        textTriggerDirectoryCheck.setManaged(false);
        textTriggerDirectoryCheck.setVisible(false);
        textTriggerDirectoryCheck.setDisable(false);
        btnChooseDirectoryFileChecker.setVisible(false);
        btnChooseDirectoryFileChecker.setManaged(false);
    }

    @FXML
    private void OnBtnWeekDays(ActionEvent event) {
        System.out.println(btnWeekDays.getText());
    }

    @FXML
    private void OnBtnDayOfWeek(ActionEvent event) {
        menuButtonTriggerDate.setText("Day Of Week");
        boxDate.setManaged(true);
        boxDate.setVisible(true);
        spinnerDayOfMonth.setManaged(false);
        spinnerDayOfMonth.setVisible(false);
        btnWeekDays.setManaged(true);
        btnWeekDays.setVisible(true);
        datePicker.setManaged(false);
        datePicker.setVisible(false);
    }

    @FXML
    private void OnBtnDayOfMonth(ActionEvent event) {
        menuButtonTriggerDate.setText("Day Of Month");
        boxDate.setManaged(true);
        boxDate.setVisible(true);
        btnWeekDays.setManaged(false);
        btnWeekDays.setVisible(false);
        spinnerDayOfMonth.setManaged(true);
        spinnerDayOfMonth.setVisible(true);
        datePicker.setManaged(false);
        datePicker.setVisible(false);
    }

    @FXML
    private void OnBtnGenericDate(ActionEvent event) {
        menuButtonTriggerDate.setText("Generic Date");
        boxDate.setManaged(false);
        boxDate.setVisible(false);
        btnWeekDays.setManaged(false);
        btnWeekDays.setVisible(false);
        spinnerDayOfMonth.setManaged(false);
        spinnerDayOfMonth.setVisible(false);
        datePicker.setManaged(true);
        datePicker.setVisible(true);
    }

    @FXML
    private void OnBtnMonday(ActionEvent event){
        btnWeekDays.setText("Monday");
    }

    @FXML
    private void OnBtnTuesday(ActionEvent event){
        btnWeekDays.setText("Tuesday");
    }

    @FXML
    private void OnBtnWednesday(ActionEvent event){
        btnWeekDays.setText("Wednesday");
    }

    @FXML
    private void OnBtnThursday(ActionEvent event){
        btnWeekDays.setText("Thursday");
    }

    @FXML
    private void OnBtnFriday(ActionEvent event){
        btnWeekDays.setText("Friday");
    }

    @FXML
    private void OnBtnSaturday(ActionEvent event){
         btnWeekDays.setText("Saturday");
    }

    @FXML
    private void OnBtnSunday(ActionEvent event){
        btnWeekDays.setText("Sunday");
    }

    @FXML
    private void onBtnDelete(ActionEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm elimination");
        confirmAlert.setHeaderText("Are you sure you want to delete the selected rules?");

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
                rule.setIsShow(false); // Set isShow to false, so it checks the rule again
                // Change the state from "Active" to "Inactive" e vice versa
                rule.setState(rule.getState().equals("Active") ? "Deactivated" : "Active");
        });
        tableView.refresh();
        updateButtonState();
    }

    @FXML
    private void onBtnFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        // Check if the btnAction text is "Alarm"
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

    @FXML
    private void OnBtnRepetition(ActionEvent event) {
        // Create the dialog
        Dialog<Object> dialog = new Dialog<>();
        dialog.setTitle("Set Sleeping Time");

        // Set the button type
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Creates the two text field
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

        // Enable/disable the OK button depending on whether the textFields are empty or not 
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        // Add a listener to check the textfield
        textField1.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the first textField at the beginning 
        Platform.runLater(textField1::requestFocus);

        // Convert the results at the click of the OK button to a string pair
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                sleepingTime = Duration.ofDays(Integer.parseInt(textField1.getText())).plusHours(Integer.parseInt(textField2.getText())).plusMinutes(Integer.parseInt(textField3.getText()));
            }
            return null;
        });

        // Show the dialog and wait for the result
        Optional<Object> result = dialog.showAndWait();
        repeat = true;
    }

    @FXML
    private void onBtnCopyFile(ActionEvent event){
        //Function to show the fields when we choose the copy file action
        textAction.setDisable(true);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textActionStringToFile.setDisable(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        argLabel.setVisible(false);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setManaged(true);
        btnChooseDirectory.setVisible(true);
        argLabel.setManaged(false);
        argLabel.setVisible(false);

        textAction.clear();
        textActionStringToFile.clear();

        btnAction.setText("Copy file");
    }

    @FXML
    private void onBtnMoveFile(ActionEvent event){
        //Function to show the fields when we choose the copy file action
        textAction.setDisable(true);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textActionStringToFile.setDisable(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        argLabel.setVisible(false);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setManaged(true);
        btnChooseDirectory.setVisible(true);
        argLabel.setManaged(false);
        argLabel.setVisible(false);

        textAction.clear();
        textActionStringToFile.clear();

        btnAction.setText("Move file");
    }

    @FXML
    private void onBtnChooseDirectory(ActionEvent event){
        //function to choose a directory to move or copy a file
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a Directory");

        textAction.setPromptText("");
        textActionStringToFile.setPromptText("");

        selectedDirectory = directoryChooser.showDialog(((Node)event.getSource()).getScene().getWindow());
        if (selectedDirectory != null && btnTrigger.getText().equals("File Check")) {
            textTriggerDirectoryCheck.setText(selectedDirectory.getAbsolutePath());
        }else{
            textActionStringToFile.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void onBtnOpenExternalProgram(ActionEvent event){
        argLabel.setManaged(true);
        argLabel.setVisible(true);
        boxCommit.setManaged(true);
        boxCommit.setVisible(true);
        textActionStringToFile.setManaged(true);
        textActionStringToFile.setVisible(true);
        textActionStringToFile.setDisable(false);
        textAction.setManaged(true);
        textAction.setVisible(true);
        textAction.setDisable(true);
        btnFile.setManaged(true);
        btnFile.setVisible(true);
        btnChooseDirectory.setVisible(false);
        argLabel.setVisible(true);
        textAction.clear();
        textActionStringToFile.setPromptText("Insert arguments*"); 
        btnAction.setText("Open External Program"); 
    }
    
    @FXML
    private void updateDeleteButtonState() {
    }
    
    @FXML
    private void onTextFieldName(ActionEvent event) {
    }

    @FXML
    private void onBtnTrigger(ActionEvent event) {
    }
    
    @FXML
    private void onBtnAddTrigger(ActionEvent event) {
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
    
    @Override
    public void updateUI() {
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

    //Method to link ObservableList of SingleRule to setOfRules, every time there is a change the update method is called 
    @Override
    public void update(Observable o, Object arg) { //arg is setOfRules when i call notifyObserver into the SetOfRules class
        if (o instanceof SetOfRules) {
            Platform.runLater(() -> {
                ruleList.setAll(((SetOfRules) o).getRules());
            });
        }
    }
    
    private void setDatePickerRange(DatePicker datePicker) {
        // Set the current date
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable the dates before the current date
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #eeeeee;");
                        }
                    }
                };
            }
        });
    }
    
    private void reset (){
        textRuleName.clear();
        
        //TRIGGERTIME-----
        triggerTimeFields.setManaged(false);
        triggerTimeFields.setVisible(false);
        numberTriggerH.setManaged(false);
        numberTriggerH.setVisible(false);
        numberTriggerM.setManaged(false);
        numberTriggerM.setVisible(false);
        numberTriggerH.getValueFactory().setValue(00);
        numberTriggerM.getValueFactory().setValue(00);
        separatorSpinner.setManaged(false);
        separatorSpinner.setVisible(false);

        //TRIGGERDATE----
        menuButtonTriggerDate.setManaged(false);
        menuButtonTriggerDate.setVisible(false);
        menuButtonTriggerDate.setText("Choose a DateType");
            //GENERIC DATE
        datePicker.setManaged(false);
        datePicker.getEditor().clear();
        datePicker.setVisible(false);
            //MONTH & WEEK
        boxDate.setManaged(false);
        boxDate.setVisible(false);
            //WEEK
        btnWeekDays.setManaged(false);
        btnWeekDays.setVisible(false);
        btnWeekDays.setText("WeekDays");
            //MONTH
        spinnerDayOfMonth.setManaged(false);
        spinnerDayOfMonth.setVisible(false);
        spinnerDayOfMonth.getValueFactory().setValue(01);
        
        //TRIGGERFILE
        boxFile.setManaged(false);
        boxFile.setVisible(false);
        textTriggerFileCheck.setManaged(false);
        textTriggerFileCheck.setVisible(false);
        textTriggerFileCheck.clear();
        textTriggerDirectoryCheck.setManaged(false);
        textTriggerDirectoryCheck.setVisible(false);
        textTriggerDirectoryCheck.setDisable(true);
        textTriggerDirectoryCheck.clear();
        btnChooseDirectoryFileChecker.setManaged(false);
        btnChooseDirectoryFileChecker.setVisible(false);
        
        //ACTION
        textAction.setManaged(false);
        textAction.setVisible(false);
        textActionStringToFile.setManaged(false);
        textActionStringToFile.setVisible(false);
        textAction.clear();
        textActionStringToFile.clear();
        btnFile.setManaged(false);
        btnFile.setVisible(false);
        argLabel.setManaged(false);
        argLabel.setVisible(false);
        
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
    
    //Spinner got day of months
    private void setupSpinnerDateWithCustomTextFormatter(Spinner<Integer> spinnerDayOfMonths) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), spinnerDayOfMonths.getValue(), change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Allow the empty field
            }
            try {
                int newValue = Integer.parseInt(newText);
                if (newValue >= 1 && newValue <= 31) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // If it's not a valid number it doesn't do anything
            }
            return null; // Ignore invalid changes
        });

        spinnerDayOfMonths.getEditor().setTextFormatter(formatter);
        spinnerDayOfMonths.setEditable(true); // Makes the spinner editable

        formatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                String text = String.format("%02d", newValue); // Format as a two-digit string
                if (!text.equals(spinnerDayOfMonths.getEditor().getText())) {
                                    spinnerDayOfMonths.getEditor().setText(text);
                }
                spinnerDayOfMonths.getValueFactory().setValue(newValue);
            }
        });

        spinnerDayOfMonths.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When the spinner loses focus 
                Integer currentValue = spinnerDayOfMonths.getValue();
                if (currentValue != null) {
                    String text = String.format("%02d", currentValue);
                    spinnerDayOfMonths.getEditor().setText(text);
                }
            }
        });
    }

    private TriggerFactory createTriggerFactory(String userChoice){
        switch (userChoice) {
            case "Time":
                return new TriggerTimeFactory(numberTriggerH.getValue().toString(), numberTriggerM.getValue().toString());
            case "File Check":
                return new TriggerExistingFileFactory(textTriggerFileCheck.getText(), textTriggerDirectoryCheck.getText());
            case "Date":
                return new TriggerDateFactory(btnWeekDays.getText().toUpperCase(),Integer.parseInt(spinnerDayOfMonth.getValue().toString()), datePicker.getValue(), menuButtonTriggerDate.getText());
            default:
                throw new IllegalArgumentException("Trigger type not supported: " + userChoice);
        }
    }
    
    //Spinner for the hour
    private void setupSpinnerWithCustomTextFormatter(Spinner<Integer> spinner, boolean isHour) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), spinner.getValue(), change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Allows the empty field 
            }
            try {
                int newValue = Integer.parseInt(newText);
                if ((isHour && newValue >= 0 && newValue <= 23) || (!isHour && newValue >= 0 && newValue <= 59)) {
                    return change;
                }
            } catch (NumberFormatException e) {
                
            }
            return null; 
        });

        spinner.getEditor().setTextFormatter(formatter);
        spinner.setEditable(true); 

        formatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                String text = String.format("%02d", newValue); 
                if (!text.equals(spinner.getEditor().getText())) {
                    spinner.getEditor().setText(text);
                }
                spinner.getValueFactory().setValue(newValue);
            }
        });
        spinner.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { 
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
            // None of the lines selected
            return;
        }

        // Creates an Alert to show the details
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dettagli Regola");
        alert.setHeaderText("Informazioni sulla Regola Selezionata");

        // Build the message with the line informations
        StringBuilder details = new StringBuilder();
        details.append("Nome: ").append(selectedRule.getName()).append("\n");
        details.append("Trigger: ").append(selectedRule.getTrigger()).append("\n");
        details.append("Azione: ").append(selectedRule.getAction()).append("\n");
        details.append("State: ").append((selectedRule.getState()));

        alert.setContentText(details.toString());

        // Shows the Alert
        alert.showAndWait();

        // Deselect the line into the TableView into the UI thread
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
    
    private void handleWindowClose(WindowEvent event) {
        if (ruleCheckerThread != null) {
            ruleCheckerThread.stop();
        }
    }

    public static void showErrorPopup(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
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
}
