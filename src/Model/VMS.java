/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Logging.Log;
import Logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author Kenneth
 */
public class VMS {
    public enum AccessType{
        READ, WRITE
    }
    
    private final Parameters parameters;
    
    private final ArrayList<Process> processes;
    
    private final ArrayList<Frame> memoryFrames;
    
    private int placementPointer = 0;
    
    public VMS(Parameters parameters){
        this.parameters = parameters;
        processes = new ArrayList<>();
        memoryFrames = new ArrayList<>();
        
        for (
            int frameAddress = 0; 
            frameAddress < parameters.getPhysicalMemory(); 
            frameAddress += parameters.getPageSize()
        ){
            memoryFrames.add(new Frame(parameters.getPageSize(), frameAddress));
        }
    }
        
    public void addProcess(String processId, int memoryRequirement, int priority){
        for (Process process : processes){
            if (process.getId().equals(processId)){
                Logger.log(Log.Type.ERROR, "Process ID \"" 
                        + processId + "\" already in use.");
                return;
            }
        }
        Process process = 
                new Process(this, processId, memoryRequirement, priority);
        processes.add(process);
        Logger.log(Log.Type.INFO, "Process \"" + processId + "\" added.");
        process.initializePages(parameters.getPageSize());
        if (parameters.getFethPolicy() == Parameters.FethPolicy.PREPAGING){
            allocatePageSet(process.getPages());
        }
    }
    
    public void accessMemory(Process process, int address, boolean writes){
        int pageIndex = (address / (parameters.getPageSize() * 1024));
        Page lookingPage = process.getPages().get(pageIndex);
        if (!lookingPage.isAllocated()){
            Logger.log(Log.Type.INFO, "Accesing unallocated page. Page fault.");
            allocatePage(lookingPage);
        }
        if (lookingPage.isAllocated()){
            int inPageOffset = address % (parameters.getPageSize() * 1024);
            int pagePhysicalByteAddress = 
                    lookingPage.getAllocatedAtFrame().getFrameAddress() * 1024;
            Logger.log(Log.Type.INFO, 
                    process.getId() + " accessed address [" + address + 
                            "] located at [" + (pagePhysicalByteAddress 
                                    + inPageOffset) + "] in physical memory.");
            lookingPage.setDirty(writes);
        } else {
            Logger.log(Log.Type.ERROR, "Cannot allocate page.");
        }
    }
    
    public void accessMemory(String processId, int address, boolean writes){
        for (Process process : processes){
            if (process.getId().equals(processId)){
                process.accessMemory(address, writes);
                return;
            }
        }
        Logger.log(Log.Type.ERROR, "Process \"" + processId + "\" not found.");
    }
    
    public void allocatePageSet(ArrayList<Page> pages){
        for (Page page : pages){
            allocatePage(page);
        }
    }
    
    public void allocatePage(Page allocatingPage){
        //PLACEMENT
        if(parameters.getPlacementPolicy() == Parameters.PlacementPolicy.FIRST){
            placementPointer = 0;
        }
        for(int sIndex = placementPointer; 
            (placementPointer + 1) % memoryFrames.size() != sIndex;
            placementPointer++){
            if (!memoryFrames.get(placementPointer).isInUse()){
                memoryFrames.get(placementPointer).allocatePage(allocatingPage);
                return;
            }
        }
        //CANNOT PLACE -> REPLACE
        //TODO ALLOCATE PAGE
    }
    
    public ArrayList<Frame> getMemoryFrames(){
        return this.memoryFrames;
    }
    
    public Process getProcess(String processId){
        for (Process process : this.processes){
            if (process.getId().equals(processId)){
                return process;
            } 
        }
        return null;
    }
    
    public ArrayList<Process> getProcesses(){
        return this.processes;
    }
}
