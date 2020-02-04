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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.Clock.system;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import static komalhealthcare.MDIMainWindow.desktop;

/**
 *
 * @author Intel
 */
public class UpdateProduct extends JInternalFrame{
    private JButton update,ucButton,utButton,uclose,ureset;
    private JTextField upro_name,uproduct_hsn_code,company_name,type_name,product_name;
    private JComboBox gst_list;
    private String[] ucstr,utstr,ucstr_id,utstr_id,upstr,upstr_id;
    private String sid;
    private ResultSet rset=null,prset=null;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    ArrayList<String> type_id=new ArrayList<>();
    ArrayList<String> type_items=new ArrayList<>(); 
    ArrayList<String> product_id=new ArrayList<>();
    ArrayList<String> product_items=new ArrayList<>();
    
    public UpdateProduct()
    {
        super("MODIFY PRODUCT",true,true);
        this.setSize(700,470);
        this.setDefaultCloseOperation(UpdateProduct.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(350, 60);
        
        gst_list=StaticMember.MyComboBox(StaticMember.gst_item,"Select GST Persentage"," GST %");
        ucButton=StaticMember.AddButton("/images/plus.png", 50, 50, "Click on button To Add More Company");
        utButton=StaticMember.AddButton("/images/plus.png", 50, 50, "Click On Button To Add More Product Type ");
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.UPDATE_PRODUCT=false;
                    
              }});
        
        JLabel h=new JLabel("MODIFY PRODUCT",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        upro_name = StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter New Product Name"," PRODUCT NAME");
        uproduct_hsn_code = StaticMember.MyInputBox("",8,StaticMember.INTEGER_TYPE," Enter HSN Code"," HSN CODE");
        ArrayList<String> hsn_items=new ArrayList<String>();
        StaticMember.setToHSNCode(uproduct_hsn_code, hsn_items);
        company_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Name"," COMPANY NAME");
        StaticMember.setToCompany(company_name, company_id, company_items);
        type_name = StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE,"Enter Product Type"," PRODUCTS TYPE");
        StaticMember.setToType(type_name, type_id, type_items);
        uclose=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        ureset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        update=StaticMember.MyButton("UPDATE","Click On Button To Update The Record");
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
        mainGridPanel.add(product_name);mainGridPanel.add(upro_name);mainGridPanel.add(companyPanel);
        mainGridPanel.add(typePanel);mainGridPanel.add(hsn_gstPanel);mainGridPanel.add(bPanel);
        companyPanel.add(company_name,BorderLayout.CENTER);companyPanel.add(ucButton,BorderLayout.EAST);
        typePanel.add(type_name,BorderLayout.CENTER);typePanel.add(utButton,BorderLayout.EAST);
        hsn_gstPanel.add(uproduct_hsn_code);hsn_gstPanel.add(gst_list);
        bPanel.add(new JLabel(" "));bPanel.add(new JLabel(" "));bPanel.add(update);bPanel.add(ureset);bPanel.add(uclose);
        
        utButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {               
                 codeToType();
             }       });
        utButton.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    codeToType();
           }}});
        
        ucButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToCompany();
          }});
        ucButton.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    codeToCompany();
           }});
        upro_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                { company_name.requestFocusInWindow();
                }
           }});
        company_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    type_name.requestFocusInWindow();
                }}});
        
        type_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    uproduct_hsn_code.requestFocus();
                }}});
        uproduct_hsn_code.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                { gst_list.requestFocusInWindow();
                }
           }});
        gst_list.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                { update.requestFocusInWindow();
                }
           }});
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToUpdate();
             }});
        update.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    codeToUpdate();
                }}});
        ureset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                reset();
             }});
        ureset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    reset();
                }}});
        uclose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                 StaticMember.UPDATE_PRODUCT=false;
                UpdateProduct.this.dispose();
             }});
        uclose.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                     StaticMember.UPDATE_PRODUCT=false;
                UpdateProduct.this.dispose();
                }}});
        
        product_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                try
                {  
                    ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+product_id.get(product_items.indexOf(product_name.getText()))+"'");
                    pr.next();
                    upro_name.setText(pr.getString("product_name"));  
                    uproduct_hsn_code.setText(pr.getString("product_hsn_code"));
                    gst_list.setSelectedItem(pr.getString("product_gst").toString().trim());
                    ResultSet r=StaticMember.con.createStatement().executeQuery("select * from company where company_id like'"+pr.getString("company_id")+"'");
                    r.next();
                    company_name.setText(r.getString("company_name"));
                                        
                    ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+pr.getString("type_id")+"'");
                    t.next();
                    
                    type_name.setText(t.getString("type_name"));
                    sid=pr.getString("product_id");
                }catch(SQLException ex)
                {
                   
                }
                upro_name.requestFocusInWindow();
                }          } });
        
        
    }
    
    //code to Update 
    private void codeToUpdate()
    {
        try
        {
          String g=gst_list.getSelectedItem().toString();
          String gst=g.substring(0,2); 
          PreparedStatement stmt=StaticMember.con.prepareStatement("update products set product_name=?,company_id=?,type_id=?,product_hsn_code=?,product_gst=? where product_id=?"); 
          stmt.setString(1,upro_name.getText());
          stmt.setString(2,company_id.get(company_items.indexOf(company_name.getText())));
          stmt.setString(3, type_id.get(type_items.indexOf(type_name.getText()))); 
          stmt.setString(4, uproduct_hsn_code.getText());
          stmt.setFloat(5, Float.parseFloat(gst));
          stmt.setString(6, sid);
          stmt.executeUpdate();
          JOptionPane.showMessageDialog(this, "Record Update Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
          reset();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    //create a Method to add Company
    private void codeToCompany()
    {
        JInternalFrame AddNewCompany=new JInternalFrame("",true,true);
                AddNewCompany anc=new AddNewCompany();
                MDIMainWindow.desktop.add(anc);
                anc.setVisible(true);
                ucButton.getModel();
                anc.setResizable(false);
    }
    
    //Create a Method To Add Type 
    private void codeToType()
    {
        JInternalFrame AddProductType=new JInternalFrame("",true,true);
                AddProductType at=new AddProductType();
                MDIMainWindow.desktop.add(at);
                at.setVisible(true);
                utButton.getModel();
                at.setResizable(false);
    }
    
    public void reset()
    {
        upro_name.setText("");
        uproduct_hsn_code.setText("");
        company_name.setText("");
        type_name.setText("");
        gst_list.setSelectedIndex(-1);
        product_name.setText("");
        product_name.requestFocusInWindow();
    }
    
}
