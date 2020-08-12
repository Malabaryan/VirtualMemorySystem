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
    
    private long allocatedSince;
    private long lastUsed;
    private int useCount;
    
    public Page(Process process, int size, int segmentAddress){
        this.process = process;
        this.size = size;
        this.segmentAddress = segmentAddress;
        this.dirty = false;
        this.allocatedSince = 0;
        this.lastUsed = 0;
        this.useCount = 0;
    }
    
    public void setAllocated(Frame frame){
        this.allocatedAt = frame;
        allocatedSince = System.nanoTime();
    }
    
    public void setDeallocated(){
        this.allocatedAt = null;
        this.allocatedSince = 0;
        this.lastUsed = 0;
        this.useCount = 0;
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
    
    public long getAllocatedSince(){
        return this.allocatedSince;
    }
    
    public long getLastUsed(){
        return this.lastUsed;
    }
    
    public int getUseCount(){
        return this.useCount;
    }
    
    public boolean isDirty(){
        return this.dirty;
    }
    
    public void accessPage(boolean writes){
        if(writes){
            this.dirty = true;
        }
        this.lastUsed = System.nanoTime();
        this.useCount++;
    }
    
    public void cleanPage(){
        this.dirty = false;
    }
    
    public String toString(){
        String string = "Page of Process: " + process.getId() + "\n";
        string += "Segment Address: " + (segmentAddress * 1024) + "\n";
        if(isDirty())
            string += "Dirty\n";
        return string;
    }
    
    public String toHTML(){
        String string = "Page of Process: " + process.getId() + "<br>";
        string += "Segment Address: " + (segmentAddress * 1024) + "<br>";
        if(isDirty())
            string += "Dirty<br>";
        return string;
    }
}
