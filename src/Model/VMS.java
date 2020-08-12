/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Logging.Log;
import Logging.Logger;
import Model.Parameters.*;
import UserInterface.VirtualMemorySystem;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Kenneth
 */
public class VMS {
    public enum AccessType{
        READ, WRITE
    }
    
    private class Cleaner extends Thread {        
        public Cleaner(){}
        
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(20000);
                    cleanPages();
                } catch (InterruptedException ex) {}
            }
        }
    }
    
    private final Parameters parameters;
    
    private final ArrayList<Process> processes;
    
    private final ArrayList<Frame> memoryFrames;
    
    private int placementPointer = 0;
    
    private int virtualMemoryAvailable;
    
    private final Cleaner cleaner;
    
    public VMS(Parameters parameters){
        this.parameters = parameters;
        processes = new ArrayList<>();
        memoryFrames = new ArrayList<>();
        cleaner = new Cleaner();
        
        virtualMemoryAvailable = parameters.getMaxVirtualMemory();
        
        for (
            int frameAddress = 0; 
            frameAddress < parameters.getPhysicalMemory(); 
            frameAddress += parameters.getPageSize()
        ){
            memoryFrames.add(new Frame(parameters.getPageSize(), frameAddress));
        }
        
        if(parameters.getCleaningPolicy() == CleaningPolicy.PRECLEANING){
            cleaner.start();
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
        if (memoryRequirement > virtualMemoryAvailable){
            Logger.log(Log.Type.ERROR, "Not enough virtual memory available.");
            return;
        }
        virtualMemoryAvailable -= memoryRequirement;
        Process process = 
                new Process(this, processId, memoryRequirement, priority);
        processes.add(process);
        Logger.log(Log.Type.INFO, "Process \"" + processId + "\" added.");
        process.initializePages(parameters.getPageSize());
        
    }
    
    private void cleanPages(){
        for(Frame frame : memoryFrames){
            if(frame.isInUse()){
                frame.getAllocatedPage().cleanPage();
            }
        }
    }
    
    public void accessMemory(Process process, int address, boolean writes){
        if(address > (process.getMemoryRequired() * 1024) - 1){
            Logger.log(Log.Type.ERROR, "Segmentation Fault.", process.getId());
            return;
        }
        int pageIndex = (address / (parameters.getPageSize() * 1024));
        Page lookingPage = process.getPages().get(pageIndex);
        if(!lookingPage.isAllocated()){
            Logger.log(Log.Type.INFO, "Accesing unallocated page, page fault.",
                    process.getId());
            process.increasePageFaultCount();
            allocatePage(lookingPage);
        }
        if(lookingPage.isAllocated()){
            int inPageOffset = address % (parameters.getPageSize() * 1024);
            int pagePhysicalByteAddress = 
                    lookingPage.getAllocatedAtFrame().getFrameAddress() * 1024;
            Logger.log(Log.Type.INFO, 
                    "Accessing address [" + address + 
                            "] located at [" + (pagePhysicalByteAddress 
                                    + inPageOffset) + "] in physical memory.",
                    process.getId());
            lookingPage.accessPage(writes);
            process.increaseAccessCount();
            
            if(parameters.getFethPolicy() == Parameters.FethPolicy.PREPAGING && !isMaxResidentSetReached(process)){
                allocateNext(lookingPage);
            }
            
            while(!isMinResidentSetReached(process)){
                if(!allocateNewPage(process)){
                    Logger.log(Log.Type.ERROR, "Cannot allocate page to satisfy minimun resident set.",
                    process.getId());
                    break;
                }
            }
            
        } else {
            Logger.log(Log.Type.ERROR, "Cannot allocate page.",
                    process.getId());
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
    
    public boolean allocateNewPage(Process process){
        for(Page page : process.getPages()){
            if(!page.isAllocated()){
                return allocatePage(page);
            }
        }
        return false;
    }
    
    public void allocateNext(Page page){
        ArrayList<Page> pages = page.getProcess().getPages();
        for(int i = pages.indexOf(page) + 1; i < pages.size(); i++){
            if (!pages.get(i).isAllocated()){
                allocatePage(pages.get(i));
                return;
            }
        }
    }
    
    public void allocatePageSet(ArrayList<Page> pages){
        for (Page page : pages){
            allocatePage(page);
        }
    }
    
    public void deallocateProcess(Process process){
        for(Frame frame : memoryFrames){
            if(frame.isInUse() && frame.getAllocatedPage().getProcess() == process){
                frame.deallocatePage();
            }
        }
    }
    
    public int getAllocatedProcessCount(){
        ArrayList<Process> processes = new ArrayList<>();
        for(Frame frame : memoryFrames){
            if(frame.isInUse() && 
                    !processes.contains(frame.getAllocatedPage().getProcess())){
                processes.add(frame.getAllocatedPage().getProcess());
            }
        }
        return processes.size();
    }
    
    public int getProcessAllocatedPagesCount(Process process){
        int pageCount = 0;
        for(Frame frame : memoryFrames){
            if(frame.isInUse()){
                if(frame.getAllocatedPage().getProcess() == process){
                    pageCount++;
                }
            }
        }
        return pageCount;
    }
    
    public boolean deallocateProcessOfLesserPriority(Process process){
        Process processDeallocate = null;
        for(Frame frame : memoryFrames){
            if(frame.isInUse()){
                if(frame.getAllocatedPage().getProcess().getPriority() >= process.getPriority()){
                    if(processDeallocate == null){
                        processDeallocate = frame.getAllocatedPage().getProcess();
                    } else if(processDeallocate.getPriority() <
                            frame.getAllocatedPage().getProcess().getPriority()) {
                        processDeallocate = frame.getAllocatedPage().getProcess();
                    }
                }
            }
        }
        if(processDeallocate != null){
            deallocateProcess(processDeallocate);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isAllocated(Process process){
        for(Frame frame : memoryFrames){
            if(frame.isInUse()){
                if(frame.getAllocatedPage().getProcess() == process){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isMinResidentSetReached(Process process){
        return getProcessAllocatedPagesCount(process) >=
                parameters.getMinResidentSet();
    }
    
    public boolean isMaxResidentSetReached(Process process){
        return getProcessAllocatedPagesCount(process) >= 
                parameters.getMaxResidentSet() && parameters.getMaxResidentSet() > 0;
    }
    
    public boolean allocatePage(Page allocatingPage){
        return allocatePage(allocatingPage, true);
    }
    
    public boolean allocatePage(Page allocatingPage, boolean allowLocalReclacement){
        if (allocatingPage.isAllocated()) {
            Logger.log(Log.Type.ERROR, "Page already allocated.");
            return true;
        }
        
        //CHECK MULTIPROGRAMING LEVEL DEGREE        
        if(parameters.getMultiProgramingDegree() <= getAllocatedProcessCount() &&
                !isAllocated(allocatingPage.getProcess())){
            if(!deallocateProcessOfLesserPriority(allocatingPage.getProcess())){
                Logger.log(Log.Type.ERROR, 
                        "Cannot allocate process due to multiprogramming level limit.", 
                        allocatingPage.getProcess().getId());
                return false;
            }
        }
        
        //CHECK MAX RESIDENT SET
        if(isMaxResidentSetReached(allocatingPage.getProcess())){
            Logger.log(Log.Type.INFO, 
                    "Max resident set reached, proceding to replacement.",
                    allocatingPage.getProcess().getId());
            
        } else {
            //PLACEMENT
            if(parameters.getPlacementPolicy() == PlacementPolicy.FIRST){
                placementPointer = 0;
            }
            int sIndex = placementPointer;
            int frameCount = memoryFrames.size();
            boolean success = false;
            while((sIndex + 1) % memoryFrames.size() != placementPointer) {
                Frame checkingFrame = memoryFrames.get(sIndex);
                if (!checkingFrame.isInUse()){
                    checkingFrame.allocatePage(allocatingPage);
                    success = true;
                    break;
                }
                sIndex++;
                sIndex = sIndex % frameCount;
            }
            placementPointer = sIndex;
            if(success){
                return true;
            }
        }
        
        //CANNOT PLACE -> REPLACE
        
        Frame frameToReplace = getFrameToReplace(allocatingPage, allowLocalReclacement);
        if (frameToReplace != null){
            frameToReplace.allocatePage(allocatingPage);
            return true;
        } else {
            Logger.log(Log.Type.ERROR, "Cannot find available frames.",
                    allocatingPage.getProcess().getId());
            return false;
        }
    }
    
    private Frame getFrameToReplace(Page page){
        return getFrameToReplace(page, true);
    }
    
    private Frame getFrameToReplace(Page page, boolean allowLocalReclacement){
        Frame resultFrame = null; 
        long SCDeadLine = System.nanoTime() - 9000000000L;
        for (Integer i : getAvailableFramesToReplace(page, allowLocalReclacement)){
            Frame frame = memoryFrames.get(i);
            if (resultFrame == null){
                resultFrame = frame;
                continue;
            }
            switch(parameters.getReplacementPolicy()){
                case FIFO:
                    if(frame.getAllocatedPage().getAllocatedSince() <
                            resultFrame.getAllocatedPage().getAllocatedSince()){
                        resultFrame = frame;
                    }
                    break;
                case LRU:
                    if(frame.getAllocatedPage().getLastUsed() <
                            resultFrame.getAllocatedPage().getLastUsed()){
                        resultFrame = frame;
                    }
                    break;
                case LFU:
                    if(frame.getAllocatedPage().getUseCount() >
                            resultFrame.getAllocatedPage().getUseCount()){
                        resultFrame = frame;
                    }
                    break;
                case MRU:
                    if(frame.getAllocatedPage().getLastUsed() >
                            resultFrame.getAllocatedPage().getLastUsed()){
                        resultFrame = frame;
                    }
                    break;
                case SECONDCHANCE:
                    if(frame.getAllocatedPage().getAllocatedSince() <
                            resultFrame.getAllocatedPage().getAllocatedSince() &&
                            frame.getAllocatedPage().getAllocatedSince() < SCDeadLine){
                        resultFrame = frame;
                    }
                    break;
                default:
                    return null;
            }
        }
        
        return resultFrame;
    }
    
    public Parameters getParameters(){
        return this.parameters;
    }
    
    private ArrayList<Integer> getAvailableFramesToReplace(Page page, boolean allowLocalReclacement){
        ArrayList<Integer> framesIds = new ArrayList<>();
        
        for (int i = 0; i < memoryFrames.size(); i++){
            if(memoryFrames.get(i).isInUse()){
                if(memoryFrames.get(i).getAllocatedPage().
                        getProcess() == page.getProcess() && allowLocalReclacement){
                    framesIds.add(i);
                } else if (parameters.getReplacementScope() == ReplacementScope.GLOBAL && 
                        memoryFrames.get(i).getAllocatedPage()
                                .getProcess().getPriority() >= page.getProcess().getPriority() &&
                        !isMaxResidentSetReached(page.getProcess())
                        ){
                    framesIds.add(i);
                }
            }
        }
        
        return framesIds;
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
    
    //GETTERS FOR EXTERNAL USE
    
    public String[][] processesStatSummary(){
        String[][] stats = new String [processes.size()][5];
        for (int i = 0; i < processes.size(); i++){
            stats[i][0] = processes.get(i).getId();
            stats[i][1] = String.valueOf(getAllocatedMemoryAmount(processes.get(i)))
                    + "/" + String.valueOf(processes.get(i).getMemoryRequired()) + " KB";
            stats[i][2] = String.valueOf(processes.get(i).getAccessCount());
            stats[i][3] = String.valueOf(processes.get(i).getPageFaultCount());
            stats[i][4] = String.valueOf(processes.get(i).getPageFaultPercentage()) + "%";
            
        }
        return stats;
    }
    
    public int getAllocatedMemoryAmount(Process process){
        int memory = 0;
        for (Page page : process.getPages()){
            if(page.isAllocated()){
                memory += parameters.getPageSize();
            }
        }
        return memory;
    }
    
    public int getTotalMemoryAccesses(){
        int totalMemoryAccesses = 0;
        for (Process process : processes){
            totalMemoryAccesses += process.getAccessCount();
        }
        return totalMemoryAccesses;
    }
    
    public int getTotalPageFaults(){
        int totalPageFaults = 0;
        for (Process process : processes){
            totalPageFaults += process.getPageFaultCount();
        }
        return totalPageFaults;
    }
    
    public float getTotalPageFaultPercentage(){
        return 100 * ((float) getTotalPageFaults()) / 
                ((float) getTotalMemoryAccesses());
    }
    
    public int getPhysicalMemoryUsed(){
        int physicalMemoryUsed = 0;
        for (Frame frame : memoryFrames){
            if(frame.isInUse()){
                physicalMemoryUsed += parameters.getPageSize();
            }
        }
        return physicalMemoryUsed;
    }
    
    public int getVirtualMemoryUsed(){
        return parameters.getMaxVirtualMemory() - this.virtualMemoryAvailable;
    }
}
