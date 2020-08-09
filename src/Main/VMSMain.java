/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.MainController;
import Logging.Logger;
import Model.*;
import UserInterface.VirtualMemorySystem;
import java.util.logging.Level;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Bryan Hernandez
 */
public class VMSMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //UI Start
        VirtualMemorySystem vmsWindow;
        vmsWindow = new VirtualMemorySystem(MainController.getInstance());
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {}
        vmsWindow.setVisible(true);
        
    }
    
}
