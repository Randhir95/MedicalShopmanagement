/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
 * @author RANDHIR KUMAR
 */
public class UpdateCustomer extends JInternalFrame{
    JTextField customer_list,owner_name,customer_name,customer_address,customer_mob,customer_email,customer_dl,customer_vat_tin,customer_gst,customer_ac,state_name,state_code,customer_ifac,customer_pan,customer_bank_name,company_name,customer_ac_holder,customer_aadhar,dl_ishu_date,dl_exp_date;
    JButton close,update,reset;
    ArrayList<String> customer_id=new ArrayList<>();
    ArrayList<String> customer_items=new ArrayList<>();
    String cuid;
    public UpdateCustomer()
    {
         super("MODIFY CUSTOMER ACCOUNT",true,true);
        //this.setSize(500,400);
        this.setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-210);
        this.setDefaultCloseOperation(AddNewCustomer.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(200,0);
        
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.UPDATE_CUSTOMER=false;
              }});
        
        JLabel h=new JLabel("MODIFY CUSTOMER",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        customer_list=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Customer Name","SELECT CUSTOMER/SHOP NAME");
        StaticMember.setCustomerdata(customer_list, customer_id, customer_items);
        customer_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Customer Name"," CUSTOMER/SHOP NAME");
        owner_name=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Shop/Farm Holder Name"," SHOP/FARM HOLDER NAME");
        customer_address=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Customer Address"," ADDRESS");
        customer_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE," Enter Customer Mobile No."," MOBILE NO");
        customer_email=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Customer Email Id"," EMAIL ID");
        customer_dl=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Customer DL No."," DL NO");
        customer_gst=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Customer GSTIN No."," GSTIN NO");
        customer_ac=StaticMember.MyInputBox("",16,StaticMember.INTEGER_TYPE," Enter Customer Account No."," ACCOUNT NO");
        customer_ifac=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Account IFSC Code"," IFSC CODE");
        state_name=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false, " State Name"," STATE NAME");
        state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code"," STATE CODE");
        customer_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No."," PAN NO");
        customer_bank_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Bank Name"," BANK NAME");
        ArrayList<String> bank_items=new ArrayList<String>();
           StaticMember.setToBankName(customer_bank_name, bank_items);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        update=StaticMember.MyButton("UPDATE","Click On Button To Update The Record");   
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainGridPanel=new JPanel(new GridLayout(11,1,5,5));
        JPanel state_codePanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel mob_dlPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel gst_panPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel account_ifscPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel bpanel=new JPanel(new GridLayout(1,5,10,10));
        mainPanel.add(new JLabel("      "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(mainGridPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("     "),BorderLayout.SOUTH);
        this.add(mainPanel,BorderLayout.CENTER);
        state_codePanel.add(state_name);state_codePanel.add(state_code);
        mob_dlPanel.add(customer_mob);mob_dlPanel.add(customer_dl);
        gst_panPanel.add(customer_gst);gst_panPanel.add(customer_pan);
        account_ifscPanel.add(customer_ac);account_ifscPanel.add(customer_ifac);
        mainGridPanel.add(customer_list);mainGridPanel.add(customer_name);
        mainGridPanel.add(owner_name);mainGridPanel.add(customer_address);mainGridPanel.add(customer_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(customer_bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(update);bpanel.add(reset);bpanel.add(close);
        
        customer_list.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
               try
                 { 
                    
                    ResultSet cr=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+customer_id.get(customer_items.indexOf(customer_list.getText()))+"'");
                    cr.next();
                    customer_name.setText((cr.getString("customer_name")).toUpperCase());
                    owner_name.setText((cr.getString("dl_holder")).toUpperCase());
                    customer_mob.setText(cr.getString("customer_mob"));
                    customer_dl.setText((cr.getString("customer_dl")).toUpperCase());
                    customer_email.setText(cr.getString("customer_email"));
                    customer_address.setText((cr.getString("customer_address")));
                    customer_gst.setText((cr.getString("customer_gst")));
                    customer_ac.setText(cr.getString("customer_ac"));
                    customer_ifac.setText((cr.getString("customer_ifsc")));
                    state_name.setText(StaticMember.getStatename(cr.getString("customer_gst")));
                    state_code.setText(StaticMember.getStateCode(cr.getString("customer_gst")));
                    customer_pan.setText(StaticMember.getPanno(cr.getString("customer_gst")));
                    customer_bank_name.setText((cr.getString("customer_bank_name")));
                    cuid=cr.getString("customer_id");
                    
                 }catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(UpdateCustomer.this, ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
                }
               catch(Exception ex)
               {
                   JOptionPane.showMessageDialog(UpdateCustomer.this, ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
               }
             customer_name.requestFocusInWindow();
           }              }});
        
        
        
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.UPDATE_CUSTOMER=false;
                 UpdateCustomer.this.dispose();
            }});
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.UPDATE_CUSTOMER=false;
                UpdateCustomer.this.dispose();
            }});
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codeToSave();
            }});
        update.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                codeToSave();
            }});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 reset();
            }});
        reset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                reset();
            }});
        customer_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                owner_name.requestFocus();
            }});
        owner_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                customer_address.requestFocus();
            }});
        customer_address.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                customer_email.requestFocus();
            }});
        customer_email.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                customer_mob.requestFocus();
            }});
        customer_mob.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                customer_dl.requestFocus();
            }});
        customer_dl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                customer_gst.requestFocusInWindow();
             }});
        
        customer_gst.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    customer_bank_name.requestFocus();
                }
            }});
        customer_bank_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    customer_ac.requestFocus();
                }});
        customer_ac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                customer_ifac.requestFocus();
            }});
        
        customer_ifac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                update.requestFocus();
            }});
        
        
        
    }
    
    
    private void reset()
    {
        customer_list.setText("");
        customer_name.setText("");
        owner_name.setText("");
        customer_address.setText("");
        customer_mob.setText("");
        customer_email.setText("");
        customer_dl.setText("");
        customer_gst.setText("");
        customer_ac.setText("");
        customer_ifac.setText("");
        customer_pan.setText("");
        customer_aadhar.setText("");
        state_name.setText("");
        state_code.setText("");
        customer_bank_name.setText("");
        customer_list.requestFocusInWindow();
    }
    
    public void codeToSave()
    {
        
        //validate data
     
        if(customer_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Customer Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            customer_name.setText("");
            customer_name.requestFocusInWindow();
           return;
        }
        if(customer_address.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            customer_address.setText("");
            customer_address.requestFocusInWindow();
           return;
        }
        if(customer_mob.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            customer_mob.setText("");
            customer_mob.requestFocusInWindow();
           return;
        }
        
       
        //code to save
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("update customer set customer_name=?,customer_address=?,customer_mob=?,customer_email=?,customer_gst=?,customer_ac=?,customer_ifsc=?,dl_holder=?,customer_dl=? where customer_id=?");
            stmt.setString(1, customer_name.getText());
            stmt.setString(2, customer_address.getText());
            stmt.setString(3, customer_mob.getText());
            stmt.setString(4, customer_email.getText());
            stmt.setString(5, customer_gst.getText());
            stmt.setString(6, customer_ac.getText());
            stmt.setString(7, customer_ifac.getText());
            stmt.setString(8, owner_name.getText());
            stmt.setString(9, customer_dl.getText());
            stmt.setString(10, cuid);
            stmt.execute();
            StaticMember.setCustomerdata(customer_list, customer_id, customer_id);
            StaticMember.setCustomerdata(Billing.cust_name, customer_id, customer_items);
            StaticMember.setCustomerdata(KBilling.cust_name, customer_id, customer_items);
            JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset();
          
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
}
