/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author dell
 */
public class AddManufacture extends JInternalFrame{
    JTextField mfg_name,mfg_address,mfg_dl,mfg_gst,mfg_mob,mfg_email,mfg_pan,mfg_bank_name,mfg_ac,mfg_ifac,mfg_state,state_code;
    JButton save,close,reset;
    Image img;
    ArrayList<String> mfg_id=new ArrayList<>();
    ArrayList<String> mfg_items=new ArrayList<>();
    public AddManufacture()
    {
        super("",true,true);
        this.setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(AddManufacture.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300, 30);
        
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_MANUFACTURE=false;
              }});
        
        //set Heading
        JLabel head=new JLabel("CREATE MANUFACTURE",JLabel.CENTER);
        head.setFont(StaticMember.HEAD_W_FONT);
        head.setBackground(StaticMember.HEAD_BG_COLOR1);
        head.setForeground(StaticMember.HEAD_FG_COLOR1);
        head.setOpaque(true);
        this.add(head,BorderLayout.NORTH);
        
        mfg_name=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Name "," MANUFACTURE NAME ");
        //StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
        mfg_address=StaticMember.MyInputBox("", 100, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Address "," ADDRESS ");
        mfg_dl=StaticMember.MyInputBox("", 40, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture DL NO. "," DL NO. ");
        mfg_gst=StaticMember.MyInputBox("", 16, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture GSTIN NO. "," GSTIN NO. ");
        mfg_mob=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE, " Enter Manufacture Mobile No. "," MOBILE NO. ");
        mfg_email=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE, " Enter Manufacture Email Id ","  EMAIL ID ");
        mfg_pan=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE,false, " Enter Manufacture Pan No. "," PAN NO. ");
        mfg_bank_name=StaticMember.MyInputBox("", 60, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Bank Name "," BANK NAME ");
        ArrayList<String> bank_items=new ArrayList<String>();
        StaticMember.setToBankName(mfg_bank_name, bank_items);
        mfg_ac=StaticMember.MyInputBox("", 20, StaticMember.INTEGER_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture Account No. "," ACCOUNT NO ");
        mfg_ifac=StaticMember.MyInputBox("", 20, StaticMember.STRING_TYPE,StaticMember.UPPER_CASE, " Enter Manufacture IFSC Code "," IFSC CODE ");
        mfg_state=StaticMember.MyInputBox("", 40, StaticMember.STRING_TYPE,false, " Enter State Name "," STATE NAME ");
        state_code=StaticMember.MyInputBox("", 3, StaticMember.INTEGER_TYPE,false, " Enter State Code "," STATE CODE ");
        
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
        state_codePanel.add(mfg_state);state_codePanel.add(state_code);
        mob_dlPanel.add(mfg_mob);mob_dlPanel.add(mfg_dl);
        gst_panPanel.add(mfg_gst);gst_panPanel.add(mfg_pan);
        account_ifscPanel.add(mfg_ac);account_ifscPanel.add(mfg_ifac);
        mainGridPanel.add(mfg_name);mainGridPanel.add(mfg_address);mainGridPanel.add(mfg_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(mfg_bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(save);bpanel.add(reset);bpanel.add(close);
        
        
        //Handle The Event
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.ADD_MANUFACTURE=false;
                    AddManufacture.this.dispose();
                }
           }});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_MANUFACTURE=false;
                AddManufacture.this.dispose();
                
               }
        });
        reset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    mreset();
           }}});
        
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               mreset();
               }
        });
        save.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){ 
                    codeToSave();
                    //MDIMainWindow.s_obj.setToManufacture();
                } }       });
        
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToSave();
               }});
        mfg_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_address.requestFocusInWindow();
            }});
        mfg_address.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_email.requestFocusInWindow();
           }});
        mfg_mob.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_dl.requestFocusInWindow();
           }});
        mfg_email.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_mob.requestFocusInWindow();
           } });
        mfg_dl.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_gst.requestFocusInWindow();
           }});
        mfg_gst.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    mfg_pan.setText(StaticMember.getPanno(mfg_gst.getText()));
                    mfg_state.setText(StaticMember.getStatename(mfg_gst.getText()));
                    state_code.setText(StaticMember.getStateCode(mfg_gst.getText()));
                    mfg_bank_name.requestFocusInWindow();
                }
            }});
        mfg_bank_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_ac.requestFocusInWindow();
           } });
        mfg_ac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    mfg_ifac.requestFocusInWindow();
           }});
        mfg_ifac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    save.requestFocusInWindow();
            }});
        
    }
    
    //code To save in dataBase
    public void codeToSave()
    {
        if(mfg_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Manufacture Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            mfg_name.requestFocusInWindow();
            return;
 
        }
         //code to update
        try
        {
            
            PreparedStatement stmt=StaticMember.con.prepareStatement("insert into manifacture(manifacture_id,manifacture_name,m_address,m_dl,m_gst,m_email,m_mob,m_account,m_bank_name,m_ifac) values(?,?,?,?,?,?,?,?,?,?)"); 
            ResultSet r=StaticMember.con.createStatement().executeQuery("select max(manifacture_id) as manifacture_id  from manifacture");
            r.next();
            int mfgid=r.getInt("manifacture_id")+1;
            stmt.setInt(1, mfgid);
            stmt.setString(2, mfg_name.getText());
            stmt.setString(3, mfg_address.getText());
            stmt.setString(4, mfg_dl.getText());
            stmt.setString(5, mfg_gst.getText());
            stmt.setString(6, mfg_email.getText());
            stmt.setString(7, mfg_mob.getText());
            stmt.setString(8, mfg_ac.getText());
            stmt.setString(9, mfg_bank_name.getText());
            stmt.setString(10, mfg_ifac.getText());
            stmt.execute();
            JOptionPane.showMessageDialog(this, "Record Saveed Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            mreset();
            mfg_name.requestFocusInWindow();
            MDIMainWindow.s_obj.setStockManufacture();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    public void mreset()
    {
        mfg_name.setText("");
        mfg_address.setText("");
        mfg_dl.setText("");
        mfg_gst.setText("");
        mfg_email.setText("");
        mfg_mob.setText("");
        mfg_ac.setText("");
        mfg_state.setText("");
        mfg_pan.setText("");
        state_code.setText("");
        mfg_bank_name.setText("");
        mfg_ifac.setText("");
    }
    
}
