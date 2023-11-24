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
    private ObservableList<Rule> listaRegole;
    private ProvaController c;

    // Il costruttore accetta ora un PromemoriaCallback
    public RuleCheckerService(ObservableList<Rule> listaRegole, ProvaController c) {
        this.listaRegole = listaRegole;
        this.c = c;
        setPeriod(Duration.seconds(10)); 
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                controllaRegole();
                return null;
            }
        };
    }

    private void controllaRegole() {
     
    }

    private boolean deveEssereAttivata() {
      
    }

    
}