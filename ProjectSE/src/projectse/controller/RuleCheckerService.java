/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class RuleCheckerService extends ScheduledService<Void> {
    private ObservableList<SingleRule> ruleList;
    private MyProjectSEViewController controller;

    // Il costruttore accetta ora un PromemoriaCallback
    public RuleCheckerService(ObservableList<SingleRule> ruleList, MyProjectSEViewController controller) {
        this.ruleList = ruleList;
        this.controller = controller;
        setPeriod(Duration.seconds(10)); 
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                checkRule();
                return null;
            }
        };
    }

    private void checkRule() {
        for(SingleRule r : ruleList){
            if(!r.isShow() && r.getTrigger().checkTrigger()){
                System.out.println("Verificato");
            }
        }
    }

    
}