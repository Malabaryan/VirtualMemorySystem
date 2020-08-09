/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Controller.MainController;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Kenneth
 */
public class ViewPages extends javax.swing.JDialog {
    MainController mainController;
    /**
     * Creates new form NewVMS
     */
    public ViewPages(java.awt.Frame parent, boolean modal, MainController mainController) {
        super(parent, modal);
        this.mainController = mainController;
        initComponents();
        DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel(mainController.getProcessIds().toArray());
        CBProcessesNames.setModel(model);
        updatePageViewer();
        this.setLocationRelativeTo(null);
    }
    
    public void updatePageViewer(){
        String processId = (String) CBProcessesNames.getSelectedItem();
        if (processId != null){
            ArrayList<VirtualMemorySystem.MemoryViewer.FrameObject> frameObjects = 
                    mainController.getPageObjects(
                            pageViewer.getHeight(), 
                            pageViewer.getWidth(),
                            processId
                    );
            ((VirtualMemorySystem.MemoryViewer) pageViewer).setFrameObjects(frameObjects);
            pageViewer.repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CBProcessesNames = new javax.swing.JComboBox<>();
        pageViewer = new VirtualMemorySystem.MemoryViewer();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Pages");

        jLabel2.setText("ProcessId:");

        CBProcessesNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBProcessesNamesActionPerformed(evt);
            }
        });

        pageViewer.setToolTipText("");

        javax.swing.GroupLayout pageViewerLayout = new javax.swing.GroupLayout(pageViewer);
        pageViewer.setLayout(pageViewerLayout);
        pageViewerLayout.setHorizontalGroup(
            pageViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pageViewerLayout.setVerticalGroup(
            pageViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 307, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pageViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBProcessesNames, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(CBProcessesNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pageViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CBProcessesNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBProcessesNamesActionPerformed
        updatePageViewer();
    }//GEN-LAST:event_CBProcessesNamesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBProcessesNames;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JPanel pageViewer;
    // End of variables declaration//GEN-END:variables
}
