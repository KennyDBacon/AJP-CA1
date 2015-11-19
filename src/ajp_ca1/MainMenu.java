/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajp_ca1;

import java.awt.CardLayout;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author Kenny
 */
public class MainMenu extends javax.swing.JFrame {
    Manager manager;
    /**
     * Creates new form MainMenu
     */
    public MainMenu() throws IOException{
        initComponents();
        setLocationRelativeTo(null);
        
        manager = new Manager();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnBusStopSearch = new javax.swing.JButton();
        btnServicesRouteSearch = new javax.swing.JButton();
        btnFindRoute = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnBusStopSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBusStopSearch.setText("<html>Search Services by<br/>Stops Description</html>");
        btnBusStopSearch.setAlignmentX(0.5F);
        btnBusStopSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusStopSearchActionPerformed(evt);
            }
        });

        btnServicesRouteSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnServicesRouteSearch.setText("<html>Display route of<br/>Bus Services</html>");
        btnServicesRouteSearch.setToolTipText("");
        btnServicesRouteSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServicesRouteSearchActionPerformed(evt);
            }
        });

        btnFindRoute.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFindRoute.setText("Find Route");
        btnFindRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindRouteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnFindRoute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBusStopSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(btnServicesRouteSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBusStopSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnServicesRouteSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFindRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnBusStopSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusStopSearchActionPerformed
        StopsSearchFrame frame = new StopsSearchFrame();
        frame.mainMenu = this;
        frame.setVisible(true);
        
        this.setVisible(false);
    }//GEN-LAST:event_btnBusStopSearchActionPerformed

    private void btnServicesRouteSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServicesRouteSearchActionPerformed
        RouteSearchFrame frame = new RouteSearchFrame();
        frame.mainMenu = this;
        frame.setVisible(true);
        
        this.setVisible(false);
    }//GEN-LAST:event_btnServicesRouteSearchActionPerformed

    private void btnFindRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindRouteActionPerformed
        CustomRouteSearchFrame frame = new CustomRouteSearchFrame();
        frame.mainMenu = this;
        frame.setVisible(true);
        
        this.setVisible(false);
    }//GEN-LAST:event_btnFindRouteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try 
                {
                    new MainMenu().setVisible(true);
                }
                catch (IOException ex){
                     ex .printStackTrace();
                     System.exit(0);
                } 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusStopSearch;
    private javax.swing.JButton btnFindRoute;
    private javax.swing.JButton btnServicesRouteSearch;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}