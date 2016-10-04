/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;

/**
 *
 * @author Karolis
 */
public class Product {
    double balance;
    double balance_left;
    String invoice;
    String note;
    Date date;

    void setBalance(double string) {
        balance = string;
       
    }

    public double getBalance() {
        return balance;
    }

    void setBalance_left(double aDouble) {
        balance_left = aDouble;
    }
    
    public double getBalance_left() {
        return balance_left;
    }

    void setInvoice(String string) {
        invoice = string;
    }

    public String getInvoice() {
        return invoice;
    }
    
    void setNote(String string) {
        note = string;
    } 
    
    public String getNote() {
        return note;
    }

    void setDate(Date adate) {
        date = adate;
    }
        
    public Date getDate() {
        return date;
    }
    
}
