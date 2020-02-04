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
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Intel
 */
public class DeleteSupplier extends JInternalFrame
{
    private JTextField supplier_name_list,dsupplier_name,dsupplier_address,dsupplier_mob,dsupplier_email,dsupplier_dl,dsupplier_gst,dsupplier_ac,dsupplier_ifac,dsupplier_pan,state_name,state_code,supplier_bank_name;
    private JButton delete,close,reset;
    private String[] str;
    private ResultSet rset=null;
    private String sid;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    ArrayList<String> supplier_id=new ArrayList<>();
    ArrayList<String> supplier_items=new ArrayList<>();
    public DeleteSupplier()
    {
        super("",true,true);
        this.setSize(StaticMember.SCREEN_WIDTH-830,StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(DeleteSupplier.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300,10);
        StaticMember.lookAndFeel();
        
         this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.DELETE_SUPPLIER=false;
              }});
    
        
        
        supplier_name_list=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Supplier Name ","SELECT SUPPLIER NAME");
        StaticMember.setToSupplier(supplier_name_list, supplier_id, supplier_items);
        dsupplier_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Supplier Name"," SUPPLIER NAME");
        dsupplier_address=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,false," Enter Supplier Address"," ADDRESS");
        dsupplier_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE,false," Enter Supplier Mobile No."," MOBILE NO");
        dsupplier_email=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,false," Enter Supplier Email Id"," EMAIL ID");
        dsupplier_dl=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," Enter Supplier DL No."," DL NO");
        dsupplier_gst=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,false," Enter Supplier GSTIN No."," GSTIN NO");
        dsupplier_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE,false," Enter Supplier Account No."," ACCOUNT NO");
        dsupplier_ifac=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,false," Enter Account IFSC Code "," IFSC CODE");
        state_name=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," State Name"," SATE NAME");
        state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code"," SATE CODE");
        dsupplier_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No."," PAN NO");
        supplier_bank_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Supplier Bank Name"," BANK NAME");
        ArrayList<String> bank_items=new ArrayList<String>();
            for(int i=0;i<StaticMember.BANK_NAME.length;i++)
                {
                    bank_items.add((StaticMember.BANK_NAME[i]).toUpperCase());
                }
            JTextFieldAutoComplete.setupAutoComplete(supplier_bank_name, bank_items,false);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        delete=StaticMember.MyButton("DELETE","Click On Button To Delete The Record");
        
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
        mob_dlPanel.add(dsupplier_mob);mob_dlPanel.add(dsupplier_dl);
        gst_panPanel.add(dsupplier_gst);gst_panPanel.add(dsupplier_pan);
        account_ifscPanel.add(dsupplier_ac);account_ifscPanel.add(dsupplier_ifac);
        mainGridPanel.add(supplier_name_list);
        mainGridPanel.add(dsupplier_name);mainGridPanel.add(dsupplier_address);mainGridPanel.add(dsupplier_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(supplier_bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(delete);bpanel.add(reset);bpanel.add(close);
        
        supplier_name_list.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {  
                if(e.getKeyChar()=='\n')
                {
                    try
                    {
                        ResultSet cr=StaticMember.con.createStatement().executeQuery("select * from supplier where supplier_id like'"+supplier_id.get(supplier_items.indexOf(supplier_name_list.getText()))+"'");
                        cr.next();
                        dsupplier_name.setText(rset.getString("supplier_name"));
                        dsupplier_address.setText(rset.getString("supplier_address"));
                        dsupplier_mob.setText(rset.getString("supplier_mob"));
                        dsupplier_email.setText(rset.getString("supplier_email"));
                        dsupplier_dl.setText(rset.getString("supplier_dl"));
                        dsupplier_gst.setText(rset.getString("supplier_gst"));
                        dsupplier_ac.setText(rset.getString("supplier_ac"));
                        dsupplier_ifac.setText(rset.getString("supplier_ifac"));
                        state_name.setText(StaticMember.getStatename(cr.getString("supplier_gst")));
                        state_code.setText(StaticMember.getStateCode(cr.getString("supplier_gst")));
                        dsupplier_pan.setText(StaticMember.getPanno(cr.getString("supplier_gst")));
                        sid=rset.getString("supplier_id");
                    }catch(SQLException ex)
                    {
                        dreset();
                    }
                }
           }});
        

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 codeToDelete();
            }});
        delete.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                codeToDelete();
            }});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 StaticMember.DELETE_SUPPLIER=false;
                    DeleteSupplier.this.dispose();
            }});
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    StaticMember.DELETE_SUPPLIER=false;
                    DeleteSupplier.this.dispose();
                }
            }});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 dreset();
            }});
        reset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                dreset();
            }});
    }
    
     private void dreset()
    {
        dsupplier_name.setText("");
        dsupplier_address.setText("");
        dsupplier_mob.setText("");
        dsupplier_email.setText("");
        dsupplier_dl.setText("");
        dsupplier_gst.setText("");
        dsupplier_ac.setText("");
        dsupplier_ifac.setText("");
        dsupplier_pan.setText("");
        state_name.setText("");
        state_code.setText("");
        supplier_bank_name.setText("");
        dsupplier_pan.setText("");
        supplier_name_list.setText("");
        supplier_name_list.requestFocusInWindow();
    }
      private void codeToDelete()
    {
        try
        {
          PreparedStatement stmt=StaticMember.con.prepareStatement("delete from supplier where supplier_id=?");
          //dpname,dcname,dmname,dtname
          stmt.setString(1, sid);         
          stmt.executeUpdate();
          JOptionPane.showMessageDialog(this, "Record Delete Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
          dreset();
          StaticMember.setToSupplier(MDIMainWindow.d_s_obj.dsupplier_name, MDIMainWindow.d_s_obj.supplier_id, MDIMainWindow.d_s_obj.supplier_items);
          supplier_name_list.requestFocusInWindow();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
      
    
}

