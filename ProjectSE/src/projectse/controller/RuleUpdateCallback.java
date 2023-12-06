/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package projectse.controller;


import projectse.model.rule.SingleRule;

/**
 *
 * @author pasqualegambino
 */
public interface RuleUpdateCallback {
    void updateUI();
    void executeAction(SingleRule rule); 
}
