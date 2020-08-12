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
public class Parameters {

    public enum FethPolicy {
        DEMAND,
        PREPAGING
    }
    public enum PlacementPolicy {
        FIRST,
        NEXT
    }
    
    public enum ReplacementPolicy {
        FIFO,
        LRU,
        LFU,
        MRU,
        SECONDCHANCE
    }
    
    public enum ReplacementScope {
        GLOBAL,
        LOCAL
    }
    
    public enum CleaningPolicy {
        DEMAND,
        PRECLEANING
    }
    
    private FethPolicy fethPolicy = FethPolicy.DEMAND;
    private PlacementPolicy placementPolicy = PlacementPolicy.FIRST;
    private ReplacementPolicy replacementPolicy = ReplacementPolicy.FIFO;
    private ReplacementScope replacementScope = ReplacementScope.GLOBAL;
    private CleaningPolicy cleaningPolicy = CleaningPolicy.DEMAND;
    
    private int multiProgramingDegree = 5;
    
    private int physicalMemory = 1024 * 20;
    private int maxVirtualMemory = 1024 * 40;
    private int pageSize = 64;
    
    private int minResidentSet = 0;
    private int maxResidentSet = 0;
    
    public Parameters(){
        
    }
    
    public FethPolicy getFethPolicy() {
        return fethPolicy;
    }

    public void setFethPolicy(FethPolicy aFethPolicy) {
        fethPolicy = aFethPolicy;
    }

    public PlacementPolicy getPlacementPolicy() {
        return placementPolicy;
    }

    public void setPlacementPolicy(PlacementPolicy aPlacementPolicy) {
        placementPolicy = aPlacementPolicy;
    }

    public ReplacementPolicy getReplacementPolicy() {
        return replacementPolicy;
    }

    public void setReplacementPolicy(ReplacementPolicy aReplacementPolicy) {
        replacementPolicy = aReplacementPolicy;
    }

    public ReplacementScope getReplacementScope() {
        return replacementScope;
    }
    
    public void setReplacementScope(ReplacementScope aReplacementScope) {
        replacementScope = aReplacementScope;
    }

    public CleaningPolicy getCleaningPolicy() {
        return cleaningPolicy;
    }

    public void setCleaningPolicy(CleaningPolicy aCleaningPolicy) {
        cleaningPolicy = aCleaningPolicy;
    }

    public int getMultiProgramingDegree() {
        return multiProgramingDegree;
    }

    public void setMultiProgramingDegree(int aMultiProgramingDegree) {
        multiProgramingDegree = aMultiProgramingDegree;
    }

    public int getPhysicalMemory() {
        return physicalMemory;
    }

    public void setPhysicalMemory(int aPhysicalMemory) {
        physicalMemory = aPhysicalMemory;
    }

    public int getMaxVirtualMemory() {
        return maxVirtualMemory;
    }

    public void setMaxVirtualMemory(int aMaxVirtualMemory) {
        maxVirtualMemory = aMaxVirtualMemory;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int aPageSize) {
        pageSize = aPageSize;
    }

    public int getMinResidentSet() {
        return minResidentSet;
    }

    public void setMinResidentSet(int minResidentSet) {
        this.minResidentSet = minResidentSet;
    }

    public int getMaxResidentSet() {
        return maxResidentSet;
    }

    public void setMaxResidentSet(int maxResidentSet) {
        this.maxResidentSet = maxResidentSet;
    }
}
