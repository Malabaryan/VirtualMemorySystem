/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.MainController;
import Controller.ParameterState;
import Controller.ParametersController;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class Process {
    public String id;
    public int priority;
    public int memoryRequirements;

    public Process(String id, int priority, int memoryRequirements) {
        this.id = id;
        this.priority = priority;
        this.memoryRequirements = memoryRequirements;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getMemoryRequirements() {
        return memoryRequirements;
    }
    
    @Override
    public String toString(){
        return  "ID: " + this.getId() +
                " Priority: " + this.getPriority() +
                " Memory Requirements: " + this.getMemoryRequirements();
    }
    
}