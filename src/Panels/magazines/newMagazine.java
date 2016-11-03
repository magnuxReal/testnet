/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels.magazines;
 
import Classes.Concept;
import Classes.WarehouseClass;
import Classes.WarehouseClass;
import Main.MysqlConnect;
import Models.Recipe;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Karolis
 */
public class newMagazine extends javax.swing.JPanel {
    Concept[] arr = {};
    final JComboBox<Concept> comboBox;
    JTextField jTextField1;
    /**
     * Creates new form newMagazine
     */
    public newMagazine() {
        initComponents();
        recipes();
        comboBox = getComboBox();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        jTextField1 = new JTextField();
        comboBox.setPreferredSize(new java.awt.Dimension(150, 30));
        jTextField1.setPreferredSize(new java.awt.Dimension(150, 30));
         
        JButton jButton1 = new JButton();
        jButton1.setPreferredSize(new java.awt.Dimension(150, 30));
        jLabel2.setText("Receptas");
        jPanel1.add(jLabel2, gridBagConstraints);
        
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        
        jPanel1.add(comboBox, gridBagConstraints);
        
        jLabel3.setText("Kiekis (Kg)");
         
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel3, gridBagConstraints);

        jTextField1.setText("1");
     
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jTextField1, gridBagConstraints);
        
        jButton1.setText("Sukurti");
 
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jButton1, gridBagConstraints);
  /*      
        comboBox.addActionListener(actionEvent -> {
            final Object selectedItem = comboBox.getSelectedItem();
            if (selectedItem instanceof Concept)
                System.out.println(((Concept) selectedItem).getValue());
        });
*/
        jButton1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
               tryAddNewMagazine();
            }
        });
        
    }
    
    private void tryAddNewMagazine(){
        final Object selectedItem = comboBox.getSelectedItem();
        double kg_need = Double.parseDouble(jTextField1.getText());
        int id_recipe = Integer.valueOf(((Concept) selectedItem).getValue());
        int i = 0;
        String [] erorrs = new String[0];
        String errorsString = "";
        
        Recipe recip = new Recipe(id_recipe);
        List<Recipe> result = recip.getRecipeProducts();
        
        for(Recipe rr : result) {
            double need_total = 0;
      
            
            double balance_left = WarehouseClass.getBalance(rr.getIdProduct());
            need_total = kg_need*rr.getQuantyti();
            
            if(need_total > balance_left){
                erorrs = Arrays.copyOf(erorrs, erorrs.length +1);
                erorrs[erorrs.length - 1] = rr.getIdProduct()+"- Trukumas: "+need_total+" > "+balance_left+"";

                 
            }
        }
        
            if(erorrs.length > 0){
                for(int e=0; e< erorrs.length; e++){
                    errorsString = errorsString+"<br>"+erorrs[e]; 
                    System.out.println(erorrs[e]);
                }
            }     
        
        //JOptionPane.showMessageDialog (null, "<html>"+errorsString+"<html>", "Klaida", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    private void recipes(){
 
        MysqlConnect mysqlConnect = new MysqlConnect();
        try {         
            Statement st = mysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_recipe");

            while (res.next()) {
                String id = res.getString("id");
                String name  = res.getString("name");
                String note  = res.getString("note");
              
              
            }  
        
        } catch (SQLException e) {
        } finally {
            mysqlConnect.disconnect();
             
        }
 
    }
    
    private JComboBox<Concept> getComboBox() {
        
        
        
        MysqlConnect mysqlConnect = new MysqlConnect();
        try {         
            Statement st = mysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_recipe ORDER BY id DESC");
            int i = 0;
            while (res.next()) {
                String id = res.getString("id");
                String name  = res.getString("name");
                String note  = res.getString("note");
          
                add_element(new Concept(name, id));
    
                i++;
              
            }  
        
        } catch (SQLException e) {
        } finally {
            mysqlConnect.disconnect();
             
        }
         
        
        final List<Concept> concepts = Arrays.asList(arr);

        final JComboBox<Concept> comboBox = new JComboBox<>(new Vector<>(concepts));
         
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
        
        return comboBox;
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gamybos žurnalas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
