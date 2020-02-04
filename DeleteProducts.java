/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dell
 */
public class DeleteProducts extends JInternalFrame{
    private JTextField product_name,dp_name,dc_name,dm_name,dt_name,dp_hsn_code,product_gst;
    private JButton close,delete,reset;
    private String[] dpstr,dpstr_id;
    private String sid;
    private ResultSet rset=null;
    ArrayList<String> product_id=new ArrayList<>();
    ArrayList<String> product_items=new ArrayList<>();
    public DeleteProducts(){
        super("Remove Product",true,true);
        setSize(700,470);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DeleteProducts.EXIT_ON_CLOSE);
        this.setLocation(350,60);
        
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.DELETE_PRODUCT=false;
              }});
        
        JLabel title=new JLabel("REMOVE PRODUCT",JLabel.CENTER);
        title.setFont(StaticMember.HEAD_W_FONT);
        title.setBackground(StaticMember.HEAD_BG_COLOR1);
        title.setOpaque(true);
        title.setForeground(StaticMember.HEAD_FG_COLOR1);
        this.add(title,BorderLayout.NORTH);
       //product_name,dp_name,dc_name,dm_name,dt_name,dp_hsn_code,product_gst;
        dp_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter New Product Name"," PRODUCT NAME");
        dp_hsn_code = StaticMember.MyInputBox("",8,StaticMember.INTEGER_TYPE,false," Enter HSN Code"," HSN CODE");
        dc_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Company Name"," COMPANY NAME");
        dt_name = StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Enter Product Type"," PRODUCTS TYPE");
        product_gst = StaticMember.MyInputBox("",12,StaticMember.STRING_TYPE,false," Enter Product GST %"," GST %");
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        delete=StaticMember.MyButton("DELETE","Click On Button To Delete The Record");
        product_name= StaticMember.MyInputBox("",35,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Product Name"," SELECT PRODUCT NAME");
        StaticMember.setToProducts(product_name, product_id, product_items);
        
        //add on panel
        JPanel mainPanel=new JPanel(new BorderLayout()); 
        JPanel mainGridPanel=new JPanel(new GridLayout(6,1,10,10));
        JPanel companyPanel=new JPanel(new BorderLayout());
        JPanel typePanel=new JPanel(new BorderLayout());
        JPanel hsn_gstPanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel bPanel=new JPanel(new GridLayout(1,5,20,20));
        this.add(mainPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("   "),BorderLayout.NORTH);
        mainPanel.add(new JLabel("     "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(new JLabel("   "),BorderLayout.SOUTH);mainPanel.add(mainGridPanel,BorderLayout.CENTER);
        mainGridPanel.add(product_name);mainGridPanel.add(dp_name);mainGridPanel.add(dc_name);
        mainGridPanel.add(dt_name);mainGridPanel.add(hsn_gstPanel);mainGridPanel.add(bPanel);
        hsn_gstPanel.add(dp_hsn_code);hsn_gstPanel.add(product_gst);
        bPanel.add(new JLabel(" "));bPanel.add(new JLabel(" "));bPanel.add(delete);bPanel.add(reset);bPanel.add(close);
        
        product_name.addKeyListener(new KeyAdapter(){
                public void keyTyped(KeyEvent e) {   
                if(e.getKeyChar()=='\n')  
                {
                    try
                    {
                        ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+product_id.get(product_items.indexOf(product_name.getText()))+"'");
                        pr.next();
                        dp_name.setText(pr.getString("product_name"));
                        dp_hsn_code.setText(pr.getString("product_hsn_code"));
                        ResultSet r=StaticMember.con.createStatement().executeQuery("select * from company where company_id like'"+pr.getString("company_id")+"'");
                        r.next();
                        dc_name.setText(r.getString("company_name"));
                        ResultSet rt=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+pr.getString("type_id")+"'");
                        rt.next();
                        dt_name.setText(rt.getString("type_name"));
                        product_gst.setText((pr.getString("product_gst")+"%"));
                        sid=pr.getString("product_id");
                    }catch(SQLException ex)
                    {

                    }
                        delete.requestFocusInWindow();
                    }
                    }});
        
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToDelete();
            }});
        delete.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {   
            if(e.getKeyChar()=='\n')  codeToDelete();
            }});
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.DELETE_PRODUCT=false;
                DeleteProducts.this.dispose();
            }});
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {   
                if(e.getKeyChar()=='\n') {
                   StaticMember.DELETE_PRODUCT=false;
                    DeleteProducts.this.dispose();
            }}});
    
      
    } 
    
    //code to Delete
    private void codeToDelete()
    {
        try
        {
          PreparedStatement stmt=StaticMember.con.prepareStatement("delete from products where product_id=?");
          stmt.setString(1, sid);         
          stmt.executeUpdate();
          JOptionPane.showMessageDialog(this, "Record Delete Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
          dreset();
          product_name.requestFocusInWindow();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
     public void dreset()
    {
        dp_name.setText("");
        dp_hsn_code.setText("");
        dc_name.setText("");
        dt_name.setText("");
        product_name.setText("");
        product_gst.setText("");
    }
}
