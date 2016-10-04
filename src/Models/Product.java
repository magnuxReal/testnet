/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Main.MysqlConnect;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            System.out.println(note);
            Statement st = mysqlConnect.connect().createStatement();   
            st.executeUpdate("UPDATE dm_balance_products SET note ='"+note+"', invoice='"+invoice+"' where id='"+id+"'");

        } catch (SQLException e) {
        } finally {
            mysqlConnect.disconnect();      
        }
        
    }
    
}
