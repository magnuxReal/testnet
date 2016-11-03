/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Classes.DoublesCellEditor;
import Classes.EXhelper;
import Classes.WarehouseClass;
import Main.MysqlConnect;
import Models.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Karolis
 */
public class recipeProducts extends javax.swing.JPanel {
    private int id_recipe;
 
    /**
     * Creates new form recipeProducts
     */
    public recipeProducts(int id, String rname, String rnote) {
        id_recipe = id;
        initComponents();
        TableColumnModel tcm1 = jTable1.getColumnModel();
        tcm1.getColumn(0).setPreferredWidth(30);    
        tcm1.getColumn(1).setPreferredWidth(100);
        
        TableColumnModel tcm2 = jTable2.getColumnModel();
        tcm2.getColumn(0).setPreferredWidth(30);    
        tcm2.getColumn(1).setPreferredWidth(100);
        
        TableColumnModel tcm3 = jTable3.getColumnModel();
        tcm3.getColumn(0).setPreferredWidth(20);    
        tcm3.getColumn(1).setPreferredWidth(120);
        tcm3.getColumn(2).setPreferredWidth(70);
        
        jTable3.getColumnModel().getColumn(2).setCellRenderer(new EXhelper.DecimalFormatRenderer4()); 
        DecimalFormat df = new DecimalFormat ("0.0000");
        JFormattedTextField fmtTxtField = new JFormattedTextField(df);
        fmtTxtField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTable3.setDefaultEditor(Double.class, new DoublesCellEditor(fmtTxtField)); 
        
        
        jTable3.getModel().addTableModelListener(new TableModelListener() {
            
          public void tableChanged(TableModelEvent e) {
            int index  = jTable3.getSelectedRow();
            
            if(index != -1){
                DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
                int id_recipe_product = (int) model.getValueAt(index, 0);
                double recipe_cuantity = (double) model.getValueAt(index, 2);
                updateRecipeProduct(id_recipe_product, recipe_cuantity);
            }
          }
          
        });
        jLabel2.setText(rname);
        jLabel3.setText(rnote);
        jButton1.setVisible(false);
        jLabel1.setVisible(false);
        load_products();
    }
    
    private void updateRecipeProduct(int id_recipe_product, double recipe_cuantity){
        try {
            Statement st = MysqlConnect.connect().createStatement(); 
            if(id_recipe_product > 0 && recipe_cuantity > 0){
                st.executeUpdate("UPDATE dm_recipe_product SET quantyti='"+recipe_cuantity+"' WHERE id='"+id_recipe_product+"' ");
            }
  
        } catch (SQLException e) {
        } finally {
            //mysqlConnect.disconnect();      
        }  
    }

    private void load_products(){
        
        try {
            Statement st = MysqlConnect.connect().createStatement();
            Statement st2 = MysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_balance");
                        ResultSet res_recipe = st2.executeQuery("SELECT rp.id, rp.quantyti, rp.id_precipe, b.name "
                    + "FROM  dm_recipe_product AS rp "
                    + "LEFT JOIN dm_balance AS b ON (b.id=rp.id_product) "
                    + "WHERE rp.id_precipe='"+id_recipe+"' ORDER BY b.type ASC, b.id ASC");
  /*
                        ResultSet res_recipe = st2.executeQuery("SELECT rp.id, rp.quantyti, b.name "
                    + "FROM  dm_recipe_product AS rp "
                    + "LEFT JOIN dm_balance AS b ON(b.id=rp.id_product)"
                    + "WHERE rp.id_precipe='"+id_recipe+"'");
            */
                      
            DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
            DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
            DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
            
            while (model1.getRowCount() > 0) {
                    model1.removeRow(0);
            }
            
            while (model2.getRowCount() > 0) {
                    model2.removeRow(0);
            } 
            
            while (model3.getRowCount() > 0) {
                    model3.removeRow(0);
            }           
            while (res.next()) {
        
                int type = res.getInt("type");
                int id = res.getInt("id");
    
                String name  = res.getString("name");
                 
                if(type == 1){
                    Object[] row = {id, name};
                    model1.addRow(row);
                }else{
                    Object[] row = {id, name};
                    model2.addRow(row);          
                }
              
            }  

            while (res_recipe.next()) {
                 
                int id = res_recipe.getInt("id");
       
                double quantyti = res_recipe.getDouble("quantyti");
                String name  = res_recipe.getString("name");
                 
                    Object[] row = {id, name, quantyti};
                    model3.addRow(row);
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           // MysqlConnect.disconnect();
             
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mesos tipas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Prieskoniai"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("<<< Pridėti");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mėsa/Prieskonis", "Kiekis"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel1.setText("Produktas");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Receptas");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Pastaba");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(97, 97, 97))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int index  = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String name = (String) model.getValueAt(index, 1);
        
        jTable2.getSelectionModel().clearSelection();
        jButton1.setVisible(true);
        jLabel1.setVisible(true);
        jLabel1.setText(name);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int index  = jTable2.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        String name = (String) model.getValueAt(index, 1);
        
        jTable1.getSelectionModel().clearSelection();
        jButton1.setVisible(true);
        jLabel1.setVisible(true);
        jLabel1.setText(name);
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        int index1  = jTable1.getSelectedRow();
        int index2  = jTable2.getSelectedRow();
        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        int current_index = 0;
        
        if(index1 != -1){
            current_index = (int) model1.getValueAt(index1, 0);
        }
        
        if(index2 != -1){
            current_index = (int) model2.getValueAt(index2, 0);
        }  
        
        if(current_index > 0){
          
            try {
                Statement st = MysqlConnect.connect().createStatement(); 

                st.executeUpdate("INSERT INTO dm_recipe_product (id_precipe, id_product, quantyti) VALUES ('"+id_recipe+"', '"+current_index+"', 0)");

            } catch (SQLException e) {
            } finally {
                //mysqlConnect.disconnect();
                load_products();
                jButton1.setVisible(false);
                jLabel1.setVisible(false);
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
