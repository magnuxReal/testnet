/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Panels.magazines.allMagazines;
import Panels.magazines.loadMagazine;
import Panels.magazines.magazineMenu;
import Panels.magazines.newMagazine;
import javax.swing.JPanel;

/**
 *
 * @author Karolis
 */
public class magazine extends javax.swing.JPanel {
    private static JPanel  reff = null;

    public static void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Creates new form magazine
     */
    public magazine() {
        initComponents();
        jPanel1.add(new magazineMenu());
        jPanel2.add(new allMagazines());
        reff = this;
    }

    private void addNewPanel(JPanel changeTo){
        jPanel2.removeAll();
        jPanel2.revalidate();
        jPanel2.repaint();
        jPanel2.add(changeTo);        
    }
    
    /**
     *
     * @param id_get
     * @param id_magazine
     */
    public static void refresh(int id_get){
        magazine ok = (magazine) reff;
        switch (id_get) {
            case 1:  ok.addNewPanel(new allMagazines());
            break;
            case 2:  ok.addNewPanel(new newMagazine());
            break;
            case 3:  ok.addNewPanel(new loadMagazine(0));
            break;
        }
         
    }

    public static void refresh(int id_get, int id_magazine){
        magazine ok = (magazine) reff;
        ok.addNewPanel(new loadMagazine(id_magazine));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables


}
