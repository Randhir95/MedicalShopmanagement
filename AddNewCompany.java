/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author dell
 */
public class AddNewCompany extends JInternalFrame{
    JButton save,close,reset;
    private JTextField comp_name,comp_add,comp_mob,comp_dl,comp_email,comp_ac_holder,comp_gst,comp_ac,comp_ifac,comp_state,comp_state_code,comp_pan,bank_name;
    ArrayList<String> company_id=new ArrayList<>();
    ArrayList<String> company_items=new ArrayList<>();
    public AddNewCompany()
    {
        super("",true,true);
        this.setSize(StaticMember.SCREEN_WIDTH-830, StaticMember.SCREEN_HEIGHT-250);
        this.setDefaultCloseOperation(AddNewCompany.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(300,50);
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_NEW_COMPANY=false;
               }
        });
        
        JLabel title=new JLabel("CREATE NEW COMPANY",JLabel.CENTER);
        title.setFont(StaticMember.HEAD_W_FONT);
        title.setBackground(StaticMember.HEAD_BG_COLOR1);
        title.setOpaque(true);
        title.setForeground(StaticMember.HEAD_FG_COLOR1);
        this.add(title,BorderLayout.NORTH);
        
        
        //Create TextField   
        comp_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Name "," COMPANY NAME ");
        //StaticMember.setToCompany(comp_name, company_id, company_items);
        comp_add=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Address"," ADDRESS ");
        comp_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE," Enter Company Mobile No.","MOBILE NO.");
        comp_dl=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company DL No."," DL NO.");
        comp_email=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Email Id","EMAIL ID");
        comp_gst=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company GSTIN No."," GSTIN NO.");
        comp_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE," Enter Company Account No."," ACCOUNT NO.");
        comp_ifac=StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company IFSC Code"," IFSC CODE");
        comp_state=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false," State Name","STATE NAME");
        comp_state_code=StaticMember.MyInputBox("",3,StaticMember.INTEGER_TYPE,false," State Code","STATE CODE");
        comp_pan=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false," Pan No.","PAN NO.");
        bank_name = StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Company Bank Name"," BANK NAME");
            ArrayList<String> bank_items=new ArrayList<String>();
            StaticMember.setToBankName(bank_name, bank_items);
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
        state_codePanel.add(comp_state);state_codePanel.add(comp_state_code);
        mob_dlPanel.add(comp_mob);mob_dlPanel.add(comp_dl);
        gst_panPanel.add(comp_gst);gst_panPanel.add(comp_pan);
        account_ifscPanel.add(comp_ac);account_ifscPanel.add(comp_ifac);
        mainGridPanel.add(comp_name);mainGridPanel.add(comp_add);mainGridPanel.add(comp_email);
        mainGridPanel.add(mob_dlPanel);mainGridPanel.add(gst_panPanel);mainGridPanel.add(state_codePanel);
        mainGridPanel.add(bank_name);mainGridPanel.add(account_ifscPanel);mainGridPanel.add(bpanel);
        
        bpanel.add(new JLabel(""));bpanel.add(new JLabel(""));
        bpanel.add(save);bpanel.add(reset);bpanel.add(close);
        //Event Handling
        save.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    codeToSave();
                }           }});
        save.addActionListener(new ActionListener(){          
            public void actionPerformed(ActionEvent e) {
                codeToSave();
            }       });
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    StaticMember.ADD_NEW_COMPANY=false;
                    AddNewCompany.this.dispose(); 
                    
                }           }});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                 StaticMember.ADD_NEW_COMPANY=false;
                    AddNewCompany.this.dispose(); 
            }
        
        });
        reset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                   creset();
                }           }});
        
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                creset();
            }
        });
        comp_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                {
                    comp_add.requestFocusInWindow();
                }
            }});
        comp_add.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_email.requestFocusInWindow();
                }            }});
        
        comp_email.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_mob.requestFocusInWindow();
                }           }});  
        comp_mob.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_dl.requestFocusInWindow();
                } }});
        comp_dl.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_gst.requestFocusInWindow();
                }  
             }});
        comp_gst.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_pan.setText(StaticMember.getPanno(comp_gst.getText()));
                    comp_state.setText(StaticMember.getStatename(comp_gst.getText()));
                    comp_state_code.setText(StaticMember.getStateCode(comp_gst.getText()));
                    bank_name.requestFocusInWindow();               
                }           }});
        bank_name.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_ac.requestFocusInWindow();               
                }           }});
        comp_ac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    comp_ifac.requestFocusInWindow();               
                }           }});
        comp_ifac.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    bank_name.requestFocusInWindow();               
                }           }});
        
    }
    //Create Method to add Company
    public void codeToSave()
    {
        
        //validate data  
     
        if(comp_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Company Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            comp_name.requestFocusInWindow();
           return;
        }
        if(comp_add.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            comp_add.requestFocusInWindow();
            return;
        }
        
        //code to save
        try
        {
              
            PreparedStatement stmt=StaticMember.con.prepareStatement("insert into company(company_id,company_name,address,mobile_no,dl_no,email,c_gst,c_ac,c_ifac,bank_name) values(?,?,?,?,?,?,?,?,?,?)");
            ResultSet comp_rset=StaticMember.con.createStatement().executeQuery("select max(company_id) as company_id  from company");
            comp_rset.next();
            int comp_id=comp_rset.getInt("company_id")+1;
            stmt.setInt(1, comp_id);
            stmt.setString(2, comp_name.getText());
            stmt.setString(3, comp_add.getText());
            stmt.setString(4, comp_mob.getText());
            stmt.setString(5, comp_dl.getText());
            stmt.setString(6, comp_email.getText());
            stmt.setString(7, comp_gst.getText());
            stmt.setString(8, comp_ac.getText());
            stmt.setString(9, comp_ifac.getText());
            stmt.setString(10, bank_name.getText());
            stmt.execute();
            JOptionPane.showMessageDialog(this, "Record save Succesfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            creset();
            comp_name.requestFocusInWindow();
            MDIMainWindow.a_n_p_obj.codeToSetCompany();
         
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    public void creset()
    {  
        comp_name.setText("");
        comp_add.setText("");
        comp_mob.setText("");
        comp_dl.setText("");
        comp_email.setText("");
        comp_gst.setText("");
        comp_ac.setText("");
        comp_ifac.setText("");
        comp_state.setText("");
        comp_state_code.setText("");
        comp_pan.setText("");
        
    }
    
}
