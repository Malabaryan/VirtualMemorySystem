/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Kenneth
 */
public class Page {
    private Frame allocatedAt;
    private Process process;
    
    private boolean dirty;
    
    private final int size;
    private final int segmentAddress;
    
    public Page(Process process, int size, int segmentAddress){
        this.process = process;
        this.size = size;
        this.segmentAddress = segmentAddress;
        this.dirty = false;
    }
    
    public void setAllocated(Frame frame){
        this.allocatedAt = frame;
    }
    
    public void setDeallocated(){
        this.allocatedAt = null;
    }
    
    public boolean isAllocated(){
        return this.allocatedAt != null;
    }
    
    public Frame getAllocatedAtFrame(){
        return this.allocatedAt;
    }
    
    public Process getProcess(){
        return this.process;
    }
    
    public void setDirty(boolean dirty){
        if(dirty){
            this.dirty = true;
        }
    }
    
    public void cleanPage(){
        this.dirty = false;
    }
    
    public String toString(){
        String string = "Page of Process: " + process.getId() + "\n";
        string += "Segment Address: " + segmentAddress + "\n";
        return string;
    }
    
    public String toHTML(){
        String string = "Page of Process: " + process.getId() + "<br/>";
        string += "Segment Address: " + segmentAddress + "<br/>";
        return string;
    }
}
