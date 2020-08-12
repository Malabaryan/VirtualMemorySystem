/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.MainController;
import UserInterface.VirtualMemorySystem;
import javax.swing.UIManager;

/**
 *
 * @author Bryan Hernandez
 */
public class VMSMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {}
        //UI Start
        VirtualMemorySystem vmsWindow;
        vmsWindow = new VirtualMemorySystem(MainController.getInstance());
        vmsWindow.setVisible(true);
        
    }
    
}
