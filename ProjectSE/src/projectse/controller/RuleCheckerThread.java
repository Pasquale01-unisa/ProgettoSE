

package projectse.controller;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.collections.ObservableList;
import projectse.model.rule.SingleRule;

public class RuleCheckerThread implements Runnable {
    private ObservableList<SingleRule> ruleList;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private RuleUpdateCallback callback;

    public RuleCheckerThread(ObservableList<SingleRule> ruleList, RuleUpdateCallback callback) {
        this.ruleList = ruleList;
        this.callback = callback;
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            checkRule();
            try {
                Thread.sleep(5000); // Sleep for 5 seconds
            } catch (InterruptedException e) {
                running.set(false);
            }
        }
    }

    public void stop() {
        running.set(false);
    }

    private void checkRule() {
        for (SingleRule r : ruleList) {
            if (r.isSleeping() && LocalDateTime.now().isAfter(r.getRepetition())) {
                r.setIsShow(false);
                r.setSleeping(false);
                r.setRepeat(false);
                r.setState("Active");

                // Notifica il controller di aggiornare l'UI
                callback.updateUI();
            }

            if (!r.isSleeping() && !r.isShow() && r.getTriggerObject().checkTrigger() && r.getState().equals("Active")) {
                r.setIsShow(true);
                if (r.isRepeat()) {
                    r.setSleeping(true);
                }
                r.setState("Deactivated");

                // Chiamata al callback per eseguire l'azione
                callback.executeAction(r);

                // Notifica il controller di aggiornare l'UI dopo l'esecuzione dell'azione
                callback.updateUI();
            }
        }
        // Un'ulteriore chiamata al callback può essere fatta qui se vuoi aggiornare l'UI dopo aver processato tutte le regole
        callback.updateUI();
    }
}

