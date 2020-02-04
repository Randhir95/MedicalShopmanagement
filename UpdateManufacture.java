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
public class UpdateManufacture extends JInternalFrame{
    JTextField manufacture_name,umfg_name,umfg_address,umfg_dl,umfg_gst,umfg_mob,umfg_email,umfg_pan,umfg_ac,umfg_ifac,bank_name,state_name,state_code;
    JButton update,uclose,ureset;
    ResultSet rset=null;
    String sid;
    ArrayList<String> mfg_id=new ArrayList<>();
    ArrayList<String> mfg_items=new ArrayList<>();
    public UpdateManufacture()
    {
        super("",true,true);
        this.setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(UpdateManufacture.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300, 30);
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.UPDATE_MANUFACTURE=false;
              }});
        
        //set Heading
        JLabel head=new JLabel("MODIFY MANUFACTURE",JLabel.CENTER);
        head.setFont(StaticMember.HEAD_W_FONT);
        head.setForeground(StaticMember.HEAD_FG_COLOR1);
        head.setOpaque(true);
        head.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(head,BorderLayout.NORTH);
        
        umfg_name=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Name "," MANUFACTURE NAME ");
        umfg_address=StaticMember.MyInputBox("", 100, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Address "," ADDRESS ");
        umfg_dl=StaticMember.MyInputBox("", 40, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture DL NO. "," DL NO. ");
        umfg_gst=StaticMember.MyInputBox("", 16, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture GSTIN NO. "," GSTIN NO. ");
        umfg_mob=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE, " Enter Manufacture Mobile No. "," MOBILE NO. ");
        umfg_email=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE, " Enter Manufacture Email Id ","  EMAIL ID ");
        umfg_pan=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE,false, " Enter Manufacture Pan No. "," PAN NO. ");
        bank_name=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Bank Name "," BANK NAME ");
        ArrayList<String> bank_items=new ArrayList<String>();
        StaticMember.setToBankName(bank_name, bank_items);
        umfg_ac=StaticMember.MyInputBox("", 20, StaticMember.INTEGER_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Account No. "," ACCOUNT NO ");
        umfg_ifac=StaticMember.MyInputBox("", 20, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture IFSC Code "," IFSC CODE ");
        state_name=StaticMember.MyInputBox("", 40, StaticMember.STRING_TYPE,false, " Enter State Name "," STATE NAME ");
        state_code=StaticMember.MyInputBox("", 3, StaticMember.INTEGER_TYPE,false, " Enter State Code "," STATE CODE ");
        
        uclose=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        ureset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        update=StaticMember.MyButton("UPDATE","Click On Button To Update The Record");
        manufacture_name=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Select Manufacture Name "," SELECT MANUFACTURE NAME ");
        StaticMember.setToManufacture(manufacture_name, mfg_id, mfg_items);
        
        manufacture_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    try
                    {
                        ResultSet mr=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+mfg_id.get(mfg_items.indexOf(manufacture_name.getText()))+"'");
                        mr.next();
                        umfg_name.setText(mr.getString("manifacture_name"));
                        umfg_address.setText(mr.getString("m_address"));
                        umfg_dl.setText(mr.getString("m_dl"));
                        umfg_gst.setText(mr.getString("m_gst"));
                        umfg_mob.setText(mr.getString("m_mob"));
                        umfg_email.setText(mr.getString("m_email"));
                        umfg_ac.setText(mr.getString("m_account"));
                        umfg_ifac.setText(mr.getString("m_ifac"));
                        sid=mr.getString("manifacture_id");
                        state_name.setText(StaticMember.getStatename(mr.getString("m_gst")));
                        state_code.setText(StaticMember.getStateCode(mr.getString("m_gst")));
                        umfg_pan.setText(StaticMember.getPanno(mr.getString("m_gst")));
                        umfg_name.requestFocusInWindow();
                    }catch(SQLException ex)
                    {
                        JOptionPane.showMessageDialog(UpdateManufacture.this, ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            });
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainGridPanel=new JPanel(new GridLayout(10,1,5,5));
        JPanel state_codePanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel mob_dlPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel gst_panPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel account_ifscPanel=new JPanel(new GridLayout(1,2,10,0));
        JPanel bpanel=new JPanel(new GridLayout(1,5,10,10));
        mainPanel.add(new JLabel("      "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(mainGridPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("    "),BorderLayout.SOUTH);
        this.add(mainPanel,BorderLayout.CENTER);
        state_codePanel.add(state_name);state_codePanel.add(state_code);
        mob_dlPanel.add(umfg_mob);mob_dlPanel.add(umfg_dl);
        gst_panPanel.add(umfg_gst);gst_panPanel.add(umfg_pan);
        account_ifscPanel.add(umfg_ac);account_ifscPanel.add(umfg_ifac);
        mainGridPanel.add(manufacture_name);
        mainGridPanel.add(umfg_name);mainGridPanel.add(umfg_address);mainGridPanel.add(umfg_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(update);bpanel.add(ureset);bpanel.add(uclose);
        
        //Handle The Event
        update.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                codeToUpdate();
           }}});
        
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToUpdate();
               }
        });
        uclose.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.UPDATE_MANUFACTURE=false;
                    UpdateManufacture.this.dispose();
                }}});
        
        uclose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.UPDATE_MANUFACTURE=false;
                    UpdateManufacture.this.dispose();
               }
        });
        ureset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    mureset();
           }}}); 
        ureset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                mureset();
               }
        });
        umfg_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_address.requestFocusInWindow();
           }});
        umfg_address.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_email.requestFocusInWindow();
           }});
        umfg_mob.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_dl.requestFocusInWindow();
           }});
        umfg_email.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_mob.requestFocusInWindow();
           }});
        umfg_dl.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_gst.requestFocusInWindow();
           } });
        umfg_gst.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    state_name.setText(StaticMember.getStatename(umfg_gst.getText()));
                    state_code.setText(StaticMember.getStateCode(umfg_gst.getText()));
                    umfg_pan.setText(StaticMember.getPanno(umfg_gst.getText()));
                    bank_name.requestFocusInWindow();
                    
           }}});
        bank_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_ac.requestFocusInWindow();
           }});
        umfg_ac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    umfg_ifac.requestFocusInWindow();
           } });
        
        umfg_ifac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    update.requestFocusInWindow();
           }});
        
        
    }
   //code to Update manufacture
    public void codeToUpdate()
    {
        //validate data         
        if(umfg_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Manufacture Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            umfg_name.requestFocusInWindow();
            return;
        }
     
         //code to update
        try
        {
            //ucname,ucadd,ucmob,ucdl,ucemail,uclocation;
          PreparedStatement stmt=StaticMember.con.prepareStatement("update manifacture set manifacture_name=?,m_address=?,m_email=?,m_mob=?,m_bank_name=?,m_account=?,m_ifac=?,m_dl=?,m_gst=? where manifacture_id=?"); 
          stmt.setString(1, umfg_name.getText());
          stmt.setString(2, umfg_address.getText());
          stmt.setString(3, umfg_email.getText());
          stmt.setString(4, umfg_mob.getText());
          stmt.setString(5, bank_name.getText());
          stmt.setString(6, umfg_ac.getText());
          stmt.setString(7, umfg_ifac.getText());
          stmt.setString(8, umfg_dl.getText());
          stmt.setString(9, umfg_gst.getText());
          stmt.setString(10, sid);
          stmt.executeUpdate();
          JOptionPane.showMessageDialog(this, "Record Update Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
           mureset();
           StaticMember.setToManufacture(manufacture_name, mfg_id, mfg_items);
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
        
    
    }
    public void mureset()
    {
        umfg_name.setText("");
        umfg_address.setText("");
        umfg_dl.setText("");
        umfg_gst.setText("");
        umfg_email.setText("");
        umfg_mob.setText("");
        umfg_ac.setText("");
        state_name.setText("");
        state_code.setText("");
        bank_name.setText("");
        umfg_pan.setText("");
        umfg_ifac.setText("");
        manufacture_name.requestFocusInWindow();
    }
    
    
    
}
