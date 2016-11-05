/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
 
import Main.MysqlConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Karolis
 */
public class Magazine {
    private int id; 
    private Statement st;
    private int id_product;
    private double quantyti;
    private String product_name;
    private String invoice;
    
    public Magazine() {
  
    }
    
    public Magazine(int ids) {
        try {
            this.st = MysqlConnect.connect().createStatement();
            id = ids;
        } catch (SQLException ex) {

        }
    }
    
        public void setIdProduct(int id_prod) {
        id_product = id_prod; 
    }
    
    public int getIdProduct() {
        return id_product; 
    } 
    
    public void setQuantyti(double quant) {
        quantyti = quant; 
    }
    
    public double getQuantyti() {
        return quantyti; 
    }  

    public void setProductName(String p_name) {
        product_name = p_name; 
    }
    
    public String getProductName() {
        return product_name; 
    } 

    public void setInvoice(String g_invoice) {
        invoice = g_invoice; 
    }
    
    public String getInvoice() {
        return invoice; 
    } 
    
    public List<Magazine> getProducts(){
        
    List<Magazine> r_products = new ArrayList<>();
 
        try {
            ResultSet res = st.executeQuery("SELECT * FROM  dm_magazine_products AS mp "
                    + "LEFT JOIN dm_balance AS b ON (b.id=mp.id_product) "
                    + "WHERE mp.id_magazine = '"+id+"' GROUP BY mp.id_product");
            while(res.next()) {
                Magazine r_product = new Magazine();
                r_product.setIdProduct(res.getInt("id_product"));
                r_product.setQuantyti(res.getDouble("quantyti"));
                r_product.setProductName(res.getString("name"));
                r_product.setInvoice(res.getString("invoice"));
                r_products.add(r_product);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 
       return r_products;
    }
    
 
    
}
