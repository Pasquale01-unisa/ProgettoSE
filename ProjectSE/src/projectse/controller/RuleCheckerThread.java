package projectse.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import projectse.model.rule.SingleRule;

/**
 *
 * @author group07
 */

public class RuleCheckerThread implements Runnable {
    private List<SingleRule> ruleList;
    private final AtomicBoolean running = new AtomicBoolean(false); //check the execution state of the thread  
    private RuleUpdateCallback callback; 

    public RuleCheckerThread(List<SingleRule> ruleList, RuleUpdateCallback callback) {
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
            //If the rule is deactive and the actual time is after the time i've setted for the reptition i have to activate the rule 
            if (r.isSleeping() && LocalDateTime.now().isAfter(r.getRepetition())) {
                r.setIsShow(false);
                r.setSleeping(false);
                r.setRepeat(false);
                r.setState("Active");
                
                // Notify the controller to update the UI
                callback.updateUI();
            }

            if (!r.isSleeping() && !r.isShow() && r.getTriggerObject().checkTrigger() && r.getState().equals("Active")) {
                r.setIsShow(true);
                if (r.isRepeat()) {
                    r.setSleeping(true);
                }
                r.setState("Deactivated");

                // Call to the callback to execute the action 
                callback.executeAction(r);

                // Notify the controller to update the UI after the execution of the action  
                callback.updateUI();
            }
        }
        callback.updateUI();
    }
}

