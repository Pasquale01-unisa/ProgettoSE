/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package projectse.controller;


import projectse.model.rule.SingleRule;

/**
 *
 * @author group07
 */
public interface RuleUpdateCallback {
    public void updateUI();
    public void executeAction(SingleRule rule); 
}
