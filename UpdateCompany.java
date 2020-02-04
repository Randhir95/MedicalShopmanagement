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
public class UpdateCompany extends JInternalFrame{
   private JTextField ucomp_name,ucomp_add,ucomp_mob,ucomp_dl,ucomp_email,ucomp_ac_holder,ucomp_gst,ucomp_ac,ucomp_ifac,comp_state,comp_state_code,ucomp_pan,bank_name,company_name;
   private JButton update,close,reset; 
   private JPanel uinfopanel,ubuttonpanel;
   private String str[]; 
   private ResultSet rset=null;
   private String sid;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    public UpdateCompany()
    {
       super("",true,true);
      this.setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-250);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(UpdateCompany.DISPOSE_ON_CLOSE);
      this.setLocation(300,50);
      StaticMember.lookAndFeel();
      this.addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.UPDATE_COMPANY=false;
            }
        });
        
      //create label for heading
      JLabel h1=new JLabel("MODIFY COMPANY",JLabel.CENTER);
      h1.setOpaque(true);
      h1.setForeground(StaticMember.HEAD_FG_COLOR1);
      h1.setBackground(StaticMember.HEAD_BG_COLOR1);
      h1.setFont(StaticMember.HEAD_W_FONT);
      this.add(h1,BorderLayout.NORTH);
      
  
        ucomp_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Name "," COMPANY NAME ");
        ucomp_add=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Address"," ADDRESS ");
        ucomp_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE," Enter Company Mobile No.","MOBILE NO.");
        ucomp_dl=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company DL No."," DL NO.");
        ucomp_email=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Email Id","EMAIL ID");
        ucomp_gst=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company GSTIN No."," GSTIN NO.");
        ucomp_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE," Enter Company Account No."," ACCOUNT NO.");
        ucomp_ifac=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company IFSC Code"," IFSC CODE");
        comp_state=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," State Name","STATE NAME");
        comp_state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code","STATE CODE");
        ucomp_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No.","PAN NO.");
        bank_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Bank Name"," BANK NAME");
            ArrayList<String> bank_items=new ArrayList<String>();
            StaticMember.setToBankName(bank_name, bank_items);
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        update=StaticMember.MyButton("UPDATE","Click On Button To Save The Record");
        company_name = StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Company Name","SELECT COMPANY NAME");
         StaticMember.setToCompany(company_name, company_id, company_items);
        
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
        state_codePanel.add(comp_state);state_codePanel.add(comp_state_code);
        mob_dlPanel.add(ucomp_mob);mob_dlPanel.add(ucomp_dl);
        gst_panPanel.add(ucomp_gst);gst_panPanel.add(ucomp_pan);
        account_ifscPanel.add(ucomp_ac);account_ifscPanel.add(ucomp_ifac);
        mainGridPanel.add(company_name);
        mainGridPanel.add(ucomp_name);mainGridPanel.add(ucomp_add);mainGridPanel.add(ucomp_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(update);bpanel.add(reset);bpanel.add(close);
      
        company_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) 
            {
                if(e.getKeyChar()=='\n')
                {
                    try
                    { 
                        ResultSet cr=StaticMember.con.createStatement().executeQuery("select * from company where company_id like'"+company_id.get(company_items.indexOf(company_name.getText()))+"'");
                        cr.next();
                        ucomp_name.setText((cr.getString("company_name")).toUpperCase());
                        ucomp_add.setText((cr.getString("address")).toUpperCase());
                        ucomp_mob.setText(cr.getString("mobile_no"));
                        ucomp_dl.setText((cr.getString("dl_no")).toUpperCase());
                        ucomp_email.setText(cr.getString("email"));
                        ucomp_gst.setText((cr.getString("c_gst")));
                        ucomp_ac.setText(cr.getString("c_ac"));
                        ucomp_ifac.setText((cr.getString("c_ifac")));
                        ucomp_pan.setText(StaticMember.getPanno(cr.getString("c_gst")));
                        comp_state.setText(StaticMember.getStatename(cr.getString("c_gst")));
                        comp_state_code.setText(StaticMember.getStateCode(cr.getString("c_gst")));
                        bank_name.setText((cr.getString("bank_name")));
                        sid=cr.getString("company_id");
                    }catch(SQLException ex)
                    {
                        reset();
                    }
                  ucomp_name.requestFocusInWindow();
               }              
            }});
        
        //Cerate Panel
        
        
        update.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
              codeToUpdate();
           }              }});
        update.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               codeToUpdate();
           }});  
      
        close.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
               StaticMember.UPDATE_COMPANY=false;
               UpdateCompany.this.dispose(); 
            }             
           }});
      
        close.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               StaticMember.UPDATE_COMPANY=false;
               UpdateCompany.this.dispose(); 
           }});  
        reset.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
              reset();
           }              }});
        reset.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               reset();
           }});
        ucomp_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_add.requestFocusInWindow();
                }
            }});
        ucomp_add.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_email.requestFocusInWindow();
                }            }});
        
        ucomp_email.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_mob.requestFocusInWindow();
                }           }}); 
        ucomp_mob.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_dl.requestFocusInWindow();
                } }});
        ucomp_dl.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_gst.requestFocusInWindow();
                }  
             }});
        ucomp_gst.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_state.setText(StaticMember.getStatename(ucomp_gst.getText()));
                    comp_state_code.setText(StaticMember.getPanno(ucomp_gst.getText()));
                    ucomp_pan.setText(StaticMember.getStateCode(ucomp_gst.getText()));
                    bank_name.requestFocusInWindow();               
                }           }});
        bank_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_ac.requestFocusInWindow();               
                }           }});
        ucomp_ac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    ucomp_ifac.requestFocusInWindow();               
                }           }});
        ucomp_ifac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    update.requestFocusInWindow();               
                }           }});
        
        
    }
    
    
    //Code to Upadte
    public void codeToUpdate()
    {
        //validate data         
        if(ucomp_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Company Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            ucomp_name.requestFocusInWindow();
            return;
        }
        
        if(ucomp_add.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            ucomp_add.requestFocusInWindow();
            return; 
        }
        
         //code to update
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("update company set company_name=?,address=?,mobile_no=?,dl_no=?,email=?,c_gst=?,c_ac=?,c_ifac=?,bank_name=? where company_id=?");
            stmt.setString(1, ucomp_name.getText());
            stmt.setString(2, ucomp_add.getText());
            stmt.setString(3, ucomp_mob.getText());
            stmt.setString(4, ucomp_dl.getText());
            stmt.setString(5, ucomp_email.getText());
            stmt.setString(6, ucomp_gst.getText());
            stmt.setString(7, ucomp_ac.getText());
            stmt.setString(8, ucomp_ifac.getText());
            stmt.setString(9, bank_name.getText());
            stmt.setString(10, sid);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record Update Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset();
            StaticMember.setToCompany(company_name, company_id, company_items);
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    public void reset()
    {
        company_name.setText("");
        ucomp_name.setText("");
        ucomp_add.setText("");
        ucomp_mob.setText("");
        ucomp_dl.setText("");
        ucomp_email.setText("");
        ucomp_gst.setText("");
        ucomp_ac.setText("");
        ucomp_ifac.setText("");
        comp_state.setText("");
        comp_state_code.setText("");
        ucomp_pan.setText("");
        bank_name.setText("");
        company_name.requestFocusInWindow();
    }
    
    
}
