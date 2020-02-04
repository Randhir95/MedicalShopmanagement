/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 *
 * @author dell
 */
public class AddNewProducts extends JInternalFrame {
    
    JButton cButton,tButton,crbutton,trbutton,save,close,reset;
    JTextField pro_name,product_hsn_code,company_name,type_name;
    JComboBox gst_list;
    String[] cstr,tstr,cstr_id,tstr_id;
    String cid,tid;
    ResultSet rset=null;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    ArrayList<String> type_id=new ArrayList<>();
    ArrayList<String> type_items=new ArrayList<>();
    AddNewProducts self;
    public AddNewProducts()
    {
        super("CTEATE NEW PRODUCT",true,true);
        this.setSize(700,400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(350,60);
        gst_list=StaticMember.MyComboBox(StaticMember.gst_item,"Select GST Persentage"," GST %");
        cButton=StaticMember.AddButton("/images/plus.png", 50, 50, "Click on button To Add More Company");
        tButton=StaticMember.AddButton("/images/plus.png", 50, 50, "Click On Button To Add More Product Type ");
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_NEW_PRODUCT=false;
              }});
        
        self=this;
        JLabel h=new JLabel("ADD NEW PRODUCT",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        
        //Create Componnent
        ArrayList<String> product_id=new ArrayList<>();
        ArrayList<String> product_items=new ArrayList<>();
        pro_name = StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter New Product Name"," PRODUCT NAME");
        //StaticMember.setToProducts(pro_name, product_id, product_items);
        product_hsn_code = StaticMember.MyInputBox("",8,StaticMember.INTEGER_TYPE," Enter HSN Code"," HSN CODE");
        ArrayList<String> hsn_items=new ArrayList<String>();
        StaticMember.setToHSNCode(product_hsn_code, hsn_items);
        company_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Name"," COMPANY NAME");
        StaticMember.setToCompany(company_name, company_id, company_items);
        type_name = StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE,"Enter Product Type"," PRODUCTS TYPE");
        StaticMember.setToType(type_name, type_id, type_items);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        save=StaticMember.MyButton("SAVE","Click On Button To Save The Record");
        
        //add on panel
        JPanel mainPanel=new JPanel(new BorderLayout()); 
        JPanel mainGridPanel=new JPanel(new GridLayout(5,1,10,10));
        JPanel companyPanel=new JPanel(new BorderLayout());
        JPanel typePanel=new JPanel(new BorderLayout());
        JPanel hsn_gstPanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel bPanel=new JPanel(new GridLayout(1,5,20,20));
        this.add(mainPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("   "),BorderLayout.NORTH);
        mainPanel.add(new JLabel("     "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(new JLabel("   "),BorderLayout.SOUTH);mainPanel.add(mainGridPanel,BorderLayout.CENTER);
        mainGridPanel.add(pro_name);mainGridPanel.add(companyPanel);
        mainGridPanel.add(typePanel);mainGridPanel.add(hsn_gstPanel);mainGridPanel.add(bPanel);
        companyPanel.add(company_name,BorderLayout.CENTER);companyPanel.add(cButton,BorderLayout.EAST);
        typePanel.add(type_name,BorderLayout.CENTER);typePanel.add(tButton,BorderLayout.EAST);
        hsn_gstPanel.add(product_hsn_code);hsn_gstPanel.add(gst_list);
        bPanel.add(new JLabel(" "));bPanel.add(new JLabel(" "));bPanel.add(save);bPanel.add(reset);bPanel.add(close);
        
        
        tButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {               
                 codeToType();
             }       });
        tButton.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    codeToType();
           }} });
        
        cButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToCompany();
          }       });
        cButton.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    codeToCompany();
           }});
        
        pro_name.addKeyListener(new KeyAdapter(){
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
                    product_hsn_code.requestFocus();
                }}});
        product_hsn_code.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                { gst_list.requestFocusInWindow();
                }
           }});
        gst_list.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                { save.requestFocusInWindow();
                }
           }});
        
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToSave();
             }       });
        save.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    codeToSave();
                }}});
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                reset();
             }});
        reset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    reset();
                }}});
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_NEW_PRODUCT=false;
                AddNewProducts.this.dispose();
             }});
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.ADD_NEW_PRODUCT=false;
                     AddNewProducts.this.dispose();
                }}});
        
        
    }
    
    //Create Method to add Company
    public void codeToSave()
    {
        
        //validate data
     
        if(pro_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Product Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            pro_name.setText("");
            pro_name.requestFocusInWindow();
            return;
        }
        if(product_hsn_code.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "HSN Code Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            product_hsn_code.setText("");
            product_hsn_code.requestFocusInWindow();
            return;
        }
        if(gst_list.getSelectedIndex()==-1)
        {
            JOptionPane.showMessageDialog(this, "GST % Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            gst_list.setSelectedIndex(-1);
            gst_list.requestFocusInWindow();
            return;
        }
        if(company_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Company Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            company_name.setText("");
            company_name.requestFocusInWindow();
            return;
        }
        
        if(type_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Product Type Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            type_name.setText("");
            type_name.requestFocusInWindow();
            return;
        }
       //code to save
        try
        {
            String g=gst_list.getSelectedItem().toString();
            String gst=g.substring(0,2);
            PreparedStatement stmt=StaticMember.con.prepareStatement("insert into products(product_id,product_name,product_hsn_code,company_id,type_id,product_gst) values(?,?,?,?,?,?)");
            ResultSet p_rset=StaticMember.con.createStatement().executeQuery("select max(product_id) as product_id  from products");
            p_rset.next();
            int p_id=p_rset.getInt("product_id")+1;
            stmt.setInt(1, p_id);
            stmt.setString(2, pro_name.getText());
            stmt.setString(3, product_hsn_code.getText());
            stmt.setString(4,company_id.get(company_items.indexOf(company_name.getText())));
            stmt.setString(5, type_id.get(type_items.indexOf(type_name.getText())));
            stmt.setFloat(6, Float.parseFloat(gst));
            stmt.execute();
            JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset();
            pro_name.requestFocus();
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
                cButton.getModel();
                anc.setResizable(false);
    }
    //Create a Method To Add Type
    private void codeToType()
    {
        JInternalFrame AddProductType=new JInternalFrame("",true,true);
                AddProductType apt=new AddProductType();
                MDIMainWindow.desktop.add(apt);
                apt.setVisible(true);
                tButton.getModel();
                apt.setResizable(false);
    }
    public void codeToSetCompany()
    {
        try
        {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM company");
            while(rset.next())
            {
                company_id.add(rset.getString("company_id"));
                company_items.add((rset.getString("company_name")).toUpperCase());
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(company_name, company_items,false);
    }
    public void codeToSetType()
    {
        try
        {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM type");
            while(rset.next())
            {
                type_id.add(rset.getString("type_id"));
                type_items.add((rset.getString("type_name")).toUpperCase());
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(type_name, type_items,false);
    }
    
    public void reset()
    {
        pro_name.setText("");
        product_hsn_code.setText("");
        company_name.setText("");
        type_name.setText("");
    }
}
