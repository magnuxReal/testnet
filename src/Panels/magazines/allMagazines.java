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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Karolis
 */
public class allMagazines extends javax.swing.JPanel {
    Concept[] arr = {};
    private List<Concept> info; 
    /**
     * Creates new form allMagazines
     */
    public allMagazines() {
     
        initComponents();
        loadMagazines(0);
        renderFilter();
        
        TableColumnModel tcm = jTable1.getColumnModel();

 
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
                if (me.getClickCount() == 2) {
                    int index  = jTable1.getSelectedRow();

                    if(index != -1){
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        int id_magazine = (int) model.getValueAt(index, 0);
                        magazine.refresh(0, id_magazine);
              
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
            try {
                st = MysqlConnect.connect().createStatement();
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                
                if(id_recipe > 0){
                       whereSQL = " WHERE  m.id_recipe = '"+id_recipe+"' ";
                }
                    ResultSet res = st.executeQuery("SELECT * FROM  dm_magazine AS m "
                            + "LEFT JOIN dm_recipe AS r ON (r.id=m.id_recipe) "
                            + whereSQL + "ORDER BY m.id ASC");
                 
                while (model.getRowCount() > 0) {
                        model.removeRow(0);
                }
                while(res.next()) {
                    Object[] row = {res.getInt("id"), res.getString("date"), 0, res.getString("name"), res.getDouble("kg"), 0};
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

        setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel1.setText("Filtras");

        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(267, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addGap(108, 108, 108))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Concept> comboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
