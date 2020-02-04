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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Randhir
 */
public class AddSupplier extends JInternalFrame{
    JTextField supplier_name,supplier_address,supplier_mob,supplier_email,supplier_dl,supplier_gst,supplier_ac,state_name,state_code,supplier_ifac,supplier_pan,supplier_bank_name;
    JButton close,save,reset;
    ArrayList<String> supplier_id=new ArrayList<>();
    ArrayList<String> supplier_items=new ArrayList<>();
    public AddSupplier()
    {
        super("",true,true);
        //this.setSize(500,400);
        this.setSize(StaticMember.SCREEN_WIDTH-830,StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(AddSupplier.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(200,0);
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_SUPPLIER=false;
              }});
        
        JLabel h=new JLabel("CREATE NEW SUPPLIER",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        supplier_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Name"," SUPPLIER NAME");
        //StaticMember.setToSupplier(supplier_name, supplier_id, supplier_items);
        supplier_address=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Address"," ADDRESS");
        supplier_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE," Enter Supplier Mobile No."," MOBILE NO");
        supplier_email=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE," Enter Supplier Email Id"," EMAIL ID");
        supplier_dl=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier DL No."," DL NO");
        supplier_gst=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier GSTIN No."," GSTIN NO");
        supplier_ac=StaticMember.MyInputBox("",16,StaticMember.INTEGER_TYPE," Enter Supplier Account No."," ACCOUNT NO");
        supplier_ifac=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Account IFSC Code "," IFSC CODE");
        state_name=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," State Name"," SATE NAME");
        state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code"," SATE CODE");
        supplier_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No."," PAN NO");
        supplier_bank_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Bank Name"," BANK NAME");
        ArrayList<String> bank_items=new ArrayList<String>();
            for(int i=0;i<StaticMember.BANK_NAME.length;i++)
                {
                    bank_items.add((StaticMember.BANK_NAME[i]).toUpperCase());
                }
            JTextFieldAutoComplete.setupAutoComplete(supplier_bank_name, bank_items,false);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        save=StaticMember.MyButton("SAVE","Click On Button To Save The Record");    
            
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainGridPanel=new JPanel(new GridLayout(9,1,5,5));
        JPanel state_codePanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel mob_dlPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel gst_panPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel account_ifscPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel bpanel=new JPanel(new GridLayout(1,5,10,10));
        mainPanel.add(new JLabel("      "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(mainGridPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("     "),BorderLayout.SOUTH);
        this.add(mainPanel,BorderLayout.CENTER);
        state_codePanel.add(state_name);state_codePanel.add(state_code);
        mob_dlPanel.add(supplier_mob);mob_dlPanel.add(supplier_dl);
        gst_panPanel.add(supplier_gst);gst_panPanel.add(supplier_pan);
        account_ifscPanel.add(supplier_ac);account_ifscPanel.add(supplier_ifac);
        mainGridPanel.add(supplier_name);mainGridPanel.add(supplier_address);mainGridPanel.add(supplier_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(supplier_bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(save);bpanel.add(reset);bpanel.add(close);
        
        
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_SUPPLIER=false;
                 AddSupplier.this.dispose();
            }});
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.ADD_SUPPLIER=false;
                AddSupplier.this.dispose();
            }});
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codeToSave();
            }});
        save.addKeyListener(new KeyAdapter() {
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
        supplier_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_address.requestFocus();
            }});
        supplier_address.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_email.requestFocus();
            }});
        supplier_email.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_mob.requestFocus();
            }});
        supplier_mob.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_dl.requestFocus();
            }});
        supplier_dl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_gst.requestFocus();
            }});
        
        supplier_gst.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    state_name.setText(StaticMember.getStatename(supplier_gst.getText()));
                    state_code.setText(StaticMember.getStateCode(supplier_gst.getText()));
                    supplier_pan.setText(StaticMember.getPanno(supplier_gst.getText()));
                    supplier_bank_name.requestFocusInWindow();
                }
            }});
        supplier_bank_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    save.requestFocus();
                }});
        supplier_ac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_ifac.requestFocus();
            }});
        
        supplier_ifac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                supplier_bank_name.requestFocus();
            }});
    
    }
    
    private void reset()
    {
        supplier_name.setText("");
        supplier_address.setText("");
        supplier_mob.setText("");
        supplier_email.setText("");
        supplier_dl.setText("");
        supplier_gst.setText("");
        supplier_ac.setText("");
        supplier_ifac.setText("");
        supplier_pan.setText("");
        state_name.setText("");
        state_code.setText("");
        supplier_bank_name.setText("");
    }
    
     public void codeToSave()
    {
        
        //validate data
     
        if(supplier_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Supplier Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            supplier_name.setText("");
            supplier_name.requestFocusInWindow();
           return;
        }
        if(supplier_address.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            supplier_address.setText("");
            supplier_address.requestFocusInWindow();
           return;
        }
        if(supplier_mob.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            supplier_mob.setText("");
            supplier_mob.requestFocusInWindow();
           return;
        }
        if(supplier_dl.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "DL No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            supplier_dl.setText("");
            supplier_dl.requestFocusInWindow();
           return;
        }
        if(supplier_gst.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "GSTIN No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            supplier_gst.setText("");
            supplier_gst.requestFocusInWindow();
            return;
        }
       
        
        //code to save
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("insert into supplier(supplier_id,supplier_name,supplier_address,supplier_mob,supplier_email,supplier_gst,supplier_ac,supplier_ifac,supplier_dl,supplier_bank_name) values(?,?,?,?,?,?,?,?,?,?)");
            ResultSet sup_rset=StaticMember.con.createStatement().executeQuery("select max(supplier_id) as supplier_id  from supplier");
            sup_rset.next();
            int sup_id=sup_rset.getInt("supplier_id")+1;
            stmt.setInt(1, sup_id);
            stmt.setString(2, supplier_name.getText());
            stmt.setString(3, supplier_address.getText());
            stmt.setString(4, supplier_mob.getText());
            stmt.setString(5, supplier_email.getText());
            stmt.setString(6, supplier_gst.getText());
            stmt.setString(7, supplier_ac.getText());
            stmt.setString(8, supplier_ifac.getText());
            stmt.setString(9, supplier_dl.getText());
            stmt.setString(10, supplier_bank_name.getText());
            stmt.execute();
            StaticMember.setToSupplier(MDIMainWindow.s_obj.supplier_name, MDIMainWindow.s_obj.supplier_id, MDIMainWindow.s_obj.supplier_items);
            JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset();
          
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
   
     
}
