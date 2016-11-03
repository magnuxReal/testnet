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
    int id;
    
    private Statement st;
  

    public Magazine(int ids) {
        try {
            this.st = MysqlConnect.connect().createStatement();
            id = ids;
        } catch (SQLException ex) {

        }
    }

    public Magazine() {
        try {
            this.st = MysqlConnect.connect().createStatement();
        } catch (SQLException ex) {

        }  
    }
    
}
