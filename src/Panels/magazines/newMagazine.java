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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Karolis
 */
public class newMagazine extends javax.swing.JPanel {

    /**
     * Creates new form newMagazine
     */
    public newMagazine() {
        initComponents();
        recipes();
        final JComboBox<Concept> comboBox = getComboBox();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JTextField jTextField1 = new JTextField();
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

    private void launchGui() {
        final JFrame frame = new JFrame("Stack Overflow: custom combo box renderer");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final JComboBox<Concept> comboBox = getComboBox();
        final JLabel label = new JLabel("Please make a selection...");

        comboBox.addActionListener(actionEvent -> {
            final Object selectedItem = comboBox.getSelectedItem();
            if (selectedItem instanceof Concept)
                label.setText(((Concept) selectedItem).getValue());
        });

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(comboBox, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }
    
    private JComboBox<Concept> getComboBox() {
        final List<Concept> concepts = Arrays.asList(new Concept("label 1", "value 1"),
                                                     new Concept("label 2", "value 2"),
                                                     new Concept("label 3", "value 3"));

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
