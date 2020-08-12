/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Logging.Log;
import Logging.Logger;

/**
 *
 * @author Kenneth
 */
public class Frame {
    private final int address;
    private final int size;
    
    private Page allocatedPage;
    
    public Frame(int size, int address){
        this.size = size;
        this.address = address;
    }
    
    public void allocatePage(Page page){
        deallocatePage();
        Logger.log(Log.Type.INFO, "Page alllocated.",
                page.getProcess().getId());
        allocatedPage = page;
        page.setAllocated(this);
    }
    
    public void deallocatePage(){
        if (allocatedPage != null){
            Logger.log(Log.Type.INFO, "Page deallocated.",
                allocatedPage.getProcess().getId());
            allocatedPage.setDeallocated();
            allocatedPage = null;
        }
    }
    
    public Page getAllocatedPage(){
        return this.allocatedPage;
    }
    
    public boolean isInUse(){
        return this.allocatedPage != null;
    }
    
    public int getFrameAddress(){
        return this.address;
    }
    
    public String toString(){
        String string = "Frame Address: " + (address * 1024) + "\n";
        if (allocatedPage == null){
            string += "Unused";
        } else {
            string += "Allocated Page:\n";
            string += allocatedPage.toString();
        }
        return string;
    }
    
    public String toHTML(){
        String string = "Frame Address: " + (address * 1024) + "<br>";
        if (allocatedPage == null){
            string += "Unused<br>";
        } else {
            string += "Allocated Page:<br>";
            string += allocatedPage.toHTML();
        }
        return string;
    }
}
