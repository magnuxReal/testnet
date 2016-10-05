/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Classes.EXhelper;
import Main.MysqlConnect;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Karolis
 */
public class Product {
    int id;
    double balance;
    double balance_left;
    String invoice;
    String note;
    Date date;
    
    public Product(int ids) {
        id = ids;
    }
 
    public Product() {

    }
    
    public void setBalance(double string) {
        balance = string;
       
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance_left(double aDouble) {
        balance_left = aDouble;
    }
    
    public double getBalance_left() {
        return balance_left;
    }

    public void setInvoice(String string) {
        invoice = string;
    }

    public String getInvoice() {
        return invoice;
    }
    
    public void setNote(String string) {
        note = string;
    } 
    
    public String getNote() {
        return note;
    }

    public void setDate(Date adate) {
        date = adate;
    }
        
    public Date getDate() {
        return date;
    }

    public int getID() {
        return id;
    }    

    public void setID(int ida) {
        id = ida;
    }
    
    public void update(){

       MysqlConnect mysqlConnect = new MysqlConnect();
        try {

            Map<String, String> arr = new HashMap<String, String>();
            
            //convert double to string
            String balance_s = String.valueOf(balance);
            String balance_left_s = String.valueOf(balance_left);
            
             
            // exception for date
            if(date != null){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String date_s = dateFormat.format(date);
                arr.put("data", date_s);
            }
            
            //add values to constructor
            arr.put("balance", balance_s);
            arr.put("balance_left", balance_left_s);
            
            arr.put("note", note);
            arr.put("invoice", invoice);
            
            //generate update fields in nonstructor class
            String listString = EXhelper.uConstruct(arr);
      
            Statement st = mysqlConnect.connect().createStatement();   
            st.executeUpdate("UPDATE dm_balance_products SET "+listString+" where id='"+id+"'");

        } catch (SQLException e) {
        } finally {
            mysqlConnect.disconnect();      
        }
        
    }
    
}
