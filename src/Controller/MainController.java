/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Logging.Log;
import Logging.Logger;
import Model.*;
import UserInterface.VirtualMemorySystem.MemoryViewer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Bryan Hernandez
 */
public class MainController {
    private static MainController instance;
    private VMS vms; 
    
    private boolean operation = true;
    
    private MainController(){
        
    }
    
    public static MainController getInstance(){
        if (instance == null){
            instance = new MainController();
        }
        return instance;
    }
    
    public void newVMS(Parameters parameters) {
        vms = new VMS(parameters);
    }
    
    public void simulation(){
        for(int i = 0; i < 1; i++){
            randAccess();
        }
    }
    
    public void randAccess(){
        ArrayList<Model.Process> processes = vms.getProcesses();
        Random r = new Random();
        Model.Process process = processes.get(r.nextInt(processes.size()));
        process.accessMemory(r.nextInt(process.getMemoryRequired()*1024), r.nextBoolean());
    }
    
    public void executeAddProcessCommand(String commandText){
        if(vms == null){
            Logger.log(Log.Type.ERROR, "Cannot run commands without a VMS.");
            return;
        }
        String[] commands = commandText.split("\n");
        for(String commandLine : commands){
            String[] command = commandLine.split(",");
            try {
                vms.addProcess(command[0], Integer.parseInt(command[1]), Integer.parseInt(command[2]));
            } catch (Exception e){
                Logger.log(Log.Type.ERROR, "Cannot parse command: " + commandLine);
            }
        }
    }
    
    public void executeCommand(String commandLine){
        if(vms == null){
            Logger.log(Log.Type.ERROR, "Cannot run commands without a VMS.");
            return;
        }
        String[] command = commandLine.split(",");
        try {
            vms.accessMemory(command[0], Integer.parseInt(command[1]), 
                    command[2].equals("write"));
        } catch (Exception e){
            e.printStackTrace();
            Logger.log(Log.Type.ERROR, "Cannot parse command: " + commandLine);
        }
    }
    
    public ArrayList<MemoryViewer.FrameObject> getFrameObjects(int panelHeight, int panelWidth){
        ArrayList<MemoryViewer.FrameObject> frameObjects = new ArrayList<>();
        
        if(vms == null)
            return frameObjects;
        
        ArrayList<Frame> frames = vms.getMemoryFrames();
        
        int frameSize = 30;
        while ((panelHeight / frameSize) * (panelWidth / frameSize) < frames.size()){
            frameSize--;
        }
        
        int framesPerFile = panelWidth / frameSize;
        try {
            for (int f = 0; f < frames.size(); f++){
                MemoryViewer.FrameObject frameObject 
                        = new MemoryViewer.FrameObject(
                                (f % framesPerFile) * frameSize,
                                (f / framesPerFile) * frameSize,
                                (int) (frameSize * 0.9),(int) (frameSize * 0.9), 
                                "<html>" + frames.get(f).toHTML(),
                                frames.get(f).getAllocatedPage() == null ? Color.GRAY 
                                        : new Color(frames.get(f).getAllocatedPage().getProcess().getId().hashCode())
                        );
                frameObjects.add(frameObject);
            }
        } catch (Exception e){
            return null;
        }
        return frameObjects;
    }
    
    public ArrayList<String> getProcessIds(){
        ArrayList<String> processIds = new ArrayList<>();
        if(vms == null)
            return processIds;
        for(Model.Process process : vms.getProcesses()){
            processIds.add(process.getId());
        }
        return processIds;
    }
    
    public ArrayList<MemoryViewer.FrameObject> getPageObjects(int panelHeight, int panelWidth, String processId){
        ArrayList<MemoryViewer.FrameObject> frameObjects = new ArrayList<>();
        
        if(vms == null)
            return frameObjects;
        Model.Process process = vms.getProcess(processId);
        if(process == null){
            return frameObjects;
        }
        ArrayList<Page> pages = process.getPages();
               
        int frameSize = 30;
        while ((panelHeight / frameSize) * (panelWidth / frameSize) < pages.size()){
            frameSize--;
        }
        
        int framesPerFile = panelWidth / frameSize;
        try {
            for (int f = 0; f < pages.size(); f++){
                MemoryViewer.FrameObject frameObject 
                        = new MemoryViewer.FrameObject(
                                (f % framesPerFile) * frameSize,
                                (f / framesPerFile) * frameSize,
                                frameSize - 4, frameSize - 4, 
                                "<html>" + pages.get(f).toHTML(),
                                pages.get(f).isAllocated() ? Color.GREEN 
                                        : Color.GRAY
                        );
                frameObjects.add(frameObject);
            }
        } catch (Exception e){
            return null;
        }
        return frameObjects;
    }
    
    public String[][] getProcessesStatSummary(){
        if (vms == null){
            return null;
        } else {
            return vms.processesStatSummary();
        }
    }
    
    public String getTotalMemoryAccesses(){
        if (vms == null){
            return "--";
        } else {
            return String.valueOf(vms.getTotalMemoryAccesses());
        }
    }
    
    public String getTotalPageFaults(){
        if (vms == null){
            return "--";
        } else {
            return String.valueOf(vms.getTotalPageFaults());
        }
    }
    
    public String getTotalPageFaultPercentage(){
        if (vms == null){
            return "--";
        } else {
            return String.valueOf(vms.getTotalPageFaultPercentage()) + "%";
        }
    }
    
    public String getVirtualMemoryUsed(){
        if (vms == null){
            return "--";
        } else {
            int vMemoryUsed = vms.getVirtualMemoryUsed();
            int vTotalMemory = vms.getParameters().getMaxVirtualMemory();
            return String.valueOf(vMemoryUsed) + "/" + 
                    String.valueOf(vTotalMemory) + " KB " + 
                    String.valueOf(100 * ((float) vMemoryUsed) / ((float) vTotalMemory))
                    + "%";
        }
    }
    
    public String getPhysicalMemoryUsed(){
        if (vms == null){
            return "--";
        } else {
            int memoryUsed = vms.getPhysicalMemoryUsed();
            int totalMemory = vms.getParameters().getPhysicalMemory();
            return String.valueOf(memoryUsed) + "/" + 
                    String.valueOf(totalMemory) + " KB " + 
                    String.valueOf(100 * ((float) memoryUsed) / ((float) totalMemory)) 
                    + "%";
        }
    }
}
