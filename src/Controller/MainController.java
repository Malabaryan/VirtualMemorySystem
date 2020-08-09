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
        vms.addProcess("Chrome", 500, 1);
        vms.addProcess("Firefox", 500, 1);
        vms.accessMemory("Chrome", 2, false);
        vms.accessMemory("Firefox", 70000, false);
        vms.accessMemory("Chrome", 70000, false);
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
                vms.addProcess(command[0], Integer.parseInt(command[1]), Integer.parseInt(command[1]));
            } catch (Exception e){
                Logger.log(Log.Type.ERROR, "Cannot parse command: " + commandLine);
            }
        }
    }
    
    public void executeCommands(String commandText){
        if(vms == null){
            Logger.log(Log.Type.ERROR, "Cannot run commands without a VMS.");
            return;
        }
        String[] commands = commandText.split("\n");
        for(String commandLine : commands){
            String[] command = commandLine.split(",");
            try {
                vms.accessMemory(command[0], Integer.parseInt(command[1]), 
                        command[2].equals("write"));
            } catch (Exception e){
                e.printStackTrace();
                Logger.log(Log.Type.ERROR, "Cannot parse command: " + commandLine);
            }
        }
    }
    
    public ArrayList<MemoryViewer.FrameObject> getFrameObjects(int panelHeight, int panelWidth){
        ArrayList<MemoryViewer.FrameObject> frameObjects = new ArrayList<>();
        
        if(vms == null)
            return frameObjects;
        
        ArrayList<Frame> frames = vms.getMemoryFrames();
        
        int frameSize = 30;
        int framesPerFile = panelWidth / frameSize;
        for (int f = 0; f < frames.size(); f++){
            MemoryViewer.FrameObject frameObject 
                    = new MemoryViewer.FrameObject(
                            (f % framesPerFile) * frameSize,
                            (f / framesPerFile) * frameSize,
                            frameSize - 4, frameSize - 4, 
                            "<html>" + frames.get(f).toHTML(),
                            frames.get(f).getAllocatedPage() == null ? Color.GRAY 
                                    : new Color(frames.get(f).getAllocatedPage().getProcess().getId().hashCode())
                    );
            frameObjects.add(frameObject);
        }
        return frameObjects;
    }
    
    public ArrayList<String> getProcessIds(){
        ArrayList<String> processIds = new ArrayList<>();
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
        ArrayList<Page> pages = process.getPages();
        
        int frameSize = 30;
        int framesPerFile = panelWidth / frameSize;
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
        return frameObjects;
    }
}
