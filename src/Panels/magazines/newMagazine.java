/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels.magazines;
 
import Classes.Concept;
import Classes.EXhelper;
import Classes.WarehouseClass;
import Classes.WarehouseClass;
import Main.MysqlConnect;
import Models.Recipe;
import Panels.magazine;
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private JButton jButton1 = new JButton();
     
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
        jButton1.setEnabled(true);
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
        
        jButton1.setText("Prašome palaukti...");
        jButton1.setEnabled(false);
        
        final Object selectedItem = comboBox.getSelectedItem();
        double kg_need = Double.parseDouble(jTextField1.getText());
        int id_recipe = Integer.valueOf(((Concept) selectedItem).getValue());
        int i = 0;
        int p = 0;
        String [] erorrs = new String[0];
        String errorsString = "";
        
        Recipe recip = new Recipe(id_recipe);
        List<Recipe> result = recip.getRecipeProducts();
        int recipeItemCount = result.size();
        
        
        if(recipeItemCount == 0){
                JOptionPane.showMessageDialog (null, "<html>Receptas neturi produktų.<html>", "Klaida", JOptionPane.INFORMATION_MESSAGE);
                jButton1.setText("Sukurti");
                jButton1.setEnabled(true);
        }
        
 
        for(Recipe rr : result) {
            double need_total = 0;
      
            
            double balance_left = WarehouseClass.getBalance(rr.getIdProduct());
            need_total = EXhelper.round((kg_need*rr.getQuantyti()), 3);
  
            if(need_total > balance_left){
                erorrs = Arrays.copyOf(erorrs, erorrs.length +1);
                erorrs[erorrs.length - 1] = rr.getProductName()+" trukumas: "+EXhelper.round((need_total-balance_left), 3)+"";

            }
        }
        
            if(erorrs.length > 0){
                
                for(int e=0; e< erorrs.length; e++){
                    errorsString = errorsString+"<br>"+erorrs[e]; 
                }
               
                JOptionPane.showMessageDialog (null, "<html>"+errorsString+"<html>", "Klaida", JOptionPane.INFORMATION_MESSAGE);
                jButton1.setText("Sukurti");
                jButton1.setEnabled(true);
                
            }else{
                //if have all needed products
                //create row in databse
                try{
                
                double currentBalanceDbl = Double.parseDouble(recip.getRecipeInfo("output_proc").replaceAll(" ","").replaceAll(",","."));
                     
                Statement st = MysqlConnect.connect().createStatement(); 
                Statement st2 = MysqlConnect.connect().createStatement();
                st.executeUpdate("INSERT INTO dm_magazine (id_recipe,kg,date,output_proc,su,de,ru,vi,at,fo) VALUES ('"+id_recipe+"', '"+kg_need+"', '"+LocalDate.now()+"', '"+currentBalanceDbl+"', ' , , , , ', ' , , , , ', ' , , , , ', ' , , , , ', ' , , , , ', ' , , , , ')", Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = st.getGeneratedKeys();
                int last_id = 0;
                if (rs.next()) {
                    last_id = rs.getInt(1);
                }

                for(Recipe rr : result) {
                    p++;
                    double need_total = 0;
                    int need_more = 1;

                    double balance_left = WarehouseClass.getBalance(rr.getIdProduct());
                    need_total = EXhelper.round((kg_need*rr.getQuantyti()), 3);
                    ResultSet res = st2.executeQuery("SELECT * FROM  dm_balance_products WHERE balance_left > 0 AND id_product ='"+rr.getIdProduct()+"' ORDER BY data ASC");

                    while (res.next()) {
                        if(need_more == 1){
                            balance_left = res.getDouble("balance_left");
                            int id_balance_product = res.getInt("id");
                            String invoice = res.getString("invoice");
                            
                            if(balance_left > need_total){
                                need_more = 0;
                                st.executeUpdate("INSERT INTO dm_magazine_products (id_magazine,id_product,id_balance_product,quantyti,invoice) VALUES ('"+last_id+"', '"+rr.getIdProduct()+"', '"+id_balance_product+"', '"+need_total+"', '"+invoice+"')");
                                st.executeUpdate("UPDATE dm_balance_products SET balance_left='"+(balance_left-need_total)+"' where id='"+id_balance_product+"'");  
                            }else{
 
                                st.executeUpdate("INSERT INTO dm_magazine_products (id_magazine,id_product,id_balance_product,quantyti,invoice) VALUES ('"+last_id+"', '"+rr.getIdProduct()+"', '"+id_balance_product+"', '"+balance_left+"', '"+invoice+"')");
                                st.executeUpdate("UPDATE dm_balance_products SET balance_left='0' where id='"+id_balance_product+"'");     
                            
                                need_total = need_total-balance_left;
                            }
   
                        }
                    }     
                    
                    if(p == recipeItemCount){
                        //if done load magazine tab
                        magazine.refresh(0, last_id);
                    }
                    
                }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }       
    }
    
    private void recipes(){
 
        
        try {         
            Statement st = MysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_recipe");

            while (res.next()) {
                String id = res.getString("id");
                String name  = res.getString("name");
                String note  = res.getString("note");
              
              
            }  
        
        } catch (SQLException e) {
        } finally {
           // MysqlConnect.disconnect();
             
        }
 
    }
    
    private JComboBox<Concept> getComboBox() {
        
        
        
        
        try {         
            Statement st = MysqlConnect.connect().createStatement();
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
            //MysqlConnect.disconnect();
             
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
        jLabel1.setText("Naujas gamybos žurnalas");

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
