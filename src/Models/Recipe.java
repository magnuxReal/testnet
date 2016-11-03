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
public class Recipe {
    int id;
    int id_product;
    double quantyti;
     
    private Statement st;   
    
    public Recipe(){
        try {
            this.st = MysqlConnect.connect().createStatement();
        } catch (SQLException ex) {

        }
    }
    
    public Recipe(int ids){
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
     
    public List<Recipe> getRecipeProducts(){
        
    List<Recipe> r_products = new ArrayList<>();

        try {
            ResultSet res = st.executeQuery("SELECT * FROM  dm_recipe_product WHERE id_precipe = '"+id+"'");
            while(res.next()) {
            Recipe r_product = new Recipe();
            r_product.setIdProduct(res.getInt("id_product"));
            r_product.setQuantyti(res.getDouble("quantyti"));
            r_products.add(r_product);
            }
            
        } catch (SQLException e) {
            //MysqlConnect.disconnect();
        } 
       return r_products;
    }
    
}
