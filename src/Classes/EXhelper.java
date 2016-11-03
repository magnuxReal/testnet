/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

 
public class EXhelper {
    //create adittional number formating 0.000
    public static class DecimalFormatRenderer extends DefaultTableCellRenderer {
      private static final DecimalFormat formatter = new DecimalFormat( "0.000" );
 
      public Component getTableCellRendererComponent(
         JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column) {
 
         // First format the cell value as required
 
         value = formatter.format((Number)value);
 
            // And pass it on to parent class
 
         return super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column );
      }
   }
    
    public static class DecimalFormatRenderer4 extends DefaultTableCellRenderer {
      private static final DecimalFormat formatter = new DecimalFormat( "#0.0000" );
 
      public Component getTableCellRendererComponent(
         JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column) {
 
         // First format the cell value as required
 
         value = formatter.format((Number)value);
 
            // And pass it on to parent class
 
         return super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column );
      }
   }
    
    //generate update fields for mysql UPDATE function
    public static String uConstruct(Map<String, String> map){
 
       List<String> sqlList = new ArrayList<String>();
        
       for(String key: map.keySet()){
           if(map.get(key) != null && !map.get(key).equals("0.0")){
               System.out.println(""+key+"='"+map.get(key)+"'");
                sqlList.add(""+key+"='"+map.get(key)+"'");
           }
       }
       
       String listString = sqlList.stream().map(Object::toString).collect(Collectors.joining(", "));
       
        return listString;
        
    }

    public static double noComma(String dd){
        String numb = String.valueOf(dd);
        return Double.parseDouble(numb.replaceAll(",", "."));
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
}
