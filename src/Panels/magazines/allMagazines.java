/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels.magazines;

import Classes.Concept;
import Classes.EXhelper;
import Main.MysqlConnect;
import Panels.magazine;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Karolis
 */
public class allMagazines extends javax.swing.JPanel {
    Concept[] arr = {};
    private List<Concept> info; 
    private int selected_id_magazine;
    private String magazine_number;
    private int id_recipe;
    /**
     * Creates new form allMagazines
     */
    public allMagazines() {
     
        initComponents();
        loadMagazines(0);
        renderFilter();
        jButtonDeleteMagazine.setVisible(false);
        TableColumnModel tcm = jTable1.getColumnModel();
        tcm.getColumn(0).setMinWidth(0);
        tcm.getColumn(0).setMaxWidth(0);
        tcm.getColumn(0).setWidth(0);  
        tcm.getColumn(1).setPreferredWidth(200);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(300);
 
        tcm.getColumn(4).setCellRenderer(new EXhelper.DecimalFormatRenderer()); 
        
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                                                          final Object value,
                                                          final int index,
                                                          final boolean isSelected,
                                                          final boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected,
                                                   cellHasFocus);
                if (value instanceof Concept)
                    setText(((Concept) value).getLabel());

                return this;
            }
        });
        
        comboBox.addActionListener(actionEvent -> {
            final Object selectedItem = comboBox.getSelectedItem();
            if (selectedItem instanceof Concept){
                int id_recipe = Integer.valueOf(((Concept) selectedItem).getValue());
               loadMagazines(id_recipe);
            }
        });

    jTable1.addMouseListener(new MouseAdapter() {

            public void mouseClicked (MouseEvent me) {
                int index  = jTable1.getSelectedRow();
                jButtonDeleteMagazine.setVisible(true);
                if(index != -1){
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    selected_id_magazine = (int) model.getValueAt(index, 0);
                    magazine_number = (String) model.getValueAt(index, 2);
               
                    if (me.getClickCount() == 2) {
                        magazine.refresh(0, selected_id_magazine);
                    }
                }
            }
        });    
    
        
    }
    
    private void renderFilter(){
        comboBox.addItem(new Concept("--Rodomi visi receptai--", "0"));
        try {         
            Statement st = MysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_recipe ORDER BY id DESC");
            int i = 0;
            while (res.next()) {
                String id = res.getString("id");
                String name  = res.getString("name");
                String note  = res.getString("note");
          
                comboBox.addItem(new Concept(name, id));
              
            }  
        
        } catch (SQLException e) {
        } finally {
            //MysqlConnect.disconnect();
             
        }            
         
    }
    
    public void loadMagazines(int id_recipe){
            Statement st;
            String whereSQL = "";
            this.id_recipe = id_recipe;
            try {
                st = MysqlConnect.connect().createStatement();
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                
                if(id_recipe > 0){
                       whereSQL = " AND  m.id_recipe = '"+id_recipe+"' ";
                }
                    ResultSet res = st.executeQuery("SELECT * FROM  dm_magazine AS m "
                            + "LEFT JOIN dm_recipe AS r ON (r.id=m.id_recipe) WHERE m.disp=1 "
                            + whereSQL + "ORDER BY m.id DESC");
                 
                while (model.getRowCount() > 0) {
                        model.removeRow(0);
                }
                while(res.next()) {
                    String newdate = "0";
 
                    try{
                        Date date = new SimpleDateFormat("yyyy-mm-DD").parse(res.getString("date"));
                        newdate  = new SimpleDateFormat("mm.DD").format(date);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    Object[] row = {res.getInt("id"), res.getString("date"), newdate, res.getString("name"), res.getDouble("total_made"), 0};
                    model.addRow(row);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(allMagazines.class.getName()).log(Level.SEVERE, null, ex);
            }
 
    }
 
    public void add_element(Concept element){
        arr = Arrays.copyOf(arr, arr.length +1);
        arr[arr.length - 1] = element;
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        comboBox = new javax.swing.JComboBox<>();
        jButtonDeleteMagazine = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#ID", "Data", "Numeris", "Receptas", "Kiekis (Kg)", "Busena"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Filtras");

        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        jButtonDeleteMagazine.setText("Trinti");
        jButtonDeleteMagazine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteMagazineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeleteMagazine)))
                .addGap(0, 159, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDeleteMagazine))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxActionPerformed

    private void jButtonDeleteMagazineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteMagazineActionPerformed
       
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Ar tikrai norite ištrinti "+magazine_number+" ?", "Ištrinti gamybos žurnalą", dialogButton);
        if(dialogResult == 0) {
            jButtonDeleteMagazine.setVisible(false);
            try {
                Statement st = MysqlConnect.connect().createStatement();   
                    st.executeUpdate("UPDATE dm_magazine SET disp='0' WHERE id='"+selected_id_magazine+"' ");

            } catch (SQLException e) {
            } finally {
                 loadMagazines(id_recipe);
            } 
            
           
        } 
    }//GEN-LAST:event_jButtonDeleteMagazineActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Concept> comboBox;
    private javax.swing.JButton jButtonDeleteMagazine;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
