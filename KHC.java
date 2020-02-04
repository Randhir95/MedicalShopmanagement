/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Intel
 */
public class KHC {
    static AddNewProducts pro_obj;
   
   public static String bill="3",b="lkdhyud5462233";
   public static String [] data,data1;
   public static String s1="2018-04-01",s2="2018-07-01";
   public static int id=16;
  
    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) throws SQLException {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            StaticMember.con=DriverManager.getConnection("jdbc:mysql://localhost/komalhealthcareupdatenew","root","");
            
        }catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "OOPs000!",JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "OOPs0!",JOptionPane.ERROR_MESSAGE);   
        }
         //Create a Thread and set MDI Form
        try
        {
        SwingUtilities.invokeAndWait(new Runnable(){
        public void run()
        {
           //new Stock(data).setVisible(true);
           
            new MDIMainWindow().setVisible(true);
            
        }
        
        });
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString(), "OOPs2!",JOptionPane.ERROR_MESSAGE);              
        }
    }
    }
    

