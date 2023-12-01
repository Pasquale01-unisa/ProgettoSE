/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectse.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import projectse.controller.MyProjectSEViewController;
import projectse.model.rule.SingleRule;

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
            
            if(r.isSleeping() && LocalDateTime.now().isAfter(r.getRepetition())){
                r.setIsShow(false);
                r.setSleeping(false);
                r.setState("Active");
                controller.update();
            }
            
            if(!r.isSleeping() && !r.isIsShow() && r.getTriggerObject().checkTrigger() && r.getState().equals("Active")){
                r.setIsShow(true);
                if(r.isRepeat()){
                    r.setSleeping(true);
                }
                r.setState("Deactivated");   

                Platform.runLater(() -> {r.getActionObject().executeAction();});
                Platform.runLater(() -> {controller.update();});
            }
        }
    }
}