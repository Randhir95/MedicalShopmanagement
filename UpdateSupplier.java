/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

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
 * @author Randhir
 */
public class UpdateSupplier extends JInternalFrame{
    JTextField usupplier_name,usupplier_address,usupplier_mob,usupplier_email,usupplier_dl,usupplier_gst,usupplier_ac,usupplier_ifac,usupplier_pan,supplier_name_list,supplier_bank_name,state_name,state_code;
    JButton uclose,update,ureset;
    String[] str;
    ResultSet rset=null;
    String sid;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    ArrayList<String> supplier_id=new ArrayList<>();
    ArrayList<String> supplier_items=new ArrayList<>();
    public UpdateSupplier()
    {
        super("",true,true);
        //this.setSize(500,400);
        this.setSize(StaticMember.SCREEN_WIDTH-830,StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(UpdateSupplier.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300,10);
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.UPDATE_SUPPLIER=false;
              }});
        
        JLabel h=new JLabel("MODIFY SUPPLIER ACCOUNT",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        supplier_name_list=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Supplier Name ","SELECT SUPPLIER NAME");
        StaticMember.setToSupplier(supplier_name_list, supplier_id, supplier_items);
        //setToSupplier();
        usupplier_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Name"," SUPPLIER NAME");
        usupplier_address=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Address"," ADDRESS");
        usupplier_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE," Enter Supplier Mobile No."," MOBILE NO");
        usupplier_email=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE," Enter Supplier Email Id"," EMAIL ID");
        usupplier_dl=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier DL No."," DL NO");
        usupplier_gst=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier GSTIN No."," GSTIN NO");
        usupplier_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE," Enter Supplier Account No."," ACCOUNT NO");
        usupplier_ifac=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Account IFSC Code "," IFSC CODE");
        state_name=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," State Name"," SATE NAME");
        state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code"," SATE CODE");
        usupplier_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No."," PAN NO");
        supplier_bank_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Bank Name"," BANK NAME");
        ArrayList<String> bank_items=new ArrayList<String>();
            for(int i=0;i<StaticMember.BANK_NAME.length;i++)
                {
                    bank_items.add((StaticMember.BANK_NAME[i]).toUpperCase());
                }
            JTextFieldAutoComplete.setupAutoComplete(supplier_bank_name, bank_items,false);
        
        uclose=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        ureset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        update=StaticMember.MyButton("UPDATE","Click On Button To Update The Record");
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainGridPanel=new JPanel(new GridLayout(10,1,5,5));
        JPanel state_codePanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel mob_dlPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel gst_panPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel account_ifscPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel bpanel=new JPanel(new GridLayout(1,5,10,10));
        mainPanel.add(new JLabel("      "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(mainGridPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("     "),BorderLayout.SOUTH);
        this.add(mainPanel,BorderLayout.CENTER);
        state_codePanel.add(state_name);state_codePanel.add(state_code);
        mob_dlPanel.add(usupplier_mob);mob_dlPanel.add(usupplier_dl);
        gst_panPanel.add(usupplier_gst);gst_panPanel.add(usupplier_pan);
        account_ifscPanel.add(usupplier_ac);account_ifscPanel.add(usupplier_ifac);
        mainGridPanel.add(supplier_name_list);
        mainGridPanel.add(usupplier_name);mainGridPanel.add(usupplier_address);mainGridPanel.add(usupplier_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(supplier_bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(update);bpanel.add(ureset);bpanel.add(uclose);
        
        supplier_name_list.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                try
                {
                    ResultSet cr=StaticMember.con.createStatement().executeQuery("select * from supplier where supplier_id like'"+supplier_id.get(supplier_items.indexOf(supplier_name_list.getText()))+"'");
                    cr.next();
                    usupplier_name.setText(cr.getString("supplier_name"));
                    usupplier_address.setText(cr.getString("supplier_address"));
                    usupplier_mob.setText(cr.getString("supplier_mob"));
                    usupplier_email.setText(cr.getString("supplier_email"));
                    usupplier_dl.setText(cr.getString("supplier_dl"));
                    usupplier_gst.setText(cr.getString("supplier_gst"));
                    usupplier_ac.setText(cr.getString("supplier_ac"));
                    usupplier_ifac.setText(cr.getString("supplier_ifac"));
                    state_name.setText(StaticMember.getStatename(cr.getString("supplier_gst")));
                    state_code.setText(StaticMember.getStateCode(cr.getString("supplier_gst")));
                    usupplier_pan.setText(StaticMember.getPanno(cr.getString("supplier_gst")));
                    supplier_bank_name.setText(cr.getString("supplier_bank_name"));
                    sid=cr.getString("supplier_id");
                    
                }catch(SQLException ex)
                {
                    ureset();
                }
                     usupplier_name.requestFocusInWindow();
                }
            }});
        
        uclose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 StaticMember.UPDATE_SUPPLIER=false;
                    UpdateSupplier.this.dispose();
            }});
        uclose.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    StaticMember.UPDATE_SUPPLIER=false;
                    UpdateSupplier.this.dispose();
                }
            }});
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 codeToUpdate();
            }});
        update.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                 codeToUpdate();   
                }
            }});
        ureset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 ureset();
            }});
        ureset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                ureset();
            }});
        usupplier_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_address.requestFocus();
            }});
        usupplier_address.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_email.requestFocus();
            }});
        usupplier_email.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_mob.requestFocus();
            }});
        usupplier_mob.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_dl.requestFocus();
            }});
        usupplier_dl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_gst.requestFocus();
            }});
        usupplier_gst.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    state_name.setText(StaticMember.getStatename(usupplier_gst.getText()));
                    state_code.setText(StaticMember.getStateCode(usupplier_gst.getText()));
                    usupplier_pan.setText(StaticMember.getPanno(usupplier_gst.getText()));
                    supplier_bank_name.requestFocus();
                }
            }});
        supplier_bank_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_ac.requestFocus();
            }});
        usupplier_ac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                usupplier_ifac.requestFocus();
            }});
        
        usupplier_ifac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                update.requestFocus();
            }});
        
       
    }
    
    private void ureset()
    {
        usupplier_name.setText("");
        usupplier_address.setText("");
        usupplier_mob.setText("");
        usupplier_email.setText("");
        usupplier_dl.setText("");
        usupplier_gst.setText("");
        usupplier_ac.setText("");
        usupplier_ifac.setText("");
        usupplier_pan.setText("");
        state_name.setText("");
        state_code.setText("");
        supplier_name_list.setText("");
        supplier_name_list.requestFocusInWindow();
    }
    
    public void codeToUpdate()
    {
        
        //validate data
     
        if(usupplier_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Supplier Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            usupplier_name.setText("");
            usupplier_name.requestFocusInWindow();
           return;
        }
        if(usupplier_address.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            usupplier_address.setText("");
            usupplier_address.requestFocusInWindow();
           return;
        }
        if(usupplier_mob.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            usupplier_mob.setText("");
            usupplier_mob.requestFocusInWindow();
           return;
        }
        if(usupplier_dl.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "DL No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            usupplier_dl.setText("");
            usupplier_dl.requestFocusInWindow();
           return;
        }
        if(usupplier_gst.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "GSTIN No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            usupplier_gst.setText("");
            usupplier_gst.requestFocusInWindow();
            return;
        }
        
        //code to save
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("update supplier set supplier_name=?,supplier_address=?,supplier_mob=?,supplier_email=?,supplier_gst=?,supplier_dl=?,supplier_ac=?,supplier_ifac=?,supplier_bank_name=? where supplier_id=?");
            stmt.setString(1, usupplier_name.getText());
            stmt.setString(2, usupplier_address.getText());
            stmt.setString(3, usupplier_mob.getText());
            stmt.setString(4, usupplier_email.getText());
            stmt.setString(5, usupplier_gst.getText());
            stmt.setString(6, usupplier_dl.getText());
            stmt.setString(7, usupplier_ac.getText());
            stmt.setString(8, usupplier_ifac.getText());
            stmt.setString(9, supplier_bank_name.getText());
            stmt.setString(10, sid);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record Updated!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            ureset();
            StaticMember.setToSupplier(MDIMainWindow.u_s_obj.usupplier_name, MDIMainWindow.u_s_obj.supplier_id, MDIMainWindow.u_s_obj.supplier_items);
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    
}
