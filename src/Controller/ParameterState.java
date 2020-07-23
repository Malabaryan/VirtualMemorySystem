/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Bryan Hernandez
 */
public enum ParameterState {
    FetchPolicy_Demand,
    FetchPolicy_Prepaging,
    
    PlacementPolicy_First,
    PlacementPolicy_Next,
    
    ReplacementPolicy_LRU,
    ReplacementPolicy_FIFO,
    ReplacementPolicy_LFU,
    ReplacementPolicy_MRU,
    ReplacementPolicy_SecondChance,
    
    RSM_Size_Fixed,
    RSM_Size_Variable,
    
    ReplacementScope_Global,
    ReplacementScope_Local,
    
    CleaningPolicy_Demand,
    CleaningPolicy_PreCleaning,

    LoadControl_Multiprogramming  
}
