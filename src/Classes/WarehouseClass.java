/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Main.MysqlConnect;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import static javax.swing.UIManager.getString;
import javax.swing.table.DefaultTableModel;
import Models.Product;

/**
 *
 * @author Karolis
 */
public class WarehouseClass {
    
    /**
     *
     * @param id
     * @return
     */
    public static double getBalance(int id){
        double balance_left = 0;
        
        MysqlConnect mysqlConnect = new MysqlConnect();
        try {
            Statement st = mysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_balance_products WHERE balance_left > 0 AND id_product ='"+id+"' ");
          
            while (res.next()) {

                balance_left = balance_left+res.getDouble("balance_left");
  
            }

        } catch (SQLException e) {
        } finally {
            mysqlConnect.disconnect();
             
        }
         
        return balance_left;
    }

    public static List<Product> getProduct(int id){
        
    List<Product> products = new ArrayList<>();
        
        MysqlConnect mysqlConnect = new MysqlConnect();
        try {
            Statement st = mysqlConnect.connect().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  dm_balance_products WHERE id_product = '"+id+"'");
            while(res.next()) {
            Product product = new Product();
            product.setID(res.getInt("id"));
            product.setBalance(res.getDouble("balance"));
            product.setBalance_left(res.getDouble("balance_left"));
            product.setInvoice(res.getString("invoice"));
            product.setNote(res.getString("note"));
            product.setDate(res.getDate("data"));
            products.add(product);
            }
            
        } catch (SQLException e) {
        } finally {
            mysqlConnect.disconnect();
             
        }
       return products;
    }

    
}
