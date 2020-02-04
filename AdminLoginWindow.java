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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import static komalhealthcare.MDIMainWindow.desktop;

/**
 *
 * @author dell
 */
public class AdminLoginWindow extends JDialog{
    //JTextField ;
    JPasswordField pass,cpass,user1;
    private JButton login,exit;
    ResultSet rset=null;
    CreateSelfCompany c_s_c_obj;
    AdminLoginWindow a_l_w_obj;
    public AdminLoginWindow()
    {
        super(MDIMainWindow.self,"LOG IN",true);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(500,120);
        this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent we)
        {
            System.exit(0);
        }
        });
        
        //Create a Panel
        JPanel logpanel=new JPanel(new GridLayout(4,1,5,5));
        JPanel p1=new JPanel(new BorderLayout());
        JPanel p2=new JPanel(new BorderLayout());
        logpanel.setBackground(StaticMember.bcolor);
        p1.setBackground(StaticMember.bcolor);
        p2.setBackground(StaticMember.bcolor);
        this.setBackground(StaticMember.bcolor);
        //create a Font object
        
        //create Label and TextField
        JLabel l1=new JLabel("User Name : ",JLabel.RIGHT);
        l1.setForeground(StaticMember.flcolor);
        l1.setFont(StaticMember.labelFont);
        JLabel l2=new JLabel("  Password : ",JLabel.RIGHT);
        l2.setForeground(StaticMember.flcolor);
        l2.setFont(StaticMember.labelFont);
        
        user1=new JPasswordField("");
        user1.setBackground(StaticMember.bcolor);
        user1.setFont(StaticMember.textFont);
        user1.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) { 
            if(e.getKeyChar()=='\n')
            {
               pass.requestFocusInWindow();
            }            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        pass=new JPasswordField("");
        pass.setBackground(StaticMember.bcolor);
        pass.setFont(StaticMember.textFont);
        pass.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) { 
            if(e.getKeyChar()=='\n')
            {
                login.requestFocusInWindow();
            }
            }
            public void keyPressed(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }});
        
        //add All Component on Panel
        
        logpanel.add(new JLabel(""));
        p1.add(l1,BorderLayout.WEST);
        p1.add(user1,BorderLayout.CENTER);
        logpanel.add(p1);
        p2.add(l2,BorderLayout.WEST);
        p2.add(pass,BorderLayout.CENTER);
        logpanel.add(p2);
        logpanel.add(new JLabel());
        this.add(new JLabel("            "),BorderLayout.EAST);
        this.add(new JLabel("            "),BorderLayout.WEST);
        this.add(logpanel,BorderLayout.CENTER);
        
        //Create a Button Panel
        JPanel bpanel=new JPanel(new GridLayout(1,3));
        bpanel.setBackground(StaticMember.bcolor);
        login=new JButton("LOG IN");
        login.setForeground(StaticMember.bfcolor);
        login.setFont(StaticMember.buttonFont);
        bpanel.add(new JLabel(""));
        bpanel.add(login);
        bpanel.add(new JLabel());
        this.add(bpanel,BorderLayout.SOUTH);
        login.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) { 
            if(e.getKeyChar()=='\n')
            {
                codeToLogin();
                AdminLoginWindow.this.dispose();
            }
            }
            @Override
            public void keyPressed(KeyEvent e) {            }
            @Override
            public void keyReleased(KeyEvent e) { }
        });
        login.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                codeToLogin();
                AdminLoginWindow.this.dispose();
            }
        });
        
    }
    private void codeToLogin()
    {
        
         try
        {
        Statement stmt=StaticMember.con.createStatement();
        rset=stmt.executeQuery("select * from password where admin_user like'"+user1.getText()+"' and admin_pass like'"+pass.getText()+"'"); 
        if(!rset.next())
        {
             JOptionPane.showMessageDialog(AdminLoginWindow.this,"Incorect User OR Password !" , "Error !",JOptionPane.ERROR_MESSAGE);
             StaticMember.CREATE_SELF_COMPANY=false;
             AdminLoginWindow.this.dispose();
             return;
        }  
        
                c_s_c_obj=new CreateSelfCompany();
                MDIMainWindow.desktop.add(c_s_c_obj);
                c_s_c_obj.setVisible(true);
                login.getModel();
                c_s_c_obj.setResizable(false);
                StaticMember.CREATE_SELF_COMPANY=false;
                AdminLoginWindow.this.dispose();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(AdminLoginWindow.this, ex.getMessage(), "OOPs!", JOptionPane.ERROR_MESSAGE);
         
        }
            
    }
}
