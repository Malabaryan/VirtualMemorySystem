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
public class ParametersController {
    private static ParametersController parametersController;
    
    private static ParameterState FetchPolicy;
    private static ParameterState PlacementPolicy;
    private static ParameterState ReplacementPolicy;
    private static ParameterState ResidentSetManagement;
    private static ParameterState ReplacementScope;
    private static ParameterState ClaningPolicy;
    private static ParameterState LoadControll;
    

    private ParametersController() {
    }
    
    public static void defaultParameters(){
        FetchPolicy = ParameterState.FetchPolicy_Demand;
        PlacementPolicy = ParameterState.PlacementPolicy_First;
        ReplacementPolicy = ParameterState.ReplacementPolicy_LRU;
        ResidentSetManagement = ParameterState.RSM_Size_Fixed;
        ReplacementScope = ParameterState.ReplacementScope_Global;
        ClaningPolicy = ParameterState.CleaningPolicy_Demand;
        LoadControll = ParameterState.LoadControl_Multiprogramming;

    }

    public static ParametersController getInstance() {
        if(parametersController == null){
            parametersController = new ParametersController(); 
            defaultParameters();
        }
        return parametersController;
    }

    public static ParametersController getParametersController() {
        return parametersController;
    }

    public static void setParametersController(ParametersController parametersController) {
        ParametersController.parametersController = parametersController;
    }

    public static ParameterState getFetchPolicy() {
        return FetchPolicy;
    }

    public static void setFetchPolicy(ParameterState FetchPolicy) {
        ParametersController.FetchPolicy = FetchPolicy;
    }

    public static ParameterState getPlacementPolicy() {
        return PlacementPolicy;
    }

    public static void setPlacementPolicy(ParameterState PlacementPolicy) {
        ParametersController.PlacementPolicy = PlacementPolicy;
    }

    public static ParameterState getReplacementPolicy() {
        return ReplacementPolicy;
    }

    public static void setReplacementPolicy(ParameterState ReplacementPolicy) {
        ParametersController.ReplacementPolicy = ReplacementPolicy;
    }

    public static ParameterState getResidentSetManagement() {
        return ResidentSetManagement;
    }

    public static void setResidentSetManagement(ParameterState ResidentSetManagement) {
        ParametersController.ResidentSetManagement = ResidentSetManagement;
    }

    public static ParameterState getReplacementScope() {
        return ReplacementScope;
    }

    public static void setReplacementScope(ParameterState ReplacementScope) {
        ParametersController.ReplacementScope = ReplacementScope;
    }

    public static ParameterState getClaningPolicy() {
        return ClaningPolicy;
    }

    public static void setClaningPolicy(ParameterState ClaningPolicy) {
        ParametersController.ClaningPolicy = ClaningPolicy;
    }

    public static ParameterState getLoadControll() {
        return LoadControll;
    }

    public static void setLoadControll(ParameterState LoadControll) {
        ParametersController.LoadControll = LoadControll;
    }
 
    
}
