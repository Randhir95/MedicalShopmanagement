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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Randhir
 */
class CreateSelfCompany extends JInternalFrame{
    JTextField shop_name,shop_address,shop_mob,shop_email,shop_gst_no,shop_tin_no,shop_dl_no,shop_owner_name,shop_owner_pan_no,admin_id;
    JButton create,reset;
    JPasswordField admin_password;
    public CreateSelfCompany()
    {
        super("CREATE SELF COMPANY",true,true);
        this.setSize(800,470);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.CREATE_SELF_COMPANY=false;
              }});
        
        JLabel shopname=new JLabel("SHOP/COMPANY NAME : ",JLabel.RIGHT);
        shopname.setFont(StaticMember.labelFont);
        JLabel shopownername=new JLabel("OWNER NAME : ",JLabel.RIGHT);
        shopownername.setFont(StaticMember.labelFont);
        JLabel shopmob=new JLabel("MOBILE NO. : ",JLabel.RIGHT);
        shopmob.setFont(StaticMember.labelFont);
        JLabel shopaddress=new JLabel("ADDRESS : ",JLabel.RIGHT);
        shopaddress.setFont(StaticMember.labelFont);
        JLabel shopemail=new JLabel("EMAIL ID : ",JLabel.RIGHT);
        shopemail.setFont(StaticMember.labelFont);
        JLabel shopdlno=new JLabel("D.L. NO. : ",JLabel.RIGHT);
        shopdlno.setFont(StaticMember.labelFont);
        JLabel shopgstno=new JLabel("GSTIN NO. : ",JLabel.RIGHT);
        shopgstno.setFont(StaticMember.labelFont);
        JLabel shoptinno=new JLabel("TIN NO. : ",JLabel.RIGHT);
        shoptinno.setFont(StaticMember.labelFont);
        JLabel shoppanno=new JLabel("PAN NO. : ",JLabel.RIGHT);
        shoppanno.setFont(StaticMember.labelFont);
        JLabel adminid=new JLabel("ADMIN ID : ",JLabel.RIGHT);
        adminid.setFont(StaticMember.labelFont);
        JLabel adminpassword=new JLabel("PASSWORD : ",JLabel.RIGHT);
        adminpassword.setFont(StaticMember.labelFont);
        JLabel productkey=new JLabel("PAN NO. : ",JLabel.RIGHT);
        shoppanno.setFont(StaticMember.labelFont);
        
        //shop_name,shop_address,shop_mob,shop_email,shop_gst_no,shop_tin_no,shop_dl_no,shop_owner_name;
        shop_name=new JTextField("");
        shop_name.setFont(StaticMember.textFont);
        shop_name.setBackground(StaticMember.bcolor);
        shop_address=new JTextField("");
        shop_address.setFont(StaticMember.textFont);
        shop_address.setBackground(StaticMember.bcolor);
        shop_mob=new JTextField("");
        shop_mob.setFont(StaticMember.textFont);
        shop_mob.setBackground(StaticMember.bcolor);
        shop_email=new JTextField("");
        shop_email.setFont(StaticMember.textFont);
        shop_email.setBackground(StaticMember.bcolor);
        shop_gst_no=new JTextField("");
        shop_gst_no.setFont(StaticMember.textFont);
        shop_gst_no.setBackground(StaticMember.bcolor);
        shop_tin_no=new JTextField("");
        shop_tin_no.setFont(StaticMember.textFont);
        shop_tin_no.setBackground(StaticMember.bcolor);
        shop_dl_no=new JTextField("");
        shop_dl_no.setFont(StaticMember.textFont);
        shop_dl_no.setBackground(StaticMember.bcolor);
        shop_owner_name=new JTextField("");
        shop_owner_name.setFont(StaticMember.textFont);
        shop_owner_name.setBackground(StaticMember.bcolor);
        shop_owner_pan_no=new JTextField("");
        shop_owner_pan_no.setFont(StaticMember.textFont);
        shop_owner_pan_no.setBackground(StaticMember.bcolor);
        admin_id=new JTextField("");
        admin_id.setFont(StaticMember.textFont);
        admin_id.setBackground(StaticMember.bcolor);
        admin_password=new JPasswordField("");
        admin_password.setFont(StaticMember.textFont);
        admin_password.setBackground(StaticMember.bcolor);
        
        create=new JButton("CREATE");
        create.setFont(StaticMember.buttonFont);
        reset=new JButton("RESET");
        reset.setFont(StaticMember.buttonFont);
        
        JPanel mainpanel=new JPanel(new GridLayout(2,1));
        JPanel inputpanel=new JPanel(new GridLayout(6,2,5,3));
        JPanel detailpanel=new JPanel(new GridLayout(6,2,5,3));
        JPanel buttonpanel=new JPanel(new GridLayout(1,6));
        //JPanel signuppanel=new JPanel(new GridLayout(1,6));
        
        inputpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Company/Shop Detail"));
        detailpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Ligle Detail"));
        inputpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"SIGN UP ADMIN"));
        inputpanel.add(shopname);inputpanel.add(shop_name);inputpanel.add(shopownername);inputpanel.add(shop_owner_name);
        inputpanel.add(shopaddress);inputpanel.add(shop_address);inputpanel.add(shopmob);inputpanel.add(shop_mob);
        inputpanel.add(shopemail);inputpanel.add(shop_email);inputpanel.add(shoppanno);inputpanel.add(shop_owner_pan_no);
        detailpanel.add(shopdlno);detailpanel.add(shop_dl_no);detailpanel.add(shopgstno);detailpanel.add(shop_gst_no);
        detailpanel.add(shoptinno);detailpanel.add(shop_tin_no);detailpanel.add(adminid);detailpanel.add(admin_id);
        detailpanel.add(adminpassword);detailpanel.add(admin_password);detailpanel.add(new JLabel());detailpanel.add(new JLabel());
        buttonpanel.add(new JLabel());buttonpanel.add(new JLabel());buttonpanel.add(new JLabel());buttonpanel.add(create);buttonpanel.add(reset);
        mainpanel.add(inputpanel);mainpanel.add(detailpanel);
        this.add(mainpanel,BorderLayout.CENTER);
        this.add(buttonpanel,BorderLayout.SOUTH);
        
        shop_name.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    shop_owner_name.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_owner_name.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    shop_address.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_address.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    shop_mob.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_mob.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    shop_email.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_email.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    shop_dl_no.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_dl_no.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    shop_gst_no.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_gst_no.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    shop_tin_no.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_tin_no.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    shop_owner_pan_no.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        shop_owner_pan_no.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    create.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        create.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    saveInDataBase();
                    
                }
                    
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        reset.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    shop_name.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        create.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveInDataBase();
            }
        });
        
    }
    
    
    public void saveInDataBase()
    {
        
        //validate data  
     
        if(shop_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Shop/Company Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            shop_name.requestFocusInWindow();
           return;
        }
        if(shop_dl_no.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "DL NO. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            shop_dl_no.requestFocusInWindow();
            return;
        }
        
        
        //code to save
        try
        {//shop_name,shop_address,shop_mob,shop_email,shop_gst_no,shop_tin_no,shop_dl_no,shop_owner_name,shop_owner_pan_no,admin_id;
          PreparedStatement stmt=StaticMember.con.prepareStatement("insert into self_company(shop_name,shop_address,shop_mob,shop_email,shop_gst_no,shop_tin_no,shop_dl_no,shop_holder_name,shop_holder_pan_no,admin_id,admin_password) values(?,?,?,?,?,?,?,?,?,?,?)");
          stmt.setString(1, shop_name.getText());
          stmt.setString(2, shop_address.getText());
          stmt.setString(3, shop_mob.getText());
          stmt.setString(4, shop_email.getText());
          stmt.setString(5, shop_gst_no.getText());
          stmt.setString(6, shop_tin_no.getText());
          stmt.setString(7, shop_dl_no.getText());
          stmt.setString(8, shop_owner_name.getText());
          stmt.setString(9, shop_owner_pan_no.getText());
          stmt.setString(10, admin_id.getText());
          stmt.setString(11, admin_password.getText());
          
          stmt.execute();
          JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
          resetData();
          shop_name.requestFocusInWindow();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
        
        
    }
    
    
    public void resetData()
    {////shop_name,shop_address,shop_mob,shop_email,shop_gst_no,shop_tin_no,shop_dl_no,shop_owner_name,shop_owner_pan_no,admin_id;
        shop_name.setText("");
        shop_address.setText("");
        shop_mob.setText("");
        shop_email.setText("");
        shop_gst_no.setText("");
        shop_tin_no.setText("");
        shop_dl_no.setText("");
        shop_owner_name.setText("");
        shop_owner_pan_no.setText("");
        admin_id.setText("");
        admin_password.setText("");
    }
}
