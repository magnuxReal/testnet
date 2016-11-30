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
import java.util.Date;
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
    private String date;
    private String recipe_name;
    private double output_proc;
    private double total_made;  
    private Date date_to;
    private String person; 
    
    public Magazine() {
  
    }
    
    public Magazine(int ids) {
        try {
            this.st = MysqlConnect.connect().createStatement();
            id = ids;
            
            ResultSet res = st.executeQuery("SELECT * FROM  dm_magazine AS m "
                    + "LEFT JOIN dm_recipe AS r ON (r.id=m.id_recipe) "
                    + "WHERE m.id = '"+id+"'");
            while(res.next()) {
                date = res.getString("date");
                recipe_name = res.getString("name");
                output_proc = res.getDouble("output_proc");
                total_made = res.getDouble("total_made");
                
                date_to = res.getDate("date_to");
                
                person = res.getString("person");
            }
            
        } catch (SQLException ex) {

        }
    }
    
        public void setIdProduct(int id_prod) {
        id_product = id_prod; 
    }
    
    public int getIdProduct() {
        return id_product; 
    } 

    public Date getDate_to() {
        return date_to; 
    } 
    
    public String getPerson() {
        return person; 
    } 
    
    public double getOutput_proc() {
        return output_proc; 
    } 
    
    public double getTotal_made() {
        return total_made; 
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

    public void setRecipeName(String r_name) {
        recipe_name = r_name; 
    }
    
    public String getRecipeName() {
        return recipe_name; 
    } 
    
    public void setDate(String p_date) {
        date = p_date; 
    }
    
    public String getDate() {
        return date; 
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
                    + "WHERE mp.id_magazine = '"+id+"'");
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
