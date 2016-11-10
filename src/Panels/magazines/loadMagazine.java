/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels.magazines;

 
import Classes.EXhelper;
import Main.MysqlConnect;
import Models.Magazine;
 
 
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.jdesktop.swingx.table.DatePickerCellEditor;
 

/**
 *
 * @author Karolis
 */
public class loadMagazine extends javax.swing.JPanel {
    private int id_magazine;
    /**
     * Creates new form loadMagazine
     */
    public loadMagazine(int id) {
 
        initComponents();
        id_magazine = id;
        loadProducts();
        
        TableColumnModel tcm = jTable1.getColumnModel();

        tcm.getColumn(0).setWidth(0);
        tcm.getColumn(0).setMinWidth(0);
        tcm.getColumn(0).setMaxWidth(0); 
        
        tcm.getColumn(1).setPreferredWidth(200);
        tcm.getColumn(2).setPreferredWidth(150);
        tcm.getColumn(3).setPreferredWidth(100);
        
         
    
        jTable1.getColumnModel().getColumn(2).setCellRenderer(new EXhelper.DecimalFormatRenderer());
        
        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                if( value instanceof Date) {
                    value = f.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
            }
        };
          
        
        jTable3.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        jTable3.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
               
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        TableColumnModel tcm3 = jTable3.getColumnModel();
        tcm3.getColumn(0).setPreferredWidth(220);
        tcm3.getColumn(1).setPreferredWidth(80);
        tcm3.getColumn(2).setPreferredWidth(50);
        tcm3.getColumn(3).setPreferredWidth(80);
        tcm3.getColumn(4).setPreferredWidth(50);
        tcm3.getColumn(5).setPreferredWidth(70);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DatePickerCellEditor datetime = new DatePickerCellEditor(format);
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
       // datetime.setFormats(dateFormat);
       jTable3.getColumnModel().getColumn(1).setCellEditor(datetime);
       jTable3.getColumnModel().getColumn(3).setCellEditor(datetime);
       
        
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("");
        comboBox.addItem("08:00");
        comboBox.addItem("09:00");
        comboBox.addItem("10:00");
        comboBox.addItem("11:00");
        comboBox.addItem("12:00");
        comboBox.addItem("13:00");
        comboBox.addItem("14:00");
        comboBox.addItem("15:00");
        comboBox.addItem("16:00");
        comboBox.addItem("17:00");
        comboBox.addItem("18:00");
     
        
        jTable3.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
        jTable3.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox));
        
        try{
        
        Statement st = MysqlConnect.connect().createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM  dm_magazine WHERE id = '"+id_magazine+"' ");
        
        while (res.next()) {
            String su  = res.getString("su");
            String[] suEX = su.split(",");
             
     
            Object[] row = {"Sūdymas, marinavimas/Faršo maišymas", reSp(suEX[0]), suEX[1], reSp(suEX[2]), suEX[3], suEX[3]};
            Object[] row2 = {"Dešrų kimšimas/Brandinimas", "", "", "", "", ""};
            Object[] row3 = {"Rūkinimas/Vytinimas", "", "", "", "", ""};
            Object[] row4 = {"Virimas", "", "", "", "", ""};
            Object[] row5 = {"Atvėsinimas", "", "", "", "", ""};
            Object[] row6 = {"Formavimas", "", "", "", "", ""};

            model.addRow(row);
            model.addRow(row2);
            model.addRow(row3);
            model.addRow(row4);
            model.addRow(row5);
            model.addRow(row6);
        } 
        } catch (SQLException e){
                    e.printStackTrace();
        }
        
 
        
        //listener
        
        jTable3.getModel().addTableModelListener(new TableModelListener() {
            
          public void tableChanged(TableModelEvent e) {
            int index  = jTable3.getSelectedRow();
            
            if(index != -1){
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
                String begin_date = "";
                String end_date = "";
            
                if(isValidDate(""+model.getValueAt(index, 1)+"")){
                    Date begin_date_n = (Date) model.getValueAt(index, 1);
                    begin_date = f.format(begin_date_n);
                }
                if(isValidDate(""+model.getValueAt(index, 3)+"")){
                    Date end_date_n = (Date) model.getValueAt(index, 3);
                    end_date = f.format(end_date_n);
                }    
                String begin_time = (String) model.getValueAt(index, 2);
                String end_time = (String) model.getValueAt(index, 4); 
                String temp = (String) model.getValueAt(index, 5); 
                
                System.out.println(begin_date+" "+begin_time);
                
            }
          }
          
        }); 
        
        
    }
    
 
    private static String reSp(String str){
        return str.replaceAll("\\s", "");
    }
    
public static boolean isValidDate(String date) 
{
        try {
            DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
}

    private void loadProducts(){
        Magazine products = new Magazine(id_magazine);
        List<Magazine> result = products.getProducts();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        double total = 0;
        for(Magazine p : result) {
            
           
            
            Object[] row = {p.getIdProduct(), p.getProductName(), p.getQuantyti(), p.getInvoice()};
            model.addRow(row);
            total = total+p.getQuantyti();
        }
        
 
         
        try{
            Date date = new SimpleDateFormat("yyyy-mm-DD").parse(products.getDate());
            String newdate  = new SimpleDateFormat("mm.DD").format(date);
            
            jLabel6.setText("<html>Partijos Nr.: <b>"+newdate+"</b></html>");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        jLabel2.setText("<html>Gaminio pavadinimas: <b>"+products.getRecipeName()+"</b></html>"); 
        jLabel4.setText("<html>Data: <b>"+products.getDate()+"</b></html>"); 
        jLabel5.setText("<html>Viso: <b>"+total+"</b></html>");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#id", "Žaliavos pavadinimai", "Paimtas kiekis gamybai, kg", "Žaliavos kodas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gamybos žurnalas");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Gaminio pavadinimas");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Receptūros Nr.");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Viso:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Data");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Partijos Nr.");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Gamyba", "Pradžia Data", "Laikas", "Pabaiga Data", "Laikas", "Teperatūra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
