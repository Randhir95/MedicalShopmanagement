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
public class DeleteManufacture extends JInternalFrame {
    JTextField dmfg_name,dmfg_address,dmfg_dl,dmfg_gst,dmfg_mob,dmfg_email,dmfg_pan,dmfg_bankname,dmfg_ac,dmfg_ifac,dmfg_state_name,state_code;
    JButton delete,dclose,dreset;
    JTextField manufacture_name;
    String str[];
    String sid;
    ResultSet rset=null;
    ArrayList<String> mfg_id=new ArrayList<>();
    ArrayList<String> mfg_items=new ArrayList<>();
    public DeleteManufacture()
    {
        super("",true,true);
        this.setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(DeleteManufacture.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300,30);
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.DELETE_MANUFACTURE=false;
              }});
        
        //set Heading
        JLabel head=new JLabel("REMOVE MANUFACTURE",JLabel.CENTER);
        head.setFont(StaticMember.HEAD_W_FONT);
        head.setForeground(StaticMember.HEAD_FG_COLOR1);
        head.setOpaque(true);
        head.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(head,BorderLayout.NORTH);
        
        dmfg_name=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,false, " Enter Manufacture Name "," MANUFACTURE NAME ");
        dmfg_address=StaticMember.MyInputBox("", 100, StaticMember.STRING_TYPE,false, " Enter Manufacture Address "," ADDRESS ");
        dmfg_dl=StaticMember.MyInputBox("", 40, StaticMember.STRING_TYPE,false, " Enter Manufacture DL NO. "," DL NO. ");
        dmfg_gst=StaticMember.MyInputBox("", 16, StaticMember.STRING_TYPE,false, " Enter Manufacture GSTIN NO. "," GSTIN NO. ");
        dmfg_mob=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE,false, " Enter Manufacture Mobile No. "," MOBILE NO. ");
        dmfg_email=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,false, " Enter Manufacture Email Id ","  EMAIL ID ");
        dmfg_pan=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE,false, " Enter Manufacture Pan No. "," PAN NO. ");
        dmfg_bankname=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,false, " Enter Manufacture Bank Name "," BANK NAME ");
        dmfg_ac=StaticMember.MyInputBox("", 20, StaticMember.INTEGER_TYPE,false, " Enter Manufacture Account No. "," ACCOUNT NO ");
        dmfg_ifac=StaticMember.MyInputBox("", 20, StaticMember.STRING_TYPE,false, " Enter Manufacture IFSC Code "," IFSC CODE ");
        dmfg_state_name=StaticMember.MyInputBox("", 40, StaticMember.STRING_TYPE,false, " Enter State Name "," STATE NAME ");
        state_code=StaticMember.MyInputBox("", 3, StaticMember.INTEGER_TYPE,false, " Enter State Code "," STATE CODE ");
        
        dclose=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        dreset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        delete=StaticMember.MyButton("DELETE","Click On Button To Delete The Record");
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
                        dmfg_name.setText(mr.getString("manifacture_name"));
                        dmfg_address.setText(mr.getString("m_address"));
                        dmfg_dl.setText(mr.getString("m_dl"));
                        dmfg_gst.setText(mr.getString("m_gst"));
                        dmfg_mob.setText(mr.getString("m_mob"));
                        dmfg_email.setText(mr.getString("m_email"));
                        dmfg_ac.setText(mr.getString("m_account"));
                        dmfg_ifac.setText(mr.getString("m_ifac"));
                        sid=mr.getString("manifacture_id");
                        dmfg_state_name.setText(StaticMember.getStatename(mr.getString("m_gst")));
                        state_code.setText(StaticMember.getStateCode(mr.getString("m_gst")));
                        dmfg_pan.setText(StaticMember.getPanno(mr.getString("m_gst")));
                        delete.requestFocusInWindow();
                    }catch(SQLException ex)
                    {

                    }
                }}});
        
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
        state_codePanel.add(dmfg_state_name);state_codePanel.add(state_code);
        mob_dlPanel.add(dmfg_mob);mob_dlPanel.add(dmfg_dl);
        gst_panPanel.add(dmfg_gst);gst_panPanel.add(dmfg_pan);
        account_ifscPanel.add(dmfg_ac);account_ifscPanel.add(dmfg_ifac);
        mainGridPanel.add(manufacture_name);
        mainGridPanel.add(dmfg_name);mainGridPanel.add(dmfg_address);mainGridPanel.add(dmfg_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(dmfg_bankname);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(delete);bpanel.add(dreset);bpanel.add(dclose);
        
        //Handle The Event
        dclose.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.DELETE_MANUFACTURE=false;
                    DeleteManufacture.this.dispose();
                }
           }});
        
        dclose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.DELETE_MANUFACTURE=false;
                    DeleteManufacture.this.dispose();
               }
        });
        delete.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    codeToDelete();
           } });
        
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToDelete();
               }
        });
      
        dreset.addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                   mdelreset();
           } });
        
        dreset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                mdelreset();
               }
        });
        
    }
    
    //code to Delete
    private void codeToDelete()
    {
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("delete from manifacture where manifacture_id=?");
            stmt.setString(1, sid);         
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this,"Record Delete Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            mdelreset();
            manufacture_name.requestFocusInWindow();
            StaticMember.setToManufacture(manufacture_name, mfg_id, mfg_items);
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
     
    
    public void mdelreset()
    {
        dmfg_name.setText("");
        dmfg_address.setText("");
        dmfg_dl.setText("");
        dmfg_gst.setText("");
        dmfg_email.setText("");
        dmfg_mob.setText("");
        dmfg_ac.setText("");
        dmfg_state_name.setText("");
        dmfg_pan.setText("");
        dmfg_bankname.setText("");
        state_code.setText("");
        dmfg_ifac.setText("");
    }
    
   
}
