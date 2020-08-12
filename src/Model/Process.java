/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Kenneth
 */
public class Process {
    private final String id;
    private final int memoryRequired;
    private final int priority;
    
    private final VMS system;
    
    private final ArrayList<Page> pageTable;
    
    private int accessCount;
    private int pageFaultCount;
    
    public Process(VMS system, String id, int memoryRequired, int priority){
        this.system = system;
        this.id = id;
        this.memoryRequired = memoryRequired;
        this.priority = priority;
        this.pageTable = new ArrayList<>();
    }
    
    public void initializePages(int pageSize){
        pageTable.clear();
        for(int toAllocate = 0; toAllocate < memoryRequired ; toAllocate += pageSize){
            pageTable.add(new Page(this, pageSize, toAllocate));
        }
    }
    
    public void accessMemory(int address, boolean writes){
        this.system.accessMemory(this, address, writes);
    }

    public void deallocateAllPages(){
        for (Page page : pageTable){
            page.setDeallocated();
        }
    }
    
    public String getId(){
        return this.id;
    }
    
    public int getPriority(){
        return this.priority;
    }
    
    public ArrayList<Page> getPages(){
        return this.pageTable;
    }
    
    public int getMemoryRequired(){
        return this.memoryRequired;
    }
    
    public void increasePageFaultCount(){
        this.pageFaultCount++;
    }
    
    public void increaseAccessCount(){
        this.accessCount++;
    }
    
    public int getAccessCount(){
        return this.accessCount;
    }
    
    public int getPageFaultCount(){
        return this.pageFaultCount;
    }
    
    public float getPageFaultPercentage(){
        return 100 * ((float) this.pageFaultCount) / ((float) this.accessCount);
    }
    
    public Page getNextTo(Page page){
        for (int i = 0; i < pageTable.size() - 1; i++){
            if(pageTable.get(i) == page){
                return pageTable.get(i + 1);
            }
        }
        return null;
    }
}
