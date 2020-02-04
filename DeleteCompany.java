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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
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
public class DeleteCompany extends JInternalFrame{
    JTextField dcomp_name,dcomp_add,dcomp_mob,dcomp_dl,dcomp_email,dcomp_gst,dcomp_ac,dcomp_ifac,comp_state,comp_state_code,dcomp_pan,bank_name;
    JButton delete,close,reset;
    JTextField company_name;
    String sid;
    String str[];
    ResultSet rset=null;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    public DeleteCompany(){
        super("",true,true);
        setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(DeleteCompany.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300,10);
        
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.DELETE_COMPANY=false;
               }
        });
        
        //create label for heading
        JLabel h1=new JLabel("REMOVE COMPANY",JLabel.CENTER);
        h1.setOpaque(true);
        h1.setForeground(StaticMember.HEAD_FG_COLOR1);
        h1.setBackground(StaticMember.HEAD_BG_COLOR1);
        h1.setFont(StaticMember.HEAD_W_FONT);
        this.add(h1,BorderLayout.NORTH);
        
        //Create TextField 
        dcomp_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Company Name "," COMPANY NAME ");
        dcomp_add=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Company Address"," ADDRESS ");
        dcomp_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE,false," Enter Company Mobile No.","MOBILE NO.");
        dcomp_dl=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Enter Company DL No."," DL NO.");
        dcomp_email=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Company Email Id","EMAIL ID");
        dcomp_gst=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,false," Enter Company GSTIN No."," GSTIN NO.");
        dcomp_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE,false," Enter Company Account No."," ACCOUNT NO.");
        dcomp_ifac=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,false," Enter Company IFSC Code"," IFSC CODE");
        comp_state=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," State Name","STATE NAME");
        comp_state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code","STATE CODE");
        dcomp_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No.","PAN NO.");
        bank_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Enter Company Bank Name"," BANK NAME");
            ArrayList<String> bank_items=new ArrayList<String>();
            StaticMember.setToBankName(bank_name, bank_items);
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        delete=StaticMember.MyButton("DELETE","Click On Button To Save The Record");
        company_name = StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Company Name","SELECT COMPANY NAME");
         StaticMember.setToCompany(company_name, company_id, company_items);
         
         
         
        company_name.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
               try
                {
                    ResultSet cr=StaticMember.con.createStatement().executeQuery("select * from company where company_id like'"+company_id.get(company_items.indexOf(company_name.getText()))+"'");
                    cr.next();
                    dcomp_name.setText(cr.getString("company_name"));
                    dcomp_add.setText(cr.getString("address"));
                    dcomp_mob.setText(cr.getString("mobile_no"));
                    dcomp_dl.setText(cr.getString("dl_no"));
                    dcomp_email.setText(cr.getString("email"));
                    dcomp_gst.setText(cr.getString("c_gst"));
                    dcomp_ac.setText(cr.getString("c_ac"));
                    dcomp_ifac.setText(cr.getString("c_ifac"));
                    comp_state.setText(StaticMember.getStatename(cr.getString("c_gst")));
                    dcomp_pan.setText(StaticMember.getPanno(cr.getString("c_gst")));
                    comp_state_code.setText(StaticMember.getStateCode(cr.getString("c_gst")));
                    bank_name.setText(cr.getString("bank_name"));
                    sid=cr.getString("company_id");
                }catch(SQLException ex)
                {
                    reset();
                }
               delete.requestFocusInWindow();
           }              }
           });
        
        //Cerate Panel
        
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
        mob_dlPanel.add(dcomp_mob);mob_dlPanel.add(dcomp_dl);
        gst_panPanel.add(dcomp_gst);gst_panPanel.add(dcomp_pan);
        account_ifscPanel.add(dcomp_ac);account_ifscPanel.add(dcomp_ifac);
        mainGridPanel.add(company_name);
        mainGridPanel.add(dcomp_name);mainGridPanel.add(dcomp_add);mainGridPanel.add(dcomp_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(delete);bpanel.add(reset);bpanel.add(close);
        
        delete.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
              codeToDelete();
           }   }});
        delete.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               codeToDelete();
           }});
      
        close.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
               StaticMember.DELETE_COMPANY=false;
               DeleteCompany.this.dispose(); 
               }             
           }});
      
        close.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               StaticMember.DELETE_COMPANY=false;
               DeleteCompany.this.dispose();
           }});      
        reset.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) 
           {
           if(e.getKeyChar()=='\n'){
               reset();
               }             
           }});
      
        reset.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               reset();
           }});
    }
    
   //code to Delete
    private void codeToDelete()
    {
         //code to delete
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("delete from company where company_id=?");
            stmt.setString(1, sid);         
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record Delete Sdccessfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset(); 
            StaticMember.setToCompany(company_name, company_id, company_items);
            company_name.requestFocusInWindow();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
                  
    }
    
    
    
    public void reset()
    {
        dcomp_name.setText("");
        dcomp_add.setText("");
        dcomp_mob.setText("");
        dcomp_dl.setText("");
        dcomp_email.setText("");
        dcomp_gst.setText("");
        dcomp_ac.setText("");
        dcomp_ifac.setText("");
        comp_state.setText("");
        dcomp_pan.setText("");
        bank_name.setText("");
        company_name.setText("");
    }
   
}
