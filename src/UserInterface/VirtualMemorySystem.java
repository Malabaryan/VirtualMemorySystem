/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Controller.MainController;
import Logging.Log;
import Model.Parameters;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Kenneth
 */
public class VirtualMemorySystem extends javax.swing.JFrame {
    public MainController mainController;
    private ViewUpdater updater;
    
    private Simulation simulation;
    
    private ViewPages processPageViewer;
    
    private ArrayList<String> loadedCommands;
    
    private class ViewUpdater extends Thread {
        private VirtualMemorySystem parent;
        
        public ViewUpdater(VirtualMemorySystem parent){
            this.parent = parent;
        }
        
        public void run() {
            while(true){
                try {
                    Thread.sleep(33);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VirtualMemorySystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.parent.updateMemoryViewer();
                this.parent.updateConsole();
                if (this.parent.processPageViewer != null)
                    this.parent.processPageViewer.update();
            }
        }
    }
    
    private class Simulation extends Thread {
        private MainController mainController;
        private boolean running;
        
        public Simulation(MainController mainController){
            this.mainController = mainController;
            this.running = true;
        }

        public void run() {
            while(running){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VirtualMemorySystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                mainController.randAccess();
            }
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public boolean isRunning(){
            return this.running;
        }
    }
    
    public static class MemoryViewer extends JPanel{
        
        ArrayList<FrameObject> frameObjects = new ArrayList<>();
        
        public void MemoryViewer(){
            
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            for (FrameObject frameObject : frameObjects){
                g.setColor(frameObject.getColor());
                ((Graphics2D) g).fill(frameObject);
            }
            g.dispose();
        }
        
        @Override
        public String getToolTipText(MouseEvent event) {
            for (FrameObject fo : frameObjects) {
                if (fo.contains(event.getPoint())) {
                    return fo.getToolTipText();
                }
            }
            return null;
        }
        
        public void setFrameObjects(ArrayList<FrameObject> frameObjects){
            this.frameObjects = frameObjects;
        }
        
        public static class FrameObject extends Rectangle {
            private final String toolTipText;
            private final Color color;
            
            public FrameObject(int x, int y, int width, int height, String info, Color color){
                super(x, y, width, height);
                this.toolTipText = info;
                this.color = color;
            }
            public String getToolTipText(){
                return this.toolTipText;
            }
            
            public Color getColor(){
                return this.color;
            }
        }
    }
    
    /**
     * Creates new form VirtualMemorySystem
     * @param mainController
     */
    public VirtualMemorySystem(MainController mainController) {
        this.mainController = mainController;
        //this.memoryViewer = new MemoryViewer();
        initComponents();
        loadedCommands = new ArrayList<>();
        updater = new ViewUpdater(this);
        updater.start();
        this.setLocationRelativeTo(null);
    }
    
    public void updateMemoryViewer(){
        ArrayList<MemoryViewer.FrameObject> frameObjects = 
                mainController.getFrameObjects(
                        memoryViewer.getHeight(), 
                        memoryViewer.getWidth()
                );
        if(frameObjects == null)
            return;
        ((MemoryViewer) memoryViewer).setFrameObjects(frameObjects);
        memoryViewer.repaint();
    }
    
    public void updateConsole(){
        this.console.setText(Logging.Logger.getAllLogs());
    }
    
    public void updateCommandList(){
        DefaultListModel model = new DefaultListModel();
        for (String command : loadedCommands){
            model.addElement(command);
        }
        this.commandList.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jMenu4 = new javax.swing.JMenu();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        commandList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        memoryViewer = new MemoryViewer();
        commandLine = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        StartStopSimulation = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu4.setText("jMenu4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Execute");
        jButton1.setToolTipText("Executes up to the selected command.");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        console.setEditable(false);
        console.setColumns(20);
        console.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        console.setForeground(new java.awt.Color(0, 0, 0));
        console.setRows(5);
        console.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        console.setEnabled(false);
        jScrollPane1.setViewportView(console);

        jScrollPane2.setViewportView(commandList);

        jLabel1.setText("Memory Frames");
        jLabel1.setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        memoryViewer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        memoryViewer.setToolTipText("");
        memoryViewer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout memoryViewerLayout = new javax.swing.GroupLayout(memoryViewer);
        memoryViewer.setLayout(memoryViewerLayout);
        memoryViewerLayout.setHorizontalGroup(
            memoryViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        memoryViewerLayout.setVerticalGroup(
            memoryViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(memoryViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(memoryViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        memoryViewer.getAccessibleContext().setAccessibleDescription("Memory Frames");

        commandLine.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        commandLine.setToolTipText("<ProcessID>,<Referenced address>,<Access type (write/read)>");
        commandLine.setRequestFocusEnabled(false);
        commandLine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                commandLineKeyPressed(evt);
            }
        });

        jLabel2.setText("Command");

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New Virtual Memory System");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Load process file..");
        jMenuItem2.setToolTipText("Load a batch file with process definitions.");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem6.setText("Load memory access file...");
        jMenuItem6.setToolTipText("Loads a file of memory access commands.");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("View");

        jMenuItem5.setText("Process Pages");
        jMenuItem5.setToolTipText("Shows all the pages for each process.");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem4.setText("Statistics");
        jMenuItem4.setToolTipText("Show a summary of the execution.");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Testing");

        jMenuItem3.setText("Random Access");
        jMenuItem3.setToolTipText("Executes a random access. (TESTING PURPOSES ONLY).");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        StartStopSimulation.setText("Start Simulation");
        StartStopSimulation.setToolTipText("Starts a thread to run random accesses (TESTING PURPOSES ONLY).");
        StartStopSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartStopSimulationActionPerformed(evt);
            }
        });
        jMenu2.add(StartStopSimulation);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("About");

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Help");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(commandLine))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(commandLine, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        NewVMS newVMSWindow = new NewVMS(this, true);
        Parameters parameters = newVMSWindow.showDialog();
        if (parameters != null)
            mainController.newVMS(parameters);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        mainController.simulation();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (!this.mainController.getProcessIds().isEmpty()){
            processPageViewer = new ViewPages(this, true, mainController);
            processPageViewer.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Process Definition File *.p", "p"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                String contents = new String(Files.readAllBytes(Paths.get(selectedFile)));
                this.mainController.executeAddProcessCommand(contents);
            } catch (Exception ex) {
                Logging.Logger.log(Log.Type.ERROR, "Cannot open file: " + selectedFile);
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Memory Access File *.ma", "ma"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                String contents = new String(Files.readAllBytes(Paths.get(selectedFile)));
                this.loadedCommands = new ArrayList<>(Arrays.asList(contents.split("\n")));
                updateCommandList();
            } catch (Exception ex) {
                Logging.Logger.log(Log.Type.ERROR, "Cannot open file: " + selectedFile);
            }
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int commandsToExecute = commandList.getSelectedIndex();
        for(int i = 0; i <= commandsToExecute; i++){
            String command = loadedCommands.get(0);
            loadedCommands.remove(0);
            this.mainController.executeCommand(command);
            updateCommandList();
        }
        updateCommandList();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void StartStopSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartStopSimulationActionPerformed
        if(simulation != null){
            StartStopSimulation.setText("Start Simulation");
            simulation.setRunning(false);
            simulation = null;
        } else {
            StartStopSimulation.setText("Stop Simulation");
            simulation = new Simulation(this.mainController);
            simulation.start();
        }
        
    }//GEN-LAST:event_StartStopSimulationActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new Statistics(this, true, mainController).setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new Help(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void commandLineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_commandLineKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String command = commandLine.getText();
            commandLine.setText("");
            this.mainController.executeCommand(command);
        }
    }//GEN-LAST:event_commandLineKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem StartStopSimulation;
    private javax.swing.JTextField commandLine;
    private javax.swing.JList<String> commandList;
    private javax.swing.JTextArea console;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel memoryViewer;
    // End of variables declaration//GEN-END:variables
}
